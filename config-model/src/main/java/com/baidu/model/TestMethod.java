package com.baidu.model;

import java.util.List;

public class TestMethod {
    private Integer methodId;

    private String methodName;

    private Integer configId;

    private List<MethodParam> params;

    public List<MethodParam> getParams() {
        return params;
    }

    public void setParams(List<MethodParam> params) {
        this.params = params;
    }

    public Integer getMethodId() {
        return methodId;
    }

    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }
}