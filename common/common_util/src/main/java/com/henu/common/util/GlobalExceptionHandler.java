package com.henu.common.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ControllerAdvice: 表示当前类是异常处理类，给Controller控制器类增强功能的
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 定义处理异常的方法，当异常发生后，执行这个方法
     * 处理Exception异常
     *
     * @ExceptionHandler: 表示此方法处理相应的异常
     *               属性: value 表示异常的类型
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }
    /**
     * 定义处理异常的方法，当异常发生后，执行这个方法
     * 处理SumException异常
     *
     * @ExceptionHandler: 表示此方法处理相应的异常
     *               属性: value 表示异常的类型
     */
    @ExceptionHandler(SumException.class)
    @ResponseBody
    public Result error(SumException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
