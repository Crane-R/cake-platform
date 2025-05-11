package com.crane.cpb.controller;

import cn.hutool.crypto.SecureUtil;
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
@CrossOrigin
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
        user.setPassword(SecureUtil.md5(params.get("password")));
        user.setEmail(params.get("email"));
        Boolean register = userService.register(user);
        ModelAndView modelAndView = new ModelAndView();
        if (!register) {
            modelAndView.addObject("message", "用户名已存在");
            modelAndView.setViewName("register");
        } else {
            modelAndView.addObject("message", "注册成功");
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam Map<String, String> params, HttpServletRequest request) {
        User user = new User();
        user.setUsername(params.get("username"));
        user.setPassword(SecureUtil.md5(params.get("password")));
        Boolean login = userService.login(user, request);
        ModelAndView modelAndView = new ModelAndView();
        if (login) {
            modelAndView.setViewName("redirect:/index");
        } else {
            modelAndView.setViewName("register");
            modelAndView.addObject("message", "用户名或密码错误");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        userService.logout(request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

    /**
     * 后台登录
     **/
    @GetMapping("/api/backgroundLogin")
    public Boolean login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(SecureUtil.md5(password));
        return userService.login(user, request);
    }

    /**
     * 后台退出
     **/
    @GetMapping("/api/logout")
    public void backgroundLogout(HttpServletRequest request) {
        userService.logout(request);
    }

}
