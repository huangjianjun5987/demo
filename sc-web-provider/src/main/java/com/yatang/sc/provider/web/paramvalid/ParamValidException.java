package com.yatang.sc.provider.web.paramvalid;

import lombok.Getter;

import java.util.Map;

/**
 * @描述: 接收基本参数类型的异常类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/5 17:26
 * @版本: v1.0
 */
@Getter
public class ParamValidException extends RuntimeException{
    private static final long serialVersionUID = 8110503528875949312L;
    private Map<String,String> fieldErrors;
    public ParamValidException(Map<String,String> errors) {
        this.fieldErrors = errors;
    }

}
