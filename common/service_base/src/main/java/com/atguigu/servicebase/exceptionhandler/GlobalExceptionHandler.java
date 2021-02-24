package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j //记录日志
@ControllerAdvice //注解定义全局异常处理类
public class GlobalExceptionHandler {

    //指定出现什么异常执这个方法
    @ExceptionHandler(Exception.class)//说明所有异常都会执行
    @ResponseBody //为了会返回 json 数据
    public R error(Exception e){
        log.info("=====================统一异常处理类执行成功!=====================");
        log.error(e.getMessage()); //打印到 logback 生成的文件中
        e.printStackTrace();
        return R.error().message("执行了全局异常处理....");
    }


    //指定特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //返回json 数据
    public R error(ArithmeticException e){
        log.info("=====================指定特定异常成功!=====================");
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message("执行了特定算术异常处理....");
    }

    //自定义异常
    @ExceptionHandler(HeartException.class)
    @ResponseBody //为了返回数据 json
    public R error(HeartException e){
        log.info("=====================自定义异常类执行成功!=====================");
        log.error(e.getMsg());
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
