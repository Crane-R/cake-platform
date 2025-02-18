package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.model.domain.Admin;
import com.crane.cpb.service.AdminService;
import com.crane.cpb.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author Xanthos
* @description 针对表【admin(管理员表)】的数据库操作Service实现
* @createDate 2025-02-12 14:27:56
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




