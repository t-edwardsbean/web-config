package com.baidu.testframework.app;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.baidu.testframework.message.*;
import com.baidu.testframework.strategy.RoundRobinWorkerSelector;
import com.baidu.testframework.strategy.WorkerSelector;
import com.google.common.collect.*;
import scala.Option;

import java.util.*;

/**
 * 负责接收，分配压测任务。
 * Created by edwardsbean on 14-11-4.
 */
public class MasterActor extends UntypedActor {
    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    //worker节点
    private List<ActorRef> workers;
    //压测任务运行成功，则向master注册
    private Multimap<ActorRef, RegisterTask> tasks = ArrayListMultimap.create();
    private WorkerSelector selector = new RoundRobinWorkerSelector();

    public MasterActor(List<ActorRef> workers) {
        this.workers = workers;
    }

    @Override
    public void preStart() throws Exception {
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

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof LaunchExecutor) {
            //由master生成任务id
            long taskId = System.currentTimeMillis();
            LaunchExecutor task = (LaunchExecutor) message;
            task.setTaskId(taskId);
            ActorRef worker = selector.getWorker(workers);
            log.info("收到任务请求，生成TaskId:" + taskId);
            worker.tell(task, getSelf());
            getSender().tell(taskId, getSelf());
        } else if (message instanceof RegisterTask) {
            RegisterTask task = (RegisterTask) message;
            log.info("压测进程正常运行，worker前来汇报，TaskId:" + task.getTaskId());
            ActorRef worker = getSender();
            tasks.put(worker, task);
        } else if (message instanceof KillTask) {
            /*
            停止压测进程
             */
            KillTask killTask = (KillTask) message;
            long taskId = killTask.getTaskId();
            ActorRef worker = getWorker(taskId);
            if (worker != null) {
                log.info("停止任务,TaskId:" + taskId);
                log.info("任务所在worker:" + worker.toString());
                worker.tell(killTask, getSelf());
            } else {
                log.warning("试图停止不存在的任务,TaskId:" + taskId);
            }
        } else if (message instanceof GetRunningTask) {
            getSender().tell(ImmutableListMultimap.copyOf(tasks), getSelf());
        }
    }

    private ActorRef getWorker(long taskId) {
        for (ActorRef worker : tasks.keySet()) {
            for (RegisterTask task : tasks.get(worker)) {
                if (task.getTaskId() == taskId) {
                    return worker;
                }
            }
        }
        return null;
    }
}
