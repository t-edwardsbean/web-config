package com.baidu.testframework.config;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class FrameworkConfig implements Serializable {
    private String serviceName;
    private String regAddrsCfg;
    private int failedRequestDelay;
    private int clientServerCount;
    private int clientThreadCount;
    private int requestDelay;
    private String gangliaAddrsCfg;
    private String reportName;
    //被测试的接口类
    private Class testClazz;
    private String webToolPlatAddrsCfg;

    public String getWebToolPlatAddrsCfg() {
        return webToolPlatAddrsCfg;
    }

    public void setWebToolPlatAddrsCfg(String webToolPlatAddrsCfg) {
        this.webToolPlatAddrsCfg = webToolPlatAddrsCfg;
    }

    public String getGangliaAddrsCfg() {
        return gangliaAddrsCfg;
    }

    public void setGangliaAddrsCfg(String gangliaAddrsCfg) {
        this.gangliaAddrsCfg = gangliaAddrsCfg;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public Class getTestClazz() {
        return testClazz;
    }

    public void setTestClazz(Class testClazz) {
        this.testClazz = testClazz;
    }

    public String getServiceName() {
        return serviceName;
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

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getClientServerCount() {
        return clientServerCount;
    }

    public void setClientServerCount(int clientServerCount) {
        this.clientServerCount = clientServerCount;
    }

    public int getClientThreadCount() {
        return clientThreadCount;
    }

    public void setClientThreadCount(int clientThreadCount) {
        this.clientThreadCount = clientThreadCount;
    }

    public String getRegAddrsCfg() {
        return regAddrsCfg;
    }

    public void setRegAddrsCfg(String regAddrsCfg) {
        this.regAddrsCfg = regAddrsCfg;
    }

    public void checkConfig() throws Exception {
        Preconditions.checkNotNull(serviceName,
                "tf.register.service.name is not configured");
        try {
            testClazz = Class.forName(serviceName.split(" ")[0] + "$Iface");
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Thrift接口包不存在");
        }
        if (gangliaAddrsCfg != null) {
            if (reportName == null) {
                reportName = "StressTest";
            }
        }
        Preconditions.checkNotNull(regAddrsCfg,
                "tf.register.server.adds is not configured");
        Preconditions.checkArgument(failedRequestDelay > 0,
                "tf.client.failed.delay is not configured");
        Preconditions.checkArgument(clientThreadCount > 0,
                "tf.client.thread is not configured");
        Preconditions.checkArgument(clientServerCount > 0,
                "tf.client.server is not configured");
        Preconditions.checkArgument(requestDelay > 0,
                "tf.client.request.delay is not configured");

    }
}
