package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.MerchantMapper;
import com.crane.cpb.model.domain.Merchant;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.MerchantService;
import com.crane.cpb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}




