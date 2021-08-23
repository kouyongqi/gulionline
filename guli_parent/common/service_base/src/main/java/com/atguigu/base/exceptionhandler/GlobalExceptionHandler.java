package com.atguigu.base.exceptionhandler;


import com.atguigu.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*全局异常处理(拦截异常信息)*/
    /*设置异常处理类型*/
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalerror(Exception exception){
        exception.printStackTrace();
        return Result.error().message("全局异常处理");
    }

    /*自定义异常处理(拦截异常信息)*/
    @ExceptionHandler(DefineException.class)
    @ResponseBody
    public Result defineerror(DefineException exception){
        /*将异常信息输出到日志中*/
        log.error(exception.getMsg());
        exception.printStackTrace();
        return Result.error().code(exception.getCode()).message(exception.getMsg());
    }



}
