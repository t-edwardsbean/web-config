package com.baidu.controller;

import com.baidu.config.FrameworkConfig;
import com.baidu.model.MethodParam;
import com.baidu.model.TestConfig;
import com.baidu.model.TestMethod;
import com.baidu.service.StressTestService;
import com.baidu.vo.Msg;
import com.baidu.vo.ServiceQuery;
import com.baidu.vo.StressTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by edwardsbean on 14-10-27.
 */
@Controller
@RequestMapping("/stress")
public class StressTestController {
    private final static Logger log = LoggerFactory.getLogger(StressTestController.class.getName());
    public static String jarPath = System.getProperty("project.root")+ File.separator + "libext";

    @Autowired
    private FrameworkConfig frameworkConfig;
    @Autowired
    private StressTestService stressTestService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "stress-create";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String list() {
        return "stress-index";
    }

    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public Msg checkService(String serviceName) throws Exception {
        String id = stressTestService.queryService(serviceName);
        Msg msg = new Msg();
        ServiceQuery serviceQuery = new ServiceQuery();
        serviceQuery.setServiceName(serviceName);
        serviceQuery.setServiceId(id);
        msg.setReturnData(serviceQuery);
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/load", method = RequestMethod.POST)
    public Msg loadService(String serviceId,String serviceName) throws Exception {
        String location;
        List list;
        if (frameworkConfig.getMode().equals("debug")){
            location = jarPath + File.separator + "thrift.jar";
            list = stressTestService.loadService(serviceName,location,false);
        } else {
            File jar = stressTestService.download(serviceId);
            location = jar.getAbsolutePath();
            list = stressTestService.loadService(serviceName,location,true);
        }
        Msg msg = new Msg();
        msg.setReturnData(list);
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Msg save(StressTest stressTest) {
        Msg msg = new Msg();
        TestConfig config = new TestConfig();
        config.setDescription(stressTest.getDescribe());
        config.setFailDelay(stressTest.getFailDelay());
        config.setGroupName(stressTest.getGroupName());
        config.setRequestDelay(stressTest.getRequestDelay());
        config.setServerNum(stressTest.getServerNum());
        config.setServiceName(stressTest.getServiceName());
        config.setThreadNum(stressTest.getThreadNum());
        config.setServiceId(stressTest.getServiceId());
        Map<String,List<String>> voMethods =  stressTest.getMethods();
        List<TestMethod> methods = new ArrayList<>();
        if (voMethods != null) {
            for (Map.Entry<String, List<String>> set : voMethods.entrySet()) {
                List<MethodParam> params = new ArrayList<>();
                TestMethod method = new TestMethod();
                method.setMethodName(set.getKey());
                for (String pv : set.getValue()) {
                    MethodParam param = new MethodParam();
                    param.setParamValue(pv);
                    params.add(param);
                }
                method.setParams(params);
                methods.add(method);
            }
        } else {
            msg.setCode("-1");
            msg.setMsg("测试方法参数值未设置");
        }
        config.setMethods(methods);
        stressTestService.save(config);
        return msg;
    }

/**
 * 内网速度快，暂时不用进度条
 */
//    @ResponseBody
//    @RequestMapping(value="/progress", method= RequestMethod.POST)
//    public String query(@ModelAttribute("serviceName")ServiceQuery serviceQuery) {
//        return "";
//    }


}
