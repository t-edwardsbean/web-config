package com.baidu.service;

import com.baidu.config.FrameworkConfig;
import com.baidu.service.vo.Status;
import com.baidu.tools.Page;
import com.baidu.tools.WebPlatTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by edwardsbean on 14-10-29.
 */
@Component("stressTestService")
public class StressTestServiceImpl implements StressTestService {
    @Autowired
    private FrameworkConfig frameworkConfig;

    @Override
    public String queryService(String serviceName) throws Exception{
        List<Page> pages = WebPlatTool.getAllService(frameworkConfig.getWebToolPlatAddrsCfg());
        return WebPlatTool.getServiceId(serviceName, pages);

    }

    @Override
    public Status download(String id) throws Exception{
        WebPlatTool.download(frameworkConfig.getWebToolPlatAddrsCfg(), id, "lib");
        Status status = new Status();
        status.setAction("download");
        status.setCode("ok");
        return status;
    }
}
