package com.baidu.testframework.app;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import com.baidu.testframework.config.ClientConfigProvider;
import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;
import com.baidu.testframework.message.*;
import com.google.common.collect.ImmutableList;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by edwardsbean on 14-11-4.
 */
@Component
public class ActorManager {
    private ActorRef materActor = null;
    private ActorSystem system;
    private final static Logger log = LoggerFactory.getLogger(ActorManager.class.getName());
    public static String webRoot = System.getProperty("project.root");

//    public ActorManager() {
//    }

    @Autowired
    public ActorManager(@Value("${dsf.stress.local}") boolean local) {
        if (local) {
            log.debug("压测使用本地模式");
            system = ActorSystem.create("stressTestSys", ConfigFactory.load().getConfig("LocalSys"));
            List<ActorRef> workers = new ArrayList<>();
            workers.add(system.actorOf(Props.create(WorkerActor.class,getWebConfPath(webRoot),getWebLibPath(webRoot),webRoot), "localWorkerActor"));
            materActor = system.actorOf(Props.create(MasterActor.class, workers), "localMasterActor");
        } else {
            /**
             * 在远程做压测，需要搜索MasterActor
             */
        }
    }

    public long run(FrameworkConfig frameworkConfig, MethodConfig methodConfig) {
        LaunchExecutor launchExecutor = new LaunchExecutor(new TaskConfig(frameworkConfig,methodConfig));
        long taskId = (long) sendAndWait(launchExecutor);
        log.info("提交任务成功，返回任务Id:" + taskId);
        return taskId;

    }

    public void stop(long taskId) {
        KillTask task = new KillTask(taskId);
        send(task);
    }

    private void send(Object object) {
        Inbox i = Inbox.create(system);
        i.send(materActor, object);
    }

    private Object sendAndWait(Object object) {
        Inbox i = Inbox.create(system);
        i.send(materActor, object);
        return i.receive(Duration.create(5, TimeUnit.SECONDS));
    }

    public ImmutableList<Executor> getRunningTask() {
        return (ImmutableList<Executor>) sendAndWait(new GetRunningTask());
    }

    private String getWebConfPath(String webRoot) {
        return webRoot + "WEB-INF" + File.separator + "classes" + File.separator;
    }

    private String getWebLibPath(String webRoot) {
        return webRoot + "WEB-INF" + File.separator + "lib" + File.separator + "*";
    }
}
