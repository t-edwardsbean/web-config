package com.baidu.model;

import java.util.List;

public class TestConfig {
    private Integer configId;

    private String serviceId;

    private String serviceName;

    private String description;

    private String groupName;

    private Integer requestDelay;

    private Integer failDelay;

    private Integer serverNum;

    private Integer threadNum;

    private List<TestMethod> methods;

    public List<TestMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<TestMethod> methods) {
        this.methods = methods;
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public Integer getRequestDelay() {
        return requestDelay;
    }

    public void setRequestDelay(Integer requestDelay) {
        this.requestDelay = requestDelay;
    }

    public Integer getFailDelay() {
        return failDelay;
    }

    public void setFailDelay(Integer failDelay) {
        this.failDelay = failDelay;
    }

    public Integer getServerNum() {
        return serverNum;
    }

    public void setServerNum(Integer serverNum) {
        this.serverNum = serverNum;
    }

    public Integer getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(Integer threadNum) {
        this.threadNum = threadNum;
    }
}