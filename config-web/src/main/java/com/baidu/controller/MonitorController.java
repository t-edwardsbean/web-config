package com.baidu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by edwardsbean on 14-10-27.
 */
@Controller
@RequestMapping("/monitor")
public class MonitorController {
    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String list(ModelMap model) {
        return "monitor-index";
    }

    @RequestMapping(value="/save", method= RequestMethod.POST)
    public String save(ModelMap model) {
        return "monitor-index";
    }

    @RequestMapping(value="/create", method= RequestMethod.GET)
    public String create(ModelMap model) {
        return "monitor-create";
    }
}
