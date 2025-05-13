package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.model.domain.Admin;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.AdminService;
import com.crane.cpb.mapper.AdminMapper;
import com.crane.cpb.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Xanthos
 * @description 针对表【admin(管理员表)】的数据库操作Service实现
 * @createDate 2025-02-12 14:27:56
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {

    private final UserService userService;

    public AdminServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Admin saveAdmin(User user) {
        Admin admin = new Admin();
        admin.setCode("admin" + super.count());
        super.save(admin);
        user.setIdentity(2);
        user.setIdentityIndex(admin.getAdminId());
        userService.save(user);
        return admin;
    }

    @Override
    public Boolean removeAdmin(Long userId) {
        User user = userService.getById(userId);
        boolean isRemoveUser = userService.removeById(userId);
        if (isRemoveUser) {
            super.removeById(user.getIdentityIndex());
        }
        return true;
    }
}




