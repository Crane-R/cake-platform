package com.crane.cpb.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.UserService;
import com.crane.cpb.util.MessageUtil;
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
    private final ShoppingCartService shoppingCartService;

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
        User user = userService.currentUser(request);
        if (user == null) {
            modelAndView.setViewName("register");
        } else {
            modelAndView.addObject("currentUser", user);
            userService.setAccountData(user, modelAndView);
            shoppingCartService.setCartData(request, modelAndView);
            modelAndView.setViewName("account");
        }
        return modelAndView;
    }

    /**
     * 修改密码
     **/
    @PostMapping("/updatePassword")
    public ModelAndView updatePassword(HttpServletRequest request) {
        User user = userService.currentUser(request);
        if (user == null) {
            return new ModelAndView("register");
        }
        String newPassword = request.getParameter("newPassword");
        user.setPassword(SecureUtil.md5(newPassword));
        ModelAndView modelAndView = new ModelAndView();
        request.getSession().setAttribute("isUpdatePassword", true);
        userService.logout(request);
        modelAndView.setViewName("redirect:/jump/register");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@RequestParam Map<String, String> params, HttpServletRequest request) {
        String username = params.get("username");
        String password = params.get("password");
        if (StrUtil.hasEmpty(username, password)) {
            ModelAndView modelAndView = new ModelAndView();
            MessageUtil.setErrorMessage(modelAndView, "请输入用户名和密码");
            modelAndView.setViewName("register");
            return modelAndView;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(SecureUtil.md5(password));
        user.setEmail(params.get("email"));
        Boolean register = userService.register(user);
        ModelAndView modelAndView = new ModelAndView();
        if (!register) {
            MessageUtil.setErrorMessage(modelAndView, "用户名已存在");
            modelAndView.setViewName("register");
        } else {
            MessageUtil.setMessage(modelAndView, "注册成功，请输入账号登录");
            modelAndView.setViewName("register");
        }
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam Map<String, String> params, HttpServletRequest request) {
        User user = new User();
        user.setUsername(params.get("username"));
        user.setPassword(SecureUtil.md5(params.get("password")));
        User login = userService.login(user, request);
        ModelAndView modelAndView = new ModelAndView();
        if (login != null) {
            MessageUtil.setStrideMessage(request, "登录成功");
            modelAndView.setViewName("redirect:/index");
        } else {
            modelAndView.setViewName("register");
            MessageUtil.setMessage(modelAndView, "用户名或密码错误");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        userService.logout(request);
        ModelAndView modelAndView = new ModelAndView();
        MessageUtil.setStrideMessage(request, "已退出登录");
        modelAndView.setViewName("redirect:/index");
        return modelAndView;
    }

    /**
     * 后台登录
     * 返回身份标识
     **/
    @GetMapping("/api/backgroundLogin")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(SecureUtil.md5(password));
        User login = userService.login(user, request);
        if (login == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        return switch (login.getIdentity()) {
            case 0 -> "customer";
            case 1 -> "merchant";
            case 2 -> "admin";
            default -> "error";
        };
    }

    /**
     * 后台退出
     **/
    @GetMapping("/api/logout")
    public void backgroundLogout(HttpServletRequest request) {
        userService.logout(request);
    }

}
