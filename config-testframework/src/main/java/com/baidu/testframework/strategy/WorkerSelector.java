package com.baidu.testframework.strategy;

import akka.actor.ActorRef;

import java.util.List;

/**
 * Created by edwardsbean on 14-11-10.
 */
public interface WorkerSelector {
    public ActorRef getWorker(List<ActorRef> workers);
}
