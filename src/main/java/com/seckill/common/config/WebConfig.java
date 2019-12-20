package com.seckill.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import javax.annotation.Resource;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    UserArgumentResolver userArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }




    /**
     * -设置url后缀模式匹配规则
     * -该设置匹配所有的后缀，使用.do或.action都可以
     */
    public void configurePathMatch(PathMatchConfigurer configurer){
        configurer.setUseSuffixPatternMatch(true) //设置是否是后缀模式匹配,即:/test.*
                .setUseTrailingSlashMatch(true);  //设置是否自动后缀路径模式匹配,即：/test/
    }

//    /**
//     * 拦截器注册
//     */
//    @Resource
//    private TokenInterceptor tokenInterceptor ;
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
//    }
}
