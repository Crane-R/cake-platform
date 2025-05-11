package com.crane.cpb.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.MerchantMapper;
import com.crane.cpb.model.domain.Merchant;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.MerchantService;
import com.crane.cpb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * @author Xanthos
 * @description 针对表【merchant(商家)】的数据库操作Service实现
 * @createDate 2025-02-12 14:28:03
 */
@Service
@RequiredArgsConstructor
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant>
        implements MerchantService {

    private final UserService userService;

    @Override
    @Transactional
    public boolean removeMerchant(Long userId) {
        User user = userService.getById(userId);
        boolean isRemoveUser = userService.removeById(userId);
        if (isRemoveUser) {
            super.removeById(user.getIdentityIndex());
        }
        return true;
    }

    @Override
    public Boolean saveUpdateMerchant(Map<String, Object> params) {
        Object username = params.get("username");
        long counted = userService.count(new QueryWrapper<User>().eq("username", username));
        if (counted != 0) {
            throw new RuntimeException("用户名已存在");
        }
        Object password = params.get("password");
        Object phone = params.get("phone");
        Object email = params.get("email");
        Object address = params.get("address");
        Object name = params.get("name");
        Object userId = params.get("user_id");
        Object merchantId = params.get("merchant_id");
        //先新增商家，再新增用户
        Merchant merchant = new Merchant();
        if (merchantId != null) {
            merchant.setMerchantId(Long.valueOf(merchantId.toString()));
        }
        merchant.setCode(RandomUtil.randomString(4));
        merchant.setName(String.valueOf(name));
        merchant.setContactInfo(String.valueOf(email));
        merchant.setAddress(String.valueOf(address));
        boolean merchantSaved = super.saveOrUpdate(merchant);
        if (!merchantSaved) {
            throw new RuntimeException("商家保存失败");
        }
        User user = new User();
        if (userId != null) {
            user.setUserId(Long.valueOf(userId.toString()));
        }
        user.setUsername(String.valueOf(username));
        user.setPassword(SecureUtil.md5(String.valueOf(password)));
        user.setPhone(String.valueOf(phone));
        user.setEmail(String.valueOf(email));
        user.setIdentity(1);
        user.setIdentityIndex(merchant.getMerchantId());
        return userService.saveOrUpdate(user);
    }
}




