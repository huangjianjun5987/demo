package com.yatang.sc.operation.vo;

import java.util.Map;

/**
 * @描述:
 * @作者: tangqi
 * @创建时间: 2017/10/31
 */
public class GrantCouponBatchVo {

    // 门店ID
    private String[] storeIds;
    // couponID和数量
    Map<String, Integer> couponParam;

    public String[] getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(String[] storeIds) {
        this.storeIds = storeIds;
    }

    public Map<String, Integer> getCouponParam() {
        return couponParam;
    }

    public void setCouponParam(Map<String, Integer> couponParam) {
        this.couponParam = couponParam;
    }
}
