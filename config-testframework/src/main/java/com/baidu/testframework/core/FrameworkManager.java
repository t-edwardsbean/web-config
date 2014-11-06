package com.baidu.testframework.core;


import com.baidu.dsf.DSFramework;
import com.baidu.dsf.adaptor.ServiceAdaptor;
import com.baidu.testframework.config.ClientConfigProvider;
import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;
import com.baidu.testframework.pool.ClientServer;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.ganglia.GangliaReporter;
import info.ganglia.gmetric4j.gmetric.GMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class FrameworkManager {
    private final static Logger log = LoggerFactory.getLogger(FrameworkManager.class.getName());
    private MethodConfig methodConfig;
    private FrameworkConfig frameworkConfig;
    private ClientConfigProvider clientConfigProvider;
    public static final MetricRegistry metricRegistry = new MetricRegistry();
    public static Timer timer;
    public static GMetric ganglia;
    public static Meter failCount;

    public FrameworkManager(MethodConfig methodConfig, FrameworkConfig frameworkConfig) {
        this.methodConfig = methodConfig;
        this.frameworkConfig = frameworkConfig;
        this.clientConfigProvider = new ClientConfigProvider();
        clientConfigProvider.setServiceName(frameworkConfig.getServiceName());
        clientConfigProvider.setClientThreadCount(frameworkConfig.getClientThreadCount());
        clientConfigProvider.setFailedRequestDelay(frameworkConfig.getFailedRequestDelay());
        clientConfigProvider.setRequestDelay(frameworkConfig.getRequestDelay());
    }

    public void startApplication() {
        init();
        MethodSelector methodSelector = new RandomMethodSelector();
        for (int index = 0; index < frameworkConfig.getClientServerCount(); index++) {
            log.info("Create clientServer" + index);
            ClientServer clientServer = new ClientServer("clientServer" + index, methodConfig, methodSelector, clientConfigProvider);
            clientServer.run();
        }
    }

    public void initStatistics() throws IOException {
        String gangliaAddrs = frameworkConfig.getGangliaAddrsCfg();
        //开启ganglia汇报
        if (gangliaAddrs != null) {
            InetSocketAddress[] address = parseSocketAddrArray(gangliaAddrs);
            try {
                ganglia = new GMetric(address[0].getHostName(), address[0].getPort(), GMetric.UDPAddressingMode.MULTICAST, 1);
            } catch (IOException e) {
                log.error("连接ganglia服务器失败", e);
                throw e;
            }
        }
        //qps
        timer = metricRegistry.timer(MetricRegistry.name("test-framework", frameworkConfig.getReportName(), "request"));

        //fail count
        failCount = metricRegistry.meter(MetricRegistry.name("test-framework", frameworkConfig.getReportName(), "fail"));
    }

    public void stopApplication() {
        /**
         * TODO
         */
    }

    public void init() {
        InetSocketAddress[] addrs = parseSocketAddrArray(frameworkConfig.getRegAddrsCfg());
        DSFramework.start(addrs, "test-framework", 200);
        ServiceAdaptor.subscribeService(frameworkConfig.getServiceName());
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            log.error("Sleep exception:", e);
        }
    }

    public void startReport() {
        GangliaReporter gangliaReporter = GangliaReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build(ganglia);
        gangliaReporter.start(1, TimeUnit.SECONDS);

        //注册metrics,每个1秒打印metrics到控制台
        ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        consoleReporter.start(1, TimeUnit.SECONDS);
    }

    private InetSocketAddress[] parseSocketAddrArray(String cfgStr) {
        String[] cfgList = cfgStr.split(";");
        int count = cfgList.length;
        InetSocketAddress[] addrs = new InetSocketAddress[count];
        for (int i = 0; i < count; ++i) {
            String cfg = cfgList[i];
            String[] strs = cfg.split(":");
            String host = strs[0];
            int port = Integer.parseInt(strs[1]);
            addrs[i] = new InetSocketAddress(host, port);
        }
        return addrs;
    }


}
