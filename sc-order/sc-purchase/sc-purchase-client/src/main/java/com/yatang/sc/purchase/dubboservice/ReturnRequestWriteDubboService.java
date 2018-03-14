package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;

/**
 * @描述:退换货dubboWrite服务定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/17 11:37
 * @版本: v1.0
 */
public interface ReturnRequestWriteDubboService {

    /**
     * 新建退换货单
     *
     * @return
     */
    Response<String> createOrderReturnedReceipt(ReturnRequestReceiptDto receiptDto);


    /**
     * 新增拒收退货单
     */
    Response<String> createRejectOrderReturnedReceipt(ReturnRequestReceiptDto receiptDto);
}
