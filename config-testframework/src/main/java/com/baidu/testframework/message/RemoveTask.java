package com.baidu.testframework.message;

import java.io.Serializable;

/**
 * Created by edwardsbean on 14-11-10.
 */
public class RemoveTask implements Serializable {
    private long taskId;

    public RemoveTask(long taskId) {
        this.taskId = taskId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
