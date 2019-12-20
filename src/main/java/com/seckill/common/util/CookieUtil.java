package com.seckill.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 * 
 * @author cjy
 * @date 2016年9月29日
 */
public class CookieUtil {


//    private static final String COOKIE_DOMAIN="www.mengxin.com"; //防止domian不一致，导致cookie储存失败
    /**
     * 保存cookie
     * 
     * @param request
     * @param response
     * @param name
     * @param value
     * @param expire
     *            unit:second
     */
    public static void saveCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int expire){
        Cookie cookie;
        if(containsName(request, name)){
            cookie = getCookieByName(request, name);
            cookie.setValue(value);
        }else{
            cookie = new Cookie(name, value);
        }
        cookie.setMaxAge(expire);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 保存cookie
     *
     * @param response
     * @param name
     * @param value
     * @param expire
     */
    public static void saveCookie(HttpServletResponse response, String name, String value, int expire){
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(expire);
        cookie.setPath("/");
//        cookie.setHttpOnly(true);//防止脚本攻击
//        cookie.setDomain(COOKIE_DOMAIN);
        response.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     * 
     * @param request
     * @param name
     *            cookie名字
     * @return cookie
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        return null;
    }

    /**
     * 判断cookie中是否存在该名字
     * 
     * @param request
     * @param name
     * @return true if contains
     */
    public static boolean containsName(HttpServletRequest request, String name){
        Map<String, Cookie> cookieMap = readCookieMap(request);
        return cookieMap.containsKey(name);
    }

    /**
     * 删除cookie
     * 
     * @param request
     * @param response
     * @param name
     */
    public static void delCookie(HttpServletResponse response, String name){
        saveCookie(response, name, null, 0);
    }

    /**
     * 刷新cookie
     * 
     * @param request
     * @param response
     * @param name
     * @param expire
     */
    public static void refreshCookie(HttpServletRequest request, HttpServletResponse response, String name, int expire){
        if(containsName(request, name)){
            Cookie cookie = getCookieByName(request, name);
            cookie.setMaxAge(expire);
            response.addCookie(cookie);
        }
    }

    /**
     * 将cookie封装到Map里面
     * 
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
