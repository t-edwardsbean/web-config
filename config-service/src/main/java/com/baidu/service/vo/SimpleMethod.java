package com.baidu.service.vo;

import java.util.List;

/**
 * Created by edwardsbean on 14-10-31.
 */
public class SimpleMethod {
    private String methodName;
    private List<String> methodParams;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<String> getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(List<String> methodParams) {
        this.methodParams = methodParams;
    }
}
