package com.baidu.testframework.strategy;


import com.baidu.tools.MethodParam;

import java.util.List;

/**
 * Created by edwardsbean on 14-10-15.
 */
public interface MethodSelector {
    public MethodParam getMethod(List<MethodParam> methods);
}
