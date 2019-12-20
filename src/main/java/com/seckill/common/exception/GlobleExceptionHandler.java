//package com.seckill.common.exception;
//
//
//import com.seckill.common.result.Result;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import javax.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//@ResponseBody
//public class GlobleExceptionHandler {
//
//    @ExceptionHandler(value = Exception.class)
//    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
//        if(e instanceof BindException){
//            BindException exception = (BindException) e;
//            return null;
//        }
//
//        return null;
//    }
//}
