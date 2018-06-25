package com.polystone.tools.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookies操作
 *
 * @author jimmy
 * @version V1.0, 2017/7/11
 * @copyright
 */
public class CookieUtil {

    /**
     * 删除cookie
     *
     * @param request
     * @param response
     * @param name
     *            [参数说明]
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        if (null == name) {
            return;
        }
        Cookie cookie = getCookie(request, name);
        if (null != cookie) {
            cookie.setPath("/");
            cookie.setValue(null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * 根据Cookie名称得到Cookie对象，不存在该对象则返回Null
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || null == name || name.length() == 0) {
            return null;
        }
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) {
                return c;
            }
        }
        return null;
    }

    /**
     * 获取cookie的值
     *
     * @param request
     * @param name
     * @return [参数说明]
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (null != cookie) {
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 获取cookie后并删除
     *
     * @param request
     * @param response
     * @param name
     * @return [参数说明]
     */
    public static String getAndRemoveCookieValue(HttpServletRequest request, HttpServletResponse response,
        String name) {
        Cookie cookie = getCookie(request, name);
        if (null != cookie) {
            String val = cookie.getValue();
            cookie.setPath("/");
            cookie.setValue(null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return val;
        }
        return null;
    }

    /**
     * 设置过期时间
     *
     * @param request
     * @param name
     * @param maxValue
     * @return [参数说明]
     */
    public static Cookie getAndExpireCookie(HttpServletRequest request, HttpServletResponse response, String name,
        int maxValue) {
        Cookie cookie = getCookie(request, name);
        if (null != cookie) {
            cookie.setPath("/");
            cookie.setMaxAge(maxValue);
            response.addCookie(cookie);
        }
        return cookie;
    }

    /**
     * 添加一条新的Cookie，可以指定过期时间(单位：秒)
     *
     * @param response
     * @param name
     * @param value
     * @param maxValue
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxValue) {
        if (null == name) {
            return;
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxValue);
        response.addCookie(cookie);
    }

}
