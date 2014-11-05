package com.baidu.vo;

import com.baidu.service.vo.SimpleMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by edwardsbean on 14-11-3.
 */
public class StressTest {
    private String serviceName;
    private String describe;
    private String groupName;
    /**
     * 测试方法，以及参数集合
     */
    private Map<String,List<String>> methods;
    private String requestDelay;
    private String failDelay;
    private String serverNum;
    private String threadNum;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Map<String, List<String>> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, List<String>> methods) {
        this.methods = methods;
    }

    public String getRequestDelay() {
        return requestDelay;
    }

    public void setRequestDelay(String requestDelay) {
        this.requestDelay = requestDelay;
    }

    public String getFailDelay() {
        return failDelay;
    }

    public void setFailDelay(String failDelay) {
        this.failDelay = failDelay;
    }

    public String getServerNum() {
        return serverNum;
    }

    public void setServerNum(String serverNum) {
        this.serverNum = serverNum;
    }

    public String getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(String threadNum) {
        this.threadNum = threadNum;
    }

    @Override
    public String toString() {
        return "StressTest{" +
                "serviceName='" + serviceName + '\'' +
                ", describe='" + describe + '\'' +
                ", groupName='" + groupName + '\'' +
                ", methods=" + methods +
                ", requestDelay='" + requestDelay + '\'' +
                ", failDelay='" + failDelay + '\'' +
                ", serverNum='" + serverNum + '\'' +
                ", threadNum='" + threadNum + '\'' +
                '}';
    }
}
