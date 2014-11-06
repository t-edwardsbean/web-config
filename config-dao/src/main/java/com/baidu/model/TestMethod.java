package com.baidu.model;

import java.util.List;

/**
 * Created by edwardsbean on 14-11-4.
 */
public class TestMethod {
    private int id;
    private String methodName;
    private List<MethodParam> methodParams;

    public List<MethodParam> getMethodParams() {
        return methodParams;
    }

    public void setMethodParams(List<MethodParam> methodParams) {
        this.methodParams = methodParams;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
