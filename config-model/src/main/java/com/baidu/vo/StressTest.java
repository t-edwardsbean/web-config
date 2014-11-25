package com.baidu.vo;


import java.util.List;
import java.util.Map;

/**
 * Created by edwardsbean on 14-11-3.
 */
public class  StressTest {
    private String serviceName;
    private String describe;
    private String groupName;
    private String serviceId;
    /**
     * 测试方法，以及参数集合
     */
    private Map<String,List<String>> methods;
    private Integer requestDelay;
    private Integer failDelay;
    private Integer serverNum;
    private Integer threadNum;

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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
