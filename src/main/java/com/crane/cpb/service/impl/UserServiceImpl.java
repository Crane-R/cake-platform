package com.crane.cpb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crane.cpb.mapper.UserMapper;
import com.crane.cpb.model.domain.User;
import com.crane.cpb.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Xanthos
 * @description 针对表【user】的数据库操作Service实现
 * @createDate 2025-02-12 14:28:09
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(User user) {
        int count = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername())).size();
        if (count > 0) {
            return false;
        }
        return userMapper.insert(user) == 1;
    }

    @Override
    public Boolean login(User user, HttpServletRequest request) {
        User selectOne = userMapper.selectOne(new QueryWrapper<User>()
                .eq("username", user.getUsername()).eq("password", user.getPassword()));
        if (selectOne != null) {
            request.getSession().setAttribute("user", selectOne);
        }
        return selectOne != null;
    }

    @Override
    public User currentUser(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        return (User) user;
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }
}




