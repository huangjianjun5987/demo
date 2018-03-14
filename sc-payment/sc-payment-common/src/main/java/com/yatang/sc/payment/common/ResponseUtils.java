package com.yatang.sc.payment.common;

import com.busi.common.resp.Response;

/**
 * Created by yuwei on 2017/7/10.
 */
public class ResponseUtils {

    public static <T> Response<T> RESPONSE_200() {
        Response<T> response = new Response<T>();
        response.setSuccess(true);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        return response;
    }

    public static <T> Response<T> RESPONSE_500() {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setCode(CommonsEnum.RESPONSE_500.getCode());
        response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        return response;
    }

    public static <T> Response<T> RESPONSE_400() {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setCode(CommonsEnum.RESPONSE_400.getCode());
        response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
        return response;
    }

    public static <T> Response<T> RESPONSE_FAILD(CommonsEnum pCommonsEnum){
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setCode(pCommonsEnum.getCode());
        response.setErrorMessage(pCommonsEnum.getName());
        return response;
    }
}
