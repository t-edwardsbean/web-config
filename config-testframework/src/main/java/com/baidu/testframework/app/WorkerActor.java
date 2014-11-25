package com.baidu.testframework.app;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.message.*;
import com.baidu.tools.Page;
import com.baidu.tools.WebPlatTool;
import com.typesafe.config.Config;
import org.apache.commons.io.FileUtils;
import org.zeroturnaround.exec.ProcessExecutor;
import scala.Option;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 负责为压测进程初始化好独立的空间，并启动压测进程
 * Created by edwardsbean on 14-11-4.
 */
public class WorkerActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    //web local和remote model，使用不同的工作目录
    private String configDir;
    private String libDir;
    private String workDir;
    /**
     * TODO 添加配置等待队列，由Executor前来取配置，过期删除
     */
    private Map<Long, Executor> executors;
    private ActorSelection master;

    public WorkerActor(String configDir, String libDir, String workDir) {
        this.configDir = configDir;
        this.libDir = libDir;
        this.workDir = workDir;
    }

    @Override
    public void preStart() throws Exception {
        ActorSystem system = getContext().system();
        Config config = system.settings().config();
        executors = new HashMap<>();
        String masterAddress = config.getString("akka.actor.master");
        master = system.actorSelection(masterAddress);
    }

    @Override
    public void postRestart(Throwable thrwbl) throws Exception {
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
    }

    @Override
    public void postStop() throws Exception {
    }

    private String getInterfaceLib(long taskId) {
        return workDir + "executor" + File.separator + "executor-" + taskId + File.separator + "lib" + File.separator + "*";
    }

    private String generateExeHome(long taskId) {
        return workDir + "executor" + File.separator + "executor-" + taskId;
    }

    private String generateExeLib(long taskId) {
        return workDir + "executor" + File.separator + "executor-" + taskId + File.separator + "lib";
    }
    private String generateExeConf(long taskId) {
        return workDir + "executor" + File.separator + "executor-" + taskId + File.separator + "conf";
    }
    /**
     * 为压测进程准备必要的目录结构，文件等
     */
    private void initExecutor(FrameworkConfig frameworkConfig,long taskId) throws Exception{
        /*
        TODO 重构代码,将initExecutor独立成一个Actor,免得编译过程中，阻塞WorkerActor
         */
        File home = new File(generateExeHome(taskId));
        File lib = new File(generateExeLib(taskId));
        if (home.mkdirs() && lib.mkdirs()) {
            log.info("创建压测进程工作目录：" + home.getAbsolutePath());
            log.info("创建压测进程工作目录：" + lib.getAbsolutePath());
        } else {
            log.info("创建压测进程工作目录失败，目录已存在？");
        }
        //下载
        log.info("Downloading interface jar");
        String serviceName = frameworkConfig.getServiceName();
        String webToolAddrs = frameworkConfig.getWebToolPlatAddrsCfg();
        List<Page> pages = WebPlatTool.getAllService(webToolAddrs);
        String id = WebPlatTool.getServiceId(serviceName, pages);
        File zip = WebPlatTool.download(webToolAddrs, id, generateExeLib(taskId));
        //编译接口
        String shell = generateExeHome(taskId) + File.separator + "../package.sh";
        WebPlatTool.parseZip2Jar(zip,shell);

//        File shell = new File(configDir + File.separator + "package.sh");
//        log.debug("package脚本：" + shell.getAbsolutePath());
//        File des = new File(generateExeConf(taskId) + File.separator + "package.sh");
//        FileUtils.copyFile(shell,des);
//        des.setExecutable(true);
//        log.debug("为Executor准备数据，拷贝编译脚本");
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof LaunchExecutor) {
            LaunchExecutor task = (LaunchExecutor) message;
            long taskId = task.getTaskId();
            log.info("启动任务进程");
            try {
                initExecutor(task.getConfig().getFrameworkConfig(),taskId);
                Future future = new ProcessExecutor()
                        .command("java", "-cp", libDir + ":" + configDir + ":" + getInterfaceLib(taskId), "com.baidu.testframework.deploy.ExecutorBackend", taskId + "")
                        .directory(new File(generateExeHome(taskId)))
                        .destroyOnExit()
                        .start()
                        .getFuture();

                Executor runningExecutor = new Executor(future, task.getTaskId(), task.getConfig());
                executors.put(runningExecutor.getTaskId(), runningExecutor);
                log.info("启动任务" + task.getTaskId() +"进程成功");
            } catch (Exception e) {
                log.warning("启动任务" + task.getTaskId() + "进程失败:[{}]", e.getMessage());
            }

        } else if (message instanceof KillTask) {
            KillTask killTask = (KillTask) message;
            Executor executor = executors.remove(killTask.getTaskId());
            if (executor != null) {
                executor.getTask().cancel(true);
                log.info("停止任务，TaskId:" + killTask.getTaskId());
            } else {
                log.warning("试图停止不存在的任务,TaskId:" + killTask.getTaskId());
            }
        } else if (message instanceof RemoveTask) {
            /*
             压测进程意外退出
             */
            RemoveTask task = (RemoveTask) message;
            if (executors.remove(task.getTaskId()) != null) {
                master.tell(task, getSelf());
            }
        } else if (message instanceof TaskConfig) {
            /*
            ExecutorActor来取任务,推送配置
             */
            TaskConfig taskConfig = (TaskConfig) message;
            log.info(getBeautifulId(taskConfig.getTaskId())+ "前来获取压测配置");
            Executor executor = executors.get(taskConfig.getTaskId());
            /*
            删除已经被取了的配置
             */
            if (executor != null) {
                LaunchTask launchTask = new LaunchTask(executor.getConfig());
                getSender().tell(launchTask, getSelf());

            }
        } else if (message instanceof RegisterTask) {
            //压测任务正常运行，向MasterActor汇报
            RegisterTask task = (RegisterTask) message;
            log.info(getBeautifulId(task.getTaskId()) + "压测程序正常运行");
            master.tell(new RegisterTask(task.getTaskId()),getSelf());
        } else {
            unhandled(message);
        }
    }

    private String getBeautifulId(long taskId) {
        return "Executor-" + taskId;
    }
}
