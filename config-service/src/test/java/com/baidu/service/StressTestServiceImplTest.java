package com.baidu.service;

import org.junit.Test;
import org.xeustechnologies.jcl.JarClassLoader;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class StressTestServiceImplTest {

    @Test
    public void testGetMethods() throws Exception {
        JarClassLoader jcl = new JarClassLoader();
        jcl.add("config-web/target/config-web/libext/");
        Class clazz = jcl.loadClass("com.baidu.softquery.SoftQuery" + "$Iface");
        System.out.println(clazz.getMethods()[0].getName());
    }

    //java自带的类加载器，不能用相对路径
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
}