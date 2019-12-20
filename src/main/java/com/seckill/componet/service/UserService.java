package com.seckill.componet.service;
import com.seckill.common.result.CodeMsg;
import com.seckill.common.util.MD5Util;
import com.seckill.common.util.UUIDUtil;
import com.seckill.common.vo.LoginVo;
import com.seckill.componet.dao.UserMapper;
import com.seckill.componet.dataobject.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${expireSeconds}")
    private String expireSeconds;

    @Value("${cookie_name}")
    private String cookieName;

    //token登陆
    public User getByToken(HttpServletResponse response,String token){
        if(StringUtils.isEmpty(token)){
            return null;
        }
        Object object = redisTemplate.opsForValue().get(token);
        if(object != null){
            User user = new User();
            //对象复制
            BeanUtils.copyProperties(object,user);
            //延长有效期
            addCookie(response,token,user);
            return  user;
        }
        return null;
    }

    //登陆
    @Transactional
    public CodeMsg login(HttpServletResponse response, LoginVo loginVo){

        User user = userMapper.selectByTel(loginVo.getMobile());
       //用户不存在
       if(user == null){
           return CodeMsg.SERVER_ERROR;
       }
        //数据库中的密码
       String password = user.getPassword();
       String saltDb   = user.getSalt();
       String calcPass = MD5Util.formPassToDBpass(loginVo.getPassword(),saltDb);
        //校验密码
       if(!calcPass.equals(password)){
           return CodeMsg.PASSWORD_ERROR;
       }
        //生成token
        String token = UUIDUtil.uuid();
        //获取旧token
        Object oldToken = redisTemplate.opsForValue().get(user.getTel());
        //判断redis中是否有用户的信息，若有则先清除，再添加，避免用户数据重复
        if(oldToken != null){
            //移除用户账号对应的token
            redisTemplate.delete(user.getTel());
            Object oldUser = redisTemplate.opsForValue().get(oldToken.toString());
            //移除用户旧token对应用户信息
            if(oldUser != null){
                redisTemplate.delete(oldToken.toString());
            }
        }
        //将用户token对应的用户信息存入redis
        redisTemplate.opsForValue().set(token,user);
        //将用户登陆账号对应的token存入redis，便于操作
        redisTemplate.opsForValue().set(user.getTel(),token);
        addCookie(response,token,user);
       return CodeMsg.SUCCESS;
    }

    //添加cookie
    private void addCookie(HttpServletResponse response,String token, User user) {
        //设置缓存时间
        redisTemplate.expire(token,Integer.valueOf(expireSeconds), TimeUnit.SECONDS);
        redisTemplate.expire(user.getTel(),Integer.valueOf(expireSeconds), TimeUnit.SECONDS);
        Cookie cookie = new Cookie(cookieName, token);
        //设置cookie时间
        cookie.setMaxAge(Integer.valueOf(expireSeconds));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
