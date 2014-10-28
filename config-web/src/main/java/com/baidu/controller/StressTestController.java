package com.baidu.controller;

import com.baidu.vo.ServiceQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


/**
 * Created by edwardsbean on 14-10-27.
 */
@Controller
@RequestMapping("/stress")
@SessionAttributes("serverName")
public class StressTestController {
    @RequestMapping(value="/create", method= RequestMethod.GET)
    public String create(ModelMap model) {
        return "stress-create";
    }

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String list(ModelMap model) {
        return "stress-index";
    }

    @ResponseBody
    @RequestMapping(value="/test", method= RequestMethod.POST)
    public String download(@RequestParam String serviceName,@ModelAttribute("serviceName")ServiceQuery serviceQuery, Model model) {
        if (serviceQuery != null) {
            return "ok";
        }else {
            /**
             * TODO 下载
             */
            serviceQuery = new ServiceQuery();
            serviceQuery.setServiceName(serviceName);
            model.addAttribute("serviceName");
            return "downloading";
        }
    }

    @ResponseBody
    @RequestMapping(value="/query", method= RequestMethod.POST)
    public String query(@ModelAttribute("serviceName")ServiceQuery serviceQuery) {
        /**
         * TODO 查询进度
         */
        return "";
    }



}
