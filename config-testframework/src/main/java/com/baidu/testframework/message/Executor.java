package com.baidu.testframework.message;

import com.baidu.testframework.config.ClientConfigProvider;
import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Created by edwardsbean on 14-11-18.
 */
public class Executor implements Serializable {
    private Future task;
    private long taskId;
    private TaskConfig config;

    public Executor(Future task, long taskId, TaskConfig config) {
        this.task = task;
        this.taskId = taskId;
        this.config = config;
    }

    public Future getTask() {

        return task;
    }

    public void setTask(Future task) {
        this.task = task;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public TaskConfig getConfig() {
        return config;
    }

    public void setConfig(TaskConfig config) {
        this.config = config;
    }
}
