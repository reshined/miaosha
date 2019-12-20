package com.seckill.web.intercepters;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.seckill.common.config.JwtConfig;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Token 拦截器
 */
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private JwtConfig jwtConfig ;

//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response,
//                             Object handler) throws Exception {
//        // 地址过滤
//        String uri = request.getRequestURI() ;
//        //登陆地址放开
//        if (uri.equals("/discount/login")){
//            return true ;
//        }
//
//       String token = request.getParameter("Author");
//
//        if(StringUtils.isEmpty(token)){
//            throw new Exception(jwtConfig.getHeader()+ "不能为空");
//        }
//        Claims claims = jwtConfig.getTokenClaim(token);
//        if(claims == null || jwtConfig.isTokenExpired(claims.getExpiration())){
//            throw new Exception(jwtConfig.getHeader() + "失效，请重新登录");
//        }
//        //设置 identityId 用户身份ID
//        request.setAttribute("identityId", claims.getSubject());
//        return true;
//    }

//    public String getRequestHeader(HttpServletRequest request,String name){
//        Map<String, String> map = new HashMap<String, String>();
//        Enumeration headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = request.getHeader(key);
//            map.put(key, value);
//        }
//        return map.get(name);
//    }
}
