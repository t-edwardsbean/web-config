package com.baidu.testframework.app;

import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * Created by edwardsbean on 14-11-4.
 */
public class ActorManager {
    private static final ActorRef callerActor;
    static {
        callerActor = ActorFactory.create(CallerActor.props(),"callerActor");
    }
}
