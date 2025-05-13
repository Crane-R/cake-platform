package com.crane.cpb.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.cpb.model.domain.Address;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.AddressService;
import com.crane.cpb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 地址接口
 *
 * @author Xanthos
 * @date 2025/5/13 20:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
@CrossOrigin
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @GetMapping("/api/page")
    public Page<Address> page(@RequestParam int current,
                              @RequestParam int size,
                              @RequestParam(required = false) String content,
                              HttpServletRequest request
    ) {
        LambdaQueryWrapper<Address> queryWrapper = new LambdaQueryWrapper<>();
        User user = userService.currentUser(request);
        if (user == null) {
            throw new RuntimeException("用户未登录");
        }
        queryWrapper.eq(Address::getUserId, user.getUserId());
        queryWrapper.orderByDesc(Address::getAddressId);
        if (StrUtil.isNotEmpty(content)) {
            queryWrapper.like(Address::getContent, content);
        }
        return addressService.page(new Page<>(current, size), queryWrapper);
    }

    @PostMapping("/api/save")
    public Boolean saveTag(@RequestBody Address address, HttpServletRequest request) {
        User user = userService.currentUser(request);
        if (user == null) {
            throw new RuntimeException("用户未登录");
        }
        address.setUserId(user.getUserId());
        return addressService.saveOrUpdate(address);
    }

    @GetMapping("/api/delete/{addressId}")
    public Boolean deleteTag(@PathVariable Long addressId) {
        return addressService.removeById(addressId);
    }

}
