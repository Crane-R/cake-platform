package com.crane.cpb.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crane.cpb.model.domain.Admin;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.AdminService;
import com.crane.cpb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员接口
 *
 * @author Xanthos
 * @date 2025/5/13 22:05
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    private final AdminService adminService;

    private final UserService userService;

    @GetMapping("/api/page")
    public Page<Map<String, Object>> page(@RequestParam int current,
                                          @RequestParam int size,
                                          @RequestParam(required = false) String username
    ) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("identity", 2);
        if (StrUtil.isNotEmpty(username)) {
            queryWrapper.like("username", username);
        }
        Page<User> users = userService.page(new Page<>(current, size), queryWrapper);
        Page<Map<String, Object>> page = new Page<>(current, size);
        BeanUtil.copyProperties(users, page);
        List<Map<String, Object>> list = users.getRecords().stream().map(user -> {
            Object identityIndex = user.getIdentityIndex();
            Admin admin = adminService.getOne(new QueryWrapper<Admin>().eq("admin_id", identityIndex));
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", user.getUserId());
            map.put("username", user.getUsername());
            map.put("phone", user.getPhone());
            map.put("email", user.getEmail());
            map.put("code", admin.getCode());
            return map;
        }).toList();
        page.setRecords(list);
        return page;
    }

    @PostMapping("/api/save")
    public Admin saveOrUpdate(@RequestBody User user) {
        return adminService.saveAdmin(user);
    }

    @GetMapping("/delete")
    public Boolean remove(@RequestParam Long userId) {
        return adminService.removeAdmin(userId);
    }

}
