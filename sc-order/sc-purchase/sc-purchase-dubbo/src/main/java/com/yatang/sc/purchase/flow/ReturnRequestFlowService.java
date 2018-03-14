package com.yatang.sc.purchase.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.order.domain.returned.ReturnRequestReceiptPo;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;

/**
 * @描述: 退换货的flow服务接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/18 13:22
 * @版本: v1.0
 */
public interface ReturnRequestFlowService {
    /**
     * 填充退换货单数据
     *
     * @param receiptDto
     * @return
     */
    Response<ReturnRequestReceiptPo> addOrderReturnReceiptDetail(ReturnRequestReceiptDto receiptDto);

}
