package com.baidu.vo;

/**
 * Created by edwardsbean on 14-11-20.
 */
public class SimpleFile {
    private String id;
    private String time;

    public SimpleFile(String id, String time) {
        this.id = id;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
