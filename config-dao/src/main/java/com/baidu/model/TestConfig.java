package com.baidu.model;

import java.util.List;

/**
 * Created by edwardsbean on 14-11-4.
 */
public class TestConfig {
    private int id;
    private String serviceId;
    private String serviceName;
    private String describe;
    private String groupName;
    private String requestDelay;
    private String failDelay;
    private String serverNum;
    private String threadNum;
    private List<TestMethod> testMethods;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public List<TestMethod> getTestMethods() {
        return testMethods;
    }

    public void setTestMethods(List<TestMethod> testMethods) {
        this.testMethods = testMethods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
