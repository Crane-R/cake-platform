package com.crane.cpb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 启动测试
 *
 * @Date 2024/10/5 14:59
 * @Author Crane Resigned
 */
@RestController
@Slf4j
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping("/testIndex")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("title", "蛋糕管理系统");
        return modelAndView;
    }

}