package com.baidu.model;

public class MethodParam {
    private Integer methodParamId;

    private String paramClass;

    private String paramValue;

    private Integer methodId;

    public Integer getMethodParamId() {
        return methodParamId;
    }

    public void setMethodParamId(Integer methodParamId) {
        this.methodParamId = methodParamId;
    }

    public String getParamClass() {
        return paramClass;
    }

    public void setParamClass(String paramClass) {
        this.paramClass = paramClass == null ? null : paramClass.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public Integer getMethodId() {
        return methodId;
    }

    public void setMethodId(Integer methodId) {
        this.methodId = methodId;
    }
}