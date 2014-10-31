package com.baidu.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xeustechnologies.jcl.JarClassLoader;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by edwardsbean on 14-10-16.
 */
public class ReflectionUtil {
    public static final Logger log = LoggerFactory.getLogger(ReflectionUtil
            .class);

    /**
     * 根据配置，实例化出对应的对象，配置分为基本类和用户自定义类的配置
     * 1:基本类
     * newInstance(String.class,"hello,world")
     * 2:用户自定义类
     * newInstance(Person.class,"{name:edwardsbean,age:23,sex:MAN,job:{name:码农}}")
     *
     * @param paramClass
     * @param value
     * @return
     * @throws Exception
     */
    public static Object newInstance(Class paramClass, String value) throws Exception {
        log.debug("Parsing line:" + value);
        Object instance = null;
        if (value.startsWith("{")) {
            //实例化自定义类对象
            log.debug("New param instance,user define class:" + paramClass.getName());
            instance = paramClass.newInstance();
            Method[] methods = paramClass.getDeclaredMethods();
            value = value.trim().substring(value.indexOf("{") + 1, value.length() - 1);
            String[] members = value.split(",");
            for (String member : members) {
                //成员变量赋值
                String memberClassString = member.trim().substring(0, member.indexOf(":")).trim();
                log.debug("Parse member->" + memberClassString);
                String memberValueString = member.substring(member.indexOf(":") + 1).trim();
                log.debug("Parse member value->" + memberValueString);
                //调用instance.setMember方法
                for (Method method : methods) {
                    if (method.getName().toLowerCase().equals("set" + memberClassString.toLowerCase())) {
                        //通过setMember方法的参数来获得member的Class
                        Class[] methodclasses = method.getParameterTypes();
                        //成员变量为嵌套复杂类型
                        if (memberValueString.startsWith("{")) {
                            method.invoke(instance, newInstance(methodclasses[0], memberValueString));
                            //成员变量为基本类型
                        } else {
                            method.invoke(instance, newBasicAndPrimitiveInstance(methodclasses[0], memberValueString));
                        }
                        log.debug("Invoke->" + paramClass.getSimpleName() + "." + method.getName());
                    }
                }
            }
        } else {
            log.debug("New param instance,basic class:" + paramClass.getSimpleName());
            instance = newBasicAndPrimitiveInstance(paramClass, value);
        }

        return instance;
    }

    public static Object newBasicAndPrimitiveInstance(Class paramClass, String value) throws Exception {
        Object instance;
        //原始类型，如int
        if (paramClass.isPrimitive()) {
            instance = Integer.class.getConstructor(String.class).newInstance(value);
            //基本类型，如String
        } else if (isBasicType(paramClass)) {
            instance = paramClass.getConstructor(String.class).newInstance(value);
            //Enum类型
        } else if (paramClass.isEnum()) {
            instance = Enum.valueOf(paramClass, value);
        } else {
            return null;
        }
        return instance;
    }

    /**
     * TODO 更多基本类型？
     *
     * @param clazz
     * @return
     */
    public static boolean isBasicType(Class clazz) {
        if (clazz.getSimpleName().equals("String")) {
            return true;
        }
        return false;
    }


    /**
     * 给定一个类clazz，以及需要测试的方法及参数配置，配置分为基本类和用户自定义类的配置
     * Map为<方法名，List<方法参数>>
     *
     * @param clazz
     * @param methods
     * @return
     */
    public static List<MethodParam> getMethodReflection(Class clazz, Map<String, List<String>> methods) throws Exception {
        List<MethodParam> reflectMethods = new ArrayList<MethodParam>();
        Method method[] = clazz.getMethods();
        for (int i = 0; i < method.length; ++i) {
            String methodName = method[i].getName();
            List<String> paramList = methods.get(methodName);
            //获取待测试方法的method反射
            if (paramList != null) {
                /**
                 * 检查方法类型，并实例化
                 */
                Class<?> paramClassList[] = method[i].getParameterTypes();
                Object paramInstances[] = new Object[paramList.size()];
                int paramIndex = 0;
                for (Class paramClass : paramClassList) {
                    String paramValue = paramList.get(paramIndex);
                    paramInstances[paramIndex] = newInstance(paramClass, paramValue);
                    paramIndex++;
                }
                MethodParam methodParam = new MethodParam();
                methodParam.setMethod(method[i]);
                methodParam.setMethodParam(paramInstances);
                reflectMethods.add(methodParam);
                log.debug(methodParam.toString());
            }
        }
        return reflectMethods;
    }

    /**
     * java8之前都无法获取参数名
     * @param jar
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static List<Method> getMethods(String jar, String className) throws ClassNotFoundException {
        JarClassLoader jcl = new JarClassLoader();
        log.info("加载接口包：" + jar);
        jcl.add(jar);
        Class clazz = Class.forName(className, true, jcl);
        List<Method> list = new ArrayList();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            list.add(method);
        }
        return list;

    }
}
