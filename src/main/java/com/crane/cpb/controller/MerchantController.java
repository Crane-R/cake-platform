package com.crane.cpb.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.cpb.model.domain.Merchant;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.MerchantService;
import com.crane.cpb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家接口
 *
 * @author Xanthos
 * @date 2025/3/22 16:10
 */
@RestController
@RequestMapping("/merchant")
@RequiredArgsConstructor
@CrossOrigin
public class MerchantController {

    private final UserService userService;

    private final MerchantService merchantService;

    @GetMapping("/page")
    public Page<Map<String, Object>> page(@RequestParam int current,
                                          @RequestParam int size,
                                          @RequestParam(required = false) String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity", 1);
        if (StrUtil.isNotEmpty(username)) {
            queryWrapper.like("username", username);
        }
        Page<User> users = userService.page(new Page<>(current, size), queryWrapper);
        Page<Map<String, Object>> page = new Page<>(current, size);
        BeanUtil.copyProperties(users, page);
        List<Map<String, Object>> list = users.getRecords().stream().map(user -> {
            Object identityIndex = user.getIdentityIndex();
            Merchant merchant = merchantService.getOne(new QueryWrapper<Merchant>().eq("merchant_id", identityIndex));
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", user.getUserId());
            map.put("username", user.getUsername());
            map.put("phone", user.getPhone());
            map.put("email", user.getEmail());
            map.put("merchant_id", merchant.getMerchantId());
            map.put("code", merchant.getCode());
            map.put("name", merchant.getName());
            map.put("contact_info", merchant.getContactInfo());
            map.put("address", merchant.getAddress());
            return map;
        }).toList();
        page.setRecords(list);
        return page;
    }

    @PostMapping("/saveOrUpdate")
    public Boolean add(@RequestBody Map<String, Object> params) {
        Object username = params.get("username");
        long counted = userService.count(new QueryWrapper<User>().eq("username", username));
        if (counted != 0) {
            return false;
        }
        Object password = params.get("password");
        Object phone = params.get("phone");
        Object email = params.get("email");
        Object address = params.get("address");
        Object name = params.get("name");
        Object userId = params.get("user_id");
        Object merchantId = params.get("merchant_id");
        User user = new User();
        user.setUserId(Long.valueOf(userId.toString()));
        user.setUsername(username.toString());
        user.setPassword(password.toString());
        if (phone != null) {
            user.setPhone(phone.toString());
        }
        if (email != null) {
            user.setEmail(email.toString());
        }
        user.setIdentity(1);
        Merchant merchant = new Merchant();
        merchant.setMerchantId(Long.valueOf(merchantId.toString()));
        merchant.setCode(RandomUtil.randomString(4));
        if (name != null) {
            merchant.setName(name.toString());
        }
        if (email != null) {
            merchant.setContactInfo(email.toString());
        }
        if (address != null) {
            merchant.setAddress(address.toString());
        }
        merchantService.saveOrUpdate(merchant);
        user.setIdentityIndex(merchant.getMerchantId());
        userService.saveOrUpdate(user);
        return true;
    }

    @GetMapping("/delete")
    public Boolean remove(@RequestParam Long userId) {
        return merchantService.removeMerchant(userId);
    }

}
