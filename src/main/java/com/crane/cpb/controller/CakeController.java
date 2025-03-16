package com.crane.cpb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.cpb.model.domain.Cake;
import com.crane.cpb.model.domain.vo.CakeVo;
import com.crane.cpb.service.CakeService;
import com.crane.cpb.service.ShoppingCartService;
import com.crane.cpb.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 蛋糕接口
 *
 * @author Xanthos
 * @date 2025/3/2 12:39
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/cake")
public class CakeController {

    private final CakeService cakeService;

    private final ShoppingCartService shoppingCartService;

    private final TagService tagService;

    /**
     * 跳转到网格列表
     *
     * @date 2025/3/12 19:58
     **/
    @GetMapping("/grid/{pageNum}")
    public ModelAndView shopGrid(@PathVariable Integer pageNum, HttpServletRequest request) {
        if (pageNum < 1) {
            pageNum = 1;
        }
        Page<Cake> page = cakeService.page(new Page<>(pageNum, 12));
        Page<CakeVo> pageVo = new Page<>();
        BeanUtils.copyProperties(page, pageVo);
        pageVo.setRecords(page.getRecords().stream().map(cakeService::toVo).toList());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("page", pageVo);
        modelAndView.addObject("currentPageNum", pageNum);
        modelAndView.setViewName("shop_grid");
        //计算pageEnd
        double l = (double) pageVo.getTotal() / pageVo.getSize();
        modelAndView.addObject("pageEnd", Math.ceil(l));
        //获取购物车数据
        shoppingCartService.setCartData(request, modelAndView);
        Object flag = request.getSession().getAttribute("addFail");
        if (flag != null) {
            modelAndView.addObject("addFail", flag);
            request.getSession().removeAttribute("addFail");
        }
        tagService.setTagsType(modelAndView);
        return modelAndView;
    }

    /**
     * 跳转至商品详情
     **/
    @GetMapping("/showOne/{cakeId}")
    public ModelAndView showOne(@PathVariable Long cakeId, HttpServletRequest request) {
        Cake byId = cakeService.getById(cakeId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("cake", cakeService.toVo(byId));
        modelAndView.setViewName("shop_details");
        shoppingCartService.setCartData(request, modelAndView);
        return modelAndView;
    }

}
