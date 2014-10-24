package com.baidu.controller;

import com.baidu.model.User;
import com.baidu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by edwardsbean on 2014/8/24 0024.
 */
//SessionAttributes("currentUser"):Model中，名为currentUser的属性，放到session中
@Controller
@SessionAttributes("currentUser")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="/login", method= RequestMethod.GET)
    public String login() {

        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String login(@RequestParam String userName,@RequestParam String password,Model model) {
        boolean found = userService.userLogin(userName,password);
        if (found) {
            User user = new User();
            model.addAttribute("currentUser", user);
            return "success";
        } else {
            return "failure";
        }
    }
}
