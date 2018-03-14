package com.yatang.sc.payment.dto.response;

import java.io.Serializable;

/**
 * Created by yuwei on 2017/7/14.
 */
public class YTPrePayResponseDto implements Serializable {
    private String apiUrl;

    private String token;

    private Integer type;

    private String datetime;
    private String user_name;

    private String shop_user_name;

    private String order_amount;
    private String order_sn;

    private Integer business_type;

    private String goods_name;
    private String sync_url;

    private String async_url;

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String pApiUrl) {
        apiUrl = pApiUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String pToken) {
        token = pToken;
    }


    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String pDatetime) {
        datetime = pDatetime;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String pUser_name) {
        user_name = pUser_name;
    }

    public String getShop_user_name() {
        return shop_user_name;
    }

    public void setShop_user_name(String pShop_user_name) {
        shop_user_name = pShop_user_name;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String pOrder_sn) {
        order_sn = pOrder_sn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer pType) {
        type = pType;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String pOrder_amount) {
        order_amount = pOrder_amount;
    }

    public Integer getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(Integer pBusiness_type) {
        business_type = pBusiness_type;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String pGoods_name) {
        goods_name = pGoods_name;
    }

    public String getSync_url() {
        return sync_url;
    }

    public void setSync_url(String pSync_url) {
        sync_url = pSync_url;
    }

    public String getAsync_url() {
        return async_url;
    }

    public void setAsync_url(String pAsync_url) {
        async_url = pAsync_url;
    }
}
