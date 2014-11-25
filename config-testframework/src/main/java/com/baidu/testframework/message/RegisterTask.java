package com.baidu.testframework.message;

import java.io.Serializable;

/**
 * Created by edwardsbean on 14-11-10.
 */
public class RegisterTask implements Serializable {
    private long taskId;

    public RegisterTask() {}

    public RegisterTask(long taskId) {
        this.taskId = taskId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
