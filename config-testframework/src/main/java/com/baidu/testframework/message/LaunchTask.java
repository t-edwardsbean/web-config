package com.baidu.testframework.message;

import java.io.Serializable;

/**
 * Created by edwardsbean on 14-11-19.
 */
public class LaunchTask implements Serializable {
    private long taskId;
    private TaskConfig config;

    public LaunchTask() {

    }

    public LaunchTask(TaskConfig config) {
        this.config = config;
    }

    public TaskConfig getConfig() {

        return config;
    }

    public void setConfig(TaskConfig config) {
        this.config = config;
    }
}
