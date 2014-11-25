package com.baidu.testframework.message;

import com.baidu.testframework.config.ClientConfigProvider;
import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;

import java.io.Serializable;

/**
 * Created by edwardsbean on 14-11-19.
 */
public class TaskConfig  implements Serializable {
    private long taskId;
    private FrameworkConfig frameworkConfig;
    private MethodConfig methodConfig;

    public TaskConfig(long taskId) {
        this.taskId = taskId;
    }

    public TaskConfig(FrameworkConfig frameworkConfig, MethodConfig methodConfig) {
        this.frameworkConfig = frameworkConfig;
        this.methodConfig = methodConfig;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public FrameworkConfig getFrameworkConfig() {
        return frameworkConfig;
    }

    public void setFrameworkConfig(FrameworkConfig frameworkConfig) {
        this.frameworkConfig = frameworkConfig;
    }

    public MethodConfig getMethodConfig() {
        return methodConfig;
    }

    public void setMethodConfig(MethodConfig methodConfig) {
        this.methodConfig = methodConfig;
    }
}
