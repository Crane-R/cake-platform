package com.crane.cpb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.crane.cpb.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Xanthos
 * @description 针对表【user】的数据库操作Service
 * @createDate 2025-02-12 14:28:09
 */
public interface UserService extends IService<User> {

    Boolean register(User user);

    User login(User user, HttpServletRequest request);

    User currentUser(HttpServletRequest request);

    void logout(HttpServletRequest request);

    /**
     * 设置个人账户页数据
     **/
    void setAccountData(User user, ModelAndView modelAndView);

}
