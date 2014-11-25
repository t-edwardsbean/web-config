package com.baidu.testframework.app;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;
import com.baidu.testframework.core.FrameworkManager;
import com.baidu.testframework.deploy.ExecutorBackend;
import com.baidu.testframework.message.*;
import com.baidu.tools.Page;
import com.baidu.tools.WebPlatTool;
import com.typesafe.config.Config;
import org.xeustechnologies.jcl.JarClassLoader;
import scala.Option;
import scala.concurrent.duration.Duration;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 执行压测任务
 * Created by edwardsbean on 14-11-10.
 */
public class ExecutorActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);
    ActorSelection workerActor;
    ActorSystem system;
    final long taskId;

    public ExecutorActor(long taskId) {
        this.taskId = taskId;
    }

    @Override
    public void preStart() throws Exception {
        system = getContext().system();
        Config config = system.settings().config();
        workerActor = system.actorSelection(config.getString("akka.actor.worker"));
        system.scheduler().scheduleOnce(Duration.create(100, TimeUnit.MILLISECONDS),
                getSelf(), new TaskConfig(taskId), system.dispatcher(),ActorRef.noSender());
    }

    @Override
    public void postStop() throws Exception {
        log.info("postStop,关闭ActorSystem");
        system.shutdown();
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        log.info("preRestart");
    }

    @Override
    public void onReceive(Object message) throws Exception{
        if (message instanceof LaunchTask) {
            log.info("正在执行任务");
            LaunchTask task = (LaunchTask) message;
            TaskConfig taskConfig = task.getConfig();
            FrameworkConfig frameworkConfig = taskConfig.getFrameworkConfig();
            log.info("Checking configuration");
            frameworkConfig.checkConfig();
            MethodConfig config = taskConfig.getMethodConfig();
            log.info("Checking method configuration");
            config.setServiceInterface(frameworkConfig.getTestClazz());
            config.checkConfig();
            FrameworkManager frameworkManager = new FrameworkManager(config, frameworkConfig);
            final FrameworkManager reference = frameworkManager;
            Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
                @Override
                public void run() {
                    //压测出错,或者被终止，注销任务
                    workerActor.tell(new RemoveTask(taskId), getSelf());
                    reference.stopApplication();
                    log.info("任务已终止");
                }
            });
            //启动框架
            frameworkManager.initStatistics();
            frameworkManager.startApplication();
            frameworkManager.startReport();
            //压测正常，向worker汇报
            workerActor.tell(new RegisterTask(taskId),getSelf());
        } else if (message instanceof TaskConfig) {
            log.info("向WorkerActor拉取配置");
            workerActor.tell(message,getSelf());
        } else if (message instanceof KillTask) {
            log.info("收到终止命令");
            ExecutorBackend.system.shutdown();
        } else {
            unhandled(message);
        }
    }
}
