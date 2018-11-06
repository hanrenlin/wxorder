package com.haorenlin.wxorder.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Yan Junlin
 * @Date 2018/11/6 10:31
 * @Description (cookie操作)
 **/
public class CookieUtil {

    /**
     * 保存cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        /** 同一应用服务器内共享 */
        cookie.setPath("/");
        /** 设置cookie过期时间 单位毫秒 */
        cookie.setMaxAge(maxAge);
        /** 添加cookie*/
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length < 1 || StringUtils.isEmpty(name)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if(name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}