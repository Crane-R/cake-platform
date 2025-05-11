package com.crane.cpb.controller;

import com.crane.cpb.service.*;
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
public class IndexController {

    private final ShoppingCartService shoppingCartService;

    private final TagService tagService;

    private final CakeService cakeService;

    private final CakeTagService cakeTagService;

    private final UserService userService;

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
        //获取轮播图数据
        cakeService.setCarouselImage(modelAndView);
        cakeService.setLatestCake(modelAndView);
        modelAndView.addObject("currentUser", userService.currentUser(request).getUsername());
        return modelAndView;
    }

    /**
     * 万能跳转接口
     *
     * @date 2025/3/2 12:13
     **/
    @GetMapping("/jump/{path}")
    public ModelAndView jump(@PathVariable String path, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(path);
        modelAndView.addObject("currentUser", userService.currentUser(request).getUsername());
        shoppingCartService.setCartData(request, modelAndView);
        return modelAndView;
    }

//    /**
//     * 搜索接口
//     **/
//    @GetMapping("/search/{word}")
//    public ModelAndView search(@PathVariable String word, @RequestParam Long typeId, HttpServletRequest request) {
//        QueryWrapper<Cake> queryWrapper = new QueryWrapper<>();
//        queryWrapper.likeRight("name", word);
//        if(typeId != null) {
//            List<CakeTag> tagId = cakeTagService.list(new QueryWrapper<CakeTag>().eq("tag_id", typeId));
//            List<Long> list = tagId.stream().map(CakeTag::getCakeId).toList();
//            queryWrapper.in("cake_id", list);
//        }
//        return cakeService.queryPage(queryWrapper,);
//    }

}
