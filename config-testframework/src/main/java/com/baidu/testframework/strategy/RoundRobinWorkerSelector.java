package com.baidu.testframework.strategy;

import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by edwardsbean on 14-11-10.
 */
public class RoundRobinWorkerSelector implements WorkerSelector{
    private final static Logger log = LoggerFactory.getLogger(RoundRobinWorkerSelector.class.getName());
    private int index = 0;

    @Override
    public ActorRef getWorker(List<ActorRef> workers) {
        if (workers != null && !workers.isEmpty()) {
            index ++;
            if (index >= workers.size()) {
                index = 0;
            }
        }
        ActorRef worker = workers.get(index);
        log.debug("选择一个Worker:" + worker);
        return worker;
    }
}
