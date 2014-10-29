package com.baidu.test.config;

import com.baidu.tools.MethodParam;
import com.baidu.tools.ReflectionUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by edwardsbean on 14-10-14.
 */
@Component
public class MethodConfig {
    //方法名：参数值
    private Map<String, List<String>> methods;
    private boolean paramChecked = false;
    private List<MethodParam> reflectMethods;
    private Class serviceInterface;

    public void setServiceInterface(Class serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public List<MethodParam> getReflectMethods() {
        if (paramChecked == true) {
            return reflectMethods;
        } else
            return null;
    }

    public void checkConfig() throws Exception {
        Preconditions.checkNotNull(methods,
                "Method list not configured");
        Preconditions.checkNotNull(serviceInterface,
                "Service interface not configured");
        reflectMethods = ReflectionUtil.getMethodReflection(serviceInterface, methods);
        paramChecked = true;
    }

    public Map<String, List<String>> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, List<String>> methods) {
        this.methods = methods;
    }
}
