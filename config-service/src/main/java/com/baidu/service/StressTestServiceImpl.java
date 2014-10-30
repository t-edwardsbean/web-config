package com.baidu.service;

import com.baidu.config.FrameworkConfig;
import com.baidu.tools.Page;
import com.baidu.tools.WebPlatTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xeustechnologies.jcl.JarClassLoader;

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
    public void download(String id) throws Exception{
        File dir = new File(jarPath);
        dir.mkdirs();
        WebPlatTool.download(frameworkConfig.getWebToolPlatAddrsCfg(), id, jarPath);
    }

    @Override
    public void download(String id, int testId) {

    }

    @Override
    public List<String> getMethods(String serviceName,String id) {
        JarClassLoader jcl = new JarClassLoader();
        String jar = jarPath + File.separator + id + ".jar";
        log.info("加载接口包：" + jar);
        jcl.add(jar);
        try {
            Class clazz = Class.forName(serviceName.split(" ")[0] + "$Iface",true,jcl);
            List<String> list = new ArrayList();
            Method[] methods = clazz.getMethods();
            for (Method method:methods) {
                list.add(method.getName());
            }
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
