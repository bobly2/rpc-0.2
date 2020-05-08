package com.rpc.register.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description:
 * @author: SC19002999
 * @time: 2020/4/30 16:01
 */
@RestController
@RequestMapping("/register")
public class RegisterController {
    @RequestMapping("/index")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }
}

