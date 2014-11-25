package com.baidu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by edwardsbean on 14-10-28.
 */
@Component("frameworkConfig")
public class FrameworkConfig {
    @Value("${dsf.report.ganglia.adds}")
    private String gangliaAddrsCfg;

    @Value("${dsf.register.server.adds}")
    private String regAddrsCfg;

    @Value("${dsf.webtoolplat.adds}")
    private String webToolPlatAddrsCfg;

    @Value("${dsf.mode}")
    private String mode;

    @Value("${dsf.stress.local}")
    private String local;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getGangliaAddrsCfg() {
        return gangliaAddrsCfg;
    }

    public void setGangliaAddrsCfg(String gangliaAddrsCfg) {
        this.gangliaAddrsCfg = gangliaAddrsCfg;
    }

    public String getRegAddrsCfg() {
        return regAddrsCfg;
    }

    public void setRegAddrsCfg(String regAddrsCfg) {
        this.regAddrsCfg = regAddrsCfg;
    }

    public String getWebToolPlatAddrsCfg() {
        return webToolPlatAddrsCfg;
    }

    public void setWebToolPlatAddrsCfg(String webToolPlatAddrsCfg) {
        this.webToolPlatAddrsCfg = webToolPlatAddrsCfg;
    }
}
