package com.yatang.sc.app.web;


import com.yatang.sc.app.web.paramvalid.ParamValidException;
import lombok.extern.log4j.Log4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
@ControllerAdvice
public class DefaultExceptionHandler {

    /**
     * 普通拦截
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            UnsatisfiedServletRequestParameterException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestPartException.class,
            Exception.class
    })
    public @ResponseBody
    HttpMessage handleRequestException(Exception ex) {
        log.error("Exception: " + ex.getMessage(), ex);
        return HttpMessage.builder()
                .timestamp(new Date())
                .isSuccess(false)
                .code(500)
                .message(ex.getMessage())
                .build();
    }

    /**
     * post方式参数校验拦截
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public HttpMessage handleRequestValidException(MethodArgumentNotValidException ex) {
        log.error("Exception: " + ex.getMessage(), ex);
        HashMap<String, String> map = new HashMap<>();
        System.out.println(ex.getBindingResult());
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : allErrors) {
            FieldError error1 = (FieldError) error;
            map.put(error1.getField(), error1.getDefaultMessage());
        }
        return HttpMessage.builder()
                .timestamp(new Date())
                .isSuccess(false)
                .code(400)
                .message(map)
                .build();
    }
   /* *//**
     *
     *
     * @param ex
     * @return
     *//*
    @ExceptionHandler({
            ConstraintViolationException.class
    })
    @ResponseBody
    public HttpMessage handleRequestParamValidException(ConstraintViolationException ex) {
        HashMap<Integer, String> map = new HashMap<>();
        AtomicInteger count = new AtomicInteger(0);

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : constraintViolations) {
            map.put(count.addAndGet(1), violation.getMessage());
        }
        return HttpMessage.builder()
                .timestamp(new Date())
                .isSuccess(false)
                .code(500)
                .message(map)
                .build();
    }*/


    /**
     * get参数校验拦截(形参为基本类型的)
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = ParamValidException.class)
    @ResponseBody
    public HttpMessage handleRequestParamValidException(ParamValidException ex) {
        log.error("Exception: " + ex.getMessage(), ex);
        Map<String, String> fieldErrors = ex.getFieldErrors();
        return HttpMessage.builder()
                .timestamp(new Date())
                .isSuccess(false)
                .code(400)
                .message(fieldErrors)
                .build();
    }

    /**
     * Get拦截绑定参数异常校验(类里面的属性)
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public HttpMessage handleRequestBindValidException(BindException ex) {
        log.error("Exception: " + ex.getMessage(), ex);
        HashMap<String, String> map = new HashMap<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        for (ObjectError error : allErrors) {
            FieldError error1 = (FieldError) error;
            map.put(error1.getField(), error1.getDefaultMessage());
        }
        return HttpMessage.builder()
                .timestamp(new Date())
                .isSuccess(false)
                .code(400)
                .message(map)
                .build();
    }
}
