package com.baidu.service;

import com.baidu.config.FrameworkConfig;
import com.baidu.mapper.MethodParamMapper;
import com.baidu.mapper.TestConfigMapper;
import com.baidu.mapper.TestMethodMapper;
import com.baidu.model.MethodParam;
import com.baidu.model.TestConfig;
import com.baidu.model.TestMethod;
import com.baidu.service.exception.BusinessException;
import com.baidu.service.vo.SimpleMethod;
import com.baidu.testframework.app.ActorManager;
import com.baidu.testframework.config.ClientConfigProvider;
import com.baidu.testframework.config.MethodConfig;
import com.baidu.tools.Page;
import com.baidu.tools.ReflectionUtil;
import com.baidu.tools.WebPlatTool;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by edwardsbean on 14-10-29.
 */
@Component("stressTestService")
public class StressTestServiceImpl implements StressTestService {
    private final static Logger log = LoggerFactory.getLogger(StressTestServiceImpl.class.getName());
    public static String webRoot = System.getProperty("project.root");
    public static String jarPath = webRoot + File.separator + "libext";

    @Autowired
    private ActorManager actorManager;

    @Autowired
    private FrameworkConfig frameworkConfig;

    @Autowired
    private TestConfigMapper testConfigMapper;

    @Autowired
    private TestMethodMapper testMethodMapper;

    @Autowired
    private MethodParamMapper methodParamMapper;

    @Override
    public String queryService(String serviceName) throws Exception {
        List<Page> pages = WebPlatTool.getAllService(frameworkConfig.getWebToolPlatAddrsCfg());
        return WebPlatTool.getServiceId(serviceName, pages);

    }

    //libext/服务id/服务id.zip + gen-java + 打包的jar
    @Override
    public File download(String id) throws Exception {
        String interfacePath = jarPath + File.separator + id;
        File dir = new File(interfacePath);
        dir.mkdirs();
        File zip = WebPlatTool.download(frameworkConfig.getWebToolPlatAddrsCfg(), id, interfacePath);
        return parseZip2Jar(zip,interfacePath);
    }

    /**
     * 加锁，以免多个用户同时操作，导致maven打包工程混乱
     * @param zip
     * @param interfacePath
     * @return
     */
    private synchronized File parseZip2Jar(File zip,String interfacePath) {
        try {
            WebPlatTool.unZipToFolder(zip.getAbsolutePath(),interfacePath);
            String sourceFile = interfacePath + File.separator + "gen-java";
            String shell = webRoot + "executor/package.sh";
            String result = new ProcessExecutor()
                    .command(shell, sourceFile, interfacePath)
                    .destroyOnExit()
                    .readOutput(true)
                    .redirectOutput(Slf4jStream.of(LoggerFactory.getLogger(getClass().getName() + ".MavenProcess")).asInfo())
                    .execute().outputUTF8();
            System.out.println("maven结果:" + result);
        } catch (Exception e) {
            throw new BusinessException("maven编译打包接口包出错:" + e.getMessage());
        }
        return new File(interfacePath + File.separator + "thrift-package-1.0-SNAPSHOT.jar");

    }

    @Override
    public File download(String id, int testId) throws Exception {
        String path = jarPath + File.separator + testId;
        File dir = new File(path);
        dir.mkdirs();
        return WebPlatTool.download(frameworkConfig.getWebToolPlatAddrsCfg(), id, path);
    }

    @Override
    public List<SimpleMethod> loadService(String serviceName, String jar, boolean clean) {
        String className = serviceName.split(" ")[0] + "$Iface";
        List<Method> list = null;
        try {
            list = ReflectionUtil.getMethods(jar, className);
        } catch (ClassNotFoundException e) {
            throw new BusinessException("找不到接口：" + e.getMessage());

        }
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
            try {
                FileUtils.deleteDirectory(jarFile.getParentFile());
            } catch (IOException e) {
                log.warn("清理接口编译失败",e);
            }
        }
        return methods;
    }

    @Override
    public void save(TestConfig testConfig) {
        testConfigMapper.insert(testConfig);
        for (TestMethod method : testConfig.getMethods()) {
            method.setConfigId(testConfig.getConfigId());
            testMethodMapper.insert(method);
            for (MethodParam param : method.getParams()) {
                param.setMethodId(method.getMethodId());
                methodParamMapper.insert(param);
            }
        }
    }

    @Override
    public long run(int testId) throws Exception {
        /*
        TODO 添加测试参数
         */
        TestConfig testConfig = testConfigMapper.selectByPrimaryKey(testId);
        List<TestMethod> testMethods = testConfig.getMethods();
        Map<String,List<String>> methods = new HashMap<>();
        for (TestMethod testMethod : testMethods) {
            List<String> params = new ArrayList<>();
            for (MethodParam param : testMethod.getParams()) {
                params.add(param.getParamValue());
            }
            methods.put(testMethod.getMethodName(),params);
        }
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setMethods(methods);
        com.baidu.testframework.config.FrameworkConfig testframework = new com.baidu.testframework.config.FrameworkConfig();
        testframework.setRequestDelay(testConfig.getRequestDelay());
        testframework.setReportName(testConfig.getGroupName());
        testframework.setClientServerCount(testConfig.getServerNum());
        testframework.setClientThreadCount(testConfig.getThreadNum());
        testframework.setFailedRequestDelay(testConfig.getRequestDelay());
        testframework.setGangliaAddrsCfg(frameworkConfig.getGangliaAddrsCfg());
        testframework.setRegAddrsCfg(frameworkConfig.getRegAddrsCfg());
        testframework.setServiceName(testConfig.getServiceName());
        testframework.setWebToolPlatAddrsCfg(frameworkConfig.getWebToolPlatAddrsCfg());

        log.debug("web提交任务,workDir:" + webRoot);
        return actorManager.run(testframework, methodConfig);
    }

    @Override
    public List<TestConfig> findAllSimple(int page) {
        return testConfigMapper.findAllSimple();

    }

    @Override
    public void stop(long taskId) {
        actorManager.stop(taskId);
    }

    @Override
    public void delete(int id) {
        testConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TestConfig> findAllSimple() {
        return testConfigMapper.findAllSimple();
    }
}
