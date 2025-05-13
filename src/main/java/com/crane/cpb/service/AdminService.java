package com.crane.cpb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.cpb.model.domain.Admin;
import com.crane.cpb.model.domain.User;

/**
* @author Xanthos
* @description 针对表【admin(管理员表)】的数据库操作Service
* @createDate 2025-02-12 14:27:56
*/
public interface AdminService extends IService<Admin> {

    Admin saveAdmin(User user);

    Boolean removeAdmin(Long userId);

}
