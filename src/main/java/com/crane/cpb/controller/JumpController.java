package com.crane.cpb.controller;

import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 跳转接口
 *
 * @author Xanthos
 * @date 2025/2/18 13:45
 */
@RestController
@RequiredArgsConstructor
public class JumpController {

    private final ShoppingCartService shoppingCartService;

    private final TagService tagService;

    /**
     * 跳转至首页
     *
     * @date 2025/3/2 12:11
     **/
    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        shoppingCartService.setCartData(request, modelAndView);
        tagService.setTagsType(modelAndView);
        return modelAndView;
    }

    /**
     * 万能跳转接口
     *
     * @date 2025/3/2 12:13
     **/
    @GetMapping("/jump/{path}")
    public ModelAndView jump(@PathVariable String path) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(path);
        return modelAndView;
    }

}
