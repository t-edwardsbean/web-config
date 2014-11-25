package com.baidu.testframework.message;

import com.baidu.testframework.config.ClientConfigProvider;
import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;

import java.io.Serializable;


/**
 * Created by edwardsbean on 14-11-10.
 */
public class LaunchExecutor implements Serializable{
    private long taskId;
    private TaskConfig config;

    public LaunchExecutor(TaskConfig config) {
        this.config = config;
    }

    public LaunchExecutor(long taskId, TaskConfig config) {
        this.taskId = taskId;
        this.config = config;
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
