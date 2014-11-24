package com.baidu.testframework.deploy;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.baidu.testframework.app.ExecutorActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class ExecutorBackend {
    private final static Logger log = LoggerFactory.getLogger(ExecutorBackend.class.getName());

    public static void main(String[] args) {
        log.info("Start test executor");
        ActorSystem system = ActorSystem.create("executor");
        ActorRef exec = system.actorOf(Props.create(ExecutorActor.class));
        system.awaitTermination();
    }

}
