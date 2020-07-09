package com.qfedu.controller;

import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Jayus
 * @create 2020-07-09-5:52 下午
 */

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/regist", method = RequestMethod.POST)
    public String regist(User user) {
        if(userService.regist(user)){
            return "redirect:/html/userLogin.html";
        } else {
            return "redirect:/html/userRegist.html";
        }
    }


}
