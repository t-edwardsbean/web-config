package com.baidu.service;

import com.baidu.config.FrameworkConfig;
import com.baidu.service.vo.SimpleMethod;
import com.baidu.tools.Page;
import com.baidu.tools.ReflectionUtil;
import com.baidu.tools.WebPlatTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwardsbean on 14-10-29.
 */
@Component("stressTestService")
public class StressTestServiceImpl implements StressTestService {
    private final static Logger log = LoggerFactory.getLogger(StressTestServiceImpl.class.getName());
    public static String jarPath = System.getProperty("project.root")+ File.separator + "libext";


    @Autowired
    private FrameworkConfig frameworkConfig;

    @Override
    public String queryService(String serviceName) throws Exception{
        List<Page> pages = WebPlatTool.getAllService(frameworkConfig.getWebToolPlatAddrsCfg());
        return WebPlatTool.getServiceId(serviceName, pages);

    }

    @Override
    public File download(String id) throws Exception{
        File dir = new File(jarPath);
        dir.mkdirs();
        File jar = WebPlatTool.download(frameworkConfig.getWebToolPlatAddrsCfg(), id, jarPath);
        return jar;
    }

    @Override
    public void download(String id, int testId) {

    }

    @Override
    public List<SimpleMethod> loadService(String serviceName,String jar,boolean clean) throws ClassNotFoundException{
        String className = serviceName.split(" ")[0] + "$Iface";
        List<Method> list = ReflectionUtil.getMethods(jar,className);
        List<SimpleMethod> methods = new ArrayList<>();
        for (Method method : list) {
            SimpleMethod simpleMethod = new SimpleMethod();
            simpleMethod.setMethodName(method.getName());
            Class[] paramClass = method.getParameterTypes();
            List<String> SimpleParams = new ArrayList<>();
            for (Class param : paramClass) {
                SimpleParams.add(param.getSimpleName());
            }
            simpleMethod.setMethodParams(SimpleParams);
            methods.add(simpleMethod);
        }
        File jarFile = new File(jar);
        if (clean) {
            jarFile.delete();
        }
        return methods;
    }


}
