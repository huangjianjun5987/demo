package com.yatang.sc.service;

public interface ThirdInventoryHelper {

    /**
     * 给小超回传商品签收数量
     * @param orderId
     */
    void sendXCSignNumber(String orderId);
}
