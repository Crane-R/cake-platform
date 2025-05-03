package com.crane.cpb.controller;

import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 用户接口
 *
 * @author Xanthos
 * @date 2025/2/17 16:57
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 账户接口
     * 需要校验是否登录，如果没登录则跳转至登录页
     *
     * @date 2025/2/17 17:17
     **/
    @GetMapping("/account")
    public ModelAndView account(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        //校验是否登录
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            modelAndView.setViewName("register");
        } else {
            modelAndView.setViewName("account");
        }
        return modelAndView;
    }
    
    @PostMapping("/register")
    public ModelAndView register(@RequestParam Map<String, String> params) {
        User user = new User();
        user.setUsername(params.get("username"));
        user.setPassword(params.get("password"));
        user.setEmail(params.get("email"));
        Boolean register = userService.register(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam Map<String, String> params, HttpServletRequest request) {
        User user = new User();
        user.setUsername(params.get("username"));
        user.setPassword(params.get("password"));
        Boolean login = userService.login(user, request);
        log.info("login: {}", login);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

}
