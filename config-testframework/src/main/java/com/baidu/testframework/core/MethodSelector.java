package com.baidu.testframework.core;


import java.util.List;

/**
 * Created by edwardsbean on 14-10-15.
 */
public interface MethodSelector {
    public MethodParam getMethod(List<MethodParam> methods);
}
