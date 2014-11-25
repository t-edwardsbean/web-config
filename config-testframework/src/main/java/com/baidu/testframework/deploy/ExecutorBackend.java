package com.baidu.testframework.deploy;

import akka.actor.*;
import akka.japi.Function;
import com.baidu.testframework.app.ExecutorActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.concurrent.duration.Duration;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class ExecutorBackend {
    private final static Logger log = LoggerFactory.getLogger(ExecutorBackend.class);
    public static ActorSystem system;
    public static void main(String[] args) {
        long taskId = 0;
        if (args.length != 0) {
            taskId = Long.parseLong(args[0]);
        }
        try {
            log.info("初始化Executor Actor System");
            Config config = ConfigFactory.load().getConfig("ExecutorSys");
            log.info("成功加载ActorSystem配置文件");
            system = ActorSystem.create("executor", config);
            log.info("准备执行任务");
            ActorRef exec = system.actorOf(Props.create(ExecutorActor.class,taskId));
        } catch (Exception e) {
            log.error("启动ActorSystem过程中出错：",e);
        }
        system.awaitTermination();
        System.exit(0);
    }

}
