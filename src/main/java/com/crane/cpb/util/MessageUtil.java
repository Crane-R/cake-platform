package com.crane.cpb.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * 消息工具
 *
 * @author Xanthos
 * @date 2025/5/13 19:34
 */
public final class MessageUtil {

    private MessageUtil() {
    }

    public static void setStrideMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
    }

    public static void setMessage(ModelAndView modelAndView, String message) {
        modelAndView.addObject("message", message);
    }

    public static void getStrideMessage(HttpServletRequest request, ModelAndView modelAndView) {
        Object message = request.getSession().getAttribute("message");
        if (message != null) {
            modelAndView.addObject("message", message);
            request.getSession().removeAttribute("message");
        }
        Object messageType = request.getSession().getAttribute("messageType");
        if (messageType != null) {
            modelAndView.addObject("messageType", messageType);
            request.getSession().removeAttribute("messageType");
        }
    }

    public static void setStrideErrorMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
        request.getSession().setAttribute("messageType", "error");
    }

}
