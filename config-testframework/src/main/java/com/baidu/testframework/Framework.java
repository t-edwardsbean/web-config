package com.baidu.testframework;

import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;
import com.baidu.testframework.core.FrameworkManager;
import com.baidu.tools.Page;
import com.baidu.tools.WebPlatTool;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.List;


/**
 * Created by edwardsbean on 14-10-14.
 */
public class Framework {
    private final static Logger log = LoggerFactory.getLogger(Framework.class.getName());
    public static ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        try {
            log.info("Start test framework");
            context = new ClassPathXmlApplicationContext("basic.xml");
            MethodConfig config = (MethodConfig) context.getBean("config");
            FrameworkConfig frameworkConfig = (FrameworkConfig) context.getBean("frameworkConfig");

            //下载Thrift接口包
            log.info("Checking interface jar");
            String serviceName = frameworkConfig.getServiceName();
            Preconditions.checkNotNull(serviceName, "Service name not configured");
            String webToolAddrs = frameworkConfig.getWebToolPlatAddrsCfg();
            Preconditions.checkNotNull(webToolAddrs, "Web tool platform address not configured");
            List<Page> pages = WebPlatTool.getAllService(webToolAddrs);
            String id = WebPlatTool.getServiceId(serviceName, pages);
            final File jar = WebPlatTool.download(webToolAddrs, id, "lib");

            //检查框架配置
            log.info("Checking configuation");
            frameworkConfig.checkConfig();
            log.info("Checking method configuration");
            config.setServiceInterface(frameworkConfig.getTestClazz());
            config.checkConfig();
            FrameworkManager frameworkManager = new FrameworkManager(config, frameworkConfig);
            //启动框架
            frameworkManager.initStatistics();
            frameworkManager.startApplication();
            frameworkManager.startReport();
            final FrameworkManager reference = frameworkManager;
            Runtime.getRuntime().addShutdownHook(new Thread("agent-shutdown-hook") {
                @Override
                public void run() {
                    jar.delete();
                    reference.stopApplication();
                }
            });
        } catch (Exception e) {
            log.error("A fatal error occurred while running. Exception follows.",
                    e);
        }
    }


}
