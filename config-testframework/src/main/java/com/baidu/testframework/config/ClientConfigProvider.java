package com.baidu.testframework.config;

/**
 * Created by edwardsbean on 14-10-15.
 */
public class ClientConfigProvider {
    private String serviceName;
    private int clientThreadCount;
    private int failedRequestDelay;
    private int requestDelay;

    public int getClientThreadCount() {
        return clientThreadCount;
    }

    public void setClientThreadCount(int clientThreadCount) {
        this.clientThreadCount = clientThreadCount;
    }

    public int getRequestDelay() {
        return requestDelay;
    }

    public void setRequestDelay(int requestDelay) {
        this.requestDelay = requestDelay;
    }

    public int getFailedRequestDelay() {
        return failedRequestDelay;
    }

    public void setFailedRequestDelay(int failedRequestDelay) {
        this.failedRequestDelay = failedRequestDelay;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
