package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;

/**
 * 直营店下单服务
 * @Author:by
 */
public interface DirectStoreOrdersDubboService {

    public Response<Double> getPrice(String productId, String branchCompanyId, long quantity);
}
