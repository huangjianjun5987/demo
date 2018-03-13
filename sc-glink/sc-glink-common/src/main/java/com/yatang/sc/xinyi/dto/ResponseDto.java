package com.yatang.sc.xinyi.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 请求dto<br>
 *
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class ResponseDto implements java.io.Serializable {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String code;        //接口调用状态码
    private String flag;        //接口调用状态码
    private String message;     //调用不成功状态描述

}
