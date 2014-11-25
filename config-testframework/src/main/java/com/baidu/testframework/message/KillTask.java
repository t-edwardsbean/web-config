package com.baidu.testframework.message;

import java.io.Serializable;

/**
 * Created by edwardsbean on 14-11-19.
 */
public class KillTask implements Serializable {
    private long taskId;

    public KillTask(long taskId) {
        this.taskId = taskId;
    }

    public KillTask() {
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
