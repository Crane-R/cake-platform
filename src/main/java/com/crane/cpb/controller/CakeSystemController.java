package com.crane.cpb.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.cpb.model.domain.Cake;
import com.crane.cpb.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 蛋糕后台接口
 *
 * @author Xanthos
 * @date 2025/3/22 17:42
 */
@RequiredArgsConstructor
@RequestMapping("/cakeSys")
@RestController
@CrossOrigin
public class CakeSystemController {

    private final CakeService cakeService;

    @GetMapping("/page")
    public Page<Cake> page(){
        return cakeService.page(new Page<>(1, 10));
    }

}
