package com.seckill.web.controller;

import com.seckill.common.result.CodeMsg;
import com.seckill.common.result.Result;
import com.seckill.common.util.MetaspaceUtil;
import com.seckill.common.util.UUIDUtil;
import com.seckill.common.util.ValidatorUtil;
import com.seckill.common.vo.LoginVo;
import com.seckill.componet.dataobject.User;
import com.seckill.componet.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;




    @GetMapping("/nonheap")
    public void heap(){

        while(true){
            MetaspaceUtil.createClasses();
        }
    }


    @PostMapping(value = "/do_login")
    @ResponseBody
    public Result<Boolean> do_login(HttpServletResponse response, LoginVo loginVo){
        logger.info("log："+loginVo.toString());
        if(!ValidatorUtil.isMobile(loginVo.getMobile())){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        CodeMsg cm = userService.login(response,loginVo);

        if (cm.getCode() == 0){
            return Result.success(true);
        }else{
            return Result.error(cm);
        }

    }


    @GetMapping("/to_login")
    public String to_login(){
        return "login";
    }


    @GetMapping("/index")
    @ResponseBody
    public Result<Boolean> index(){
        return Result.error(CodeMsg.MOBILE_ERROR);
    }

}
