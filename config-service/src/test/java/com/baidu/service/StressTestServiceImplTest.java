package com.baidu.service;

import com.baidu.mapper.TestConfigMapper;
import com.baidu.model.MethodParam;
import com.baidu.model.TestConfig;
import com.baidu.model.TestMethod;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xeustechnologies.jcl.JarClassLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:service.xml","classpath*:dao.xml"})
public class StressTestServiceImplTest {
    @Autowired
    StressTestService stressTestService;

    @Test
    public void testGetMethods() throws Exception {
        JarClassLoader jcl = new JarClassLoader();
        jcl.add("config-web/target/config-web/libext/");
        Class clazz = jcl.loadClass("com.baidu.softquery.SoftQuery" + "$Iface");
        System.out.println(clazz.getMethods()[0].getName());
    }

    //java自带的类加载器，不能用相对路径?
    @Test
    public void testSystemClassLoader() throws Exception {
        URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        File file = new File("config-web/target/config-web/libext/thrift.jar");
        Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
        add.setAccessible(true);
        add.invoke(classloader, new Object[] { file.toURI().toURL() });
        Class clazz = Class.forName("com.baidu.softquery.SoftQuery" + "$Iface");
        System.out.println(clazz.getMethods()[0].getName());
    }

    @Test
    public void testJarEntry() throws Exception {
        File file = new File("config-web/target/config-web/libext/thrift.jar");
        JarFile jarFile = new JarFile(file);
        Enumeration enums =  jarFile.entries();
        while (enums.hasMoreElements()) {
            JarEntry e = (JarEntry)enums.nextElement();
            System.out.println(e.getName());
        }
    }

    @Test
    public void testInsert() throws Exception {
        TestConfig testConfig = new TestConfig();
        testConfig.setServiceName("test v1.0");
        testConfig.setDescription("测试描述");
        testConfig.setFailDelay(1000);
        testConfig.setGroupName("com.baidu");
        testConfig.setRequestDelay(1000);
        testConfig.setServerNum(2);
        testConfig.setServiceId("123123123");
        testConfig.setThreadNum(1);
        List<MethodParam> params = new ArrayList<>();
        List<MethodParam> params2 = new ArrayList<>();
        MethodParam param = new MethodParam();
        MethodParam param2 = new MethodParam();
        param.setParamClass("com.One");
        param2.setParamClass("com.Two");
        param.setParamValue("OneValue");
        param2.setParamValue("TwoValue");
        params.add(param);
        params.add(param2);
        params2.add(param2);
        params2.add(param2);
        List<TestMethod> methods = new ArrayList<>();
        TestMethod testMethod = new TestMethod();
        TestMethod testMethod2 = new TestMethod();
        testMethod.setMethodName("haha");
        testMethod2.setMethodName("haha2");
        testMethod.setParams(params);
        testMethod2.setParams(params2);
        methods.add(testMethod);
        methods.add(testMethod2);
        testConfig.setMethods(methods);
        stressTestService.save(testConfig);
    }

    @Test
    public void testRun() throws Exception {
        stressTestService.run(1);
    }
}