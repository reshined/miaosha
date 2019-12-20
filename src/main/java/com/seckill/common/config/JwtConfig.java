package com.seckill.common.config;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;


@Component
public class JwtConfig {


    private final  String secret = "iwqjhda8232bjgh432[cicada-smile]";

    private final  long   expire = 3600 ;

    private final  String  header = "token";
    /*
     * 根据身份ID标识，生成Token
     */
    public  String getToken (String identityId){
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(identityId)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /*
     * 获取 Token 中注册信息
     */
    public  Claims getTokenClaim (String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /*
     * Token 是否过期验证
     */
    public  boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 获取token信息，同时也做校验处理
     * @param token
     * @return
     */
    public  Claims getTokenBody(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    public String getSecret() {
        return secret;
    }

    public long getExpire() {
        return expire;
    }

    public String getHeader() {
        return header;
    }

}
