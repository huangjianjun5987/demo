package com.yatang.sc.inventory.dubboservice.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentReceiptDto;

/**
 * @描述: 库存调整flow服务接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/1 下午4:00
 * @版本: v1.0
 */
public interface ImAdjustmentFlowService {


    Response<Void> adjustInventoryItem(ImAdjustmentReceiptDto receiptDto);


    /**
     * 数据调整(际连数据转scm数据)
     * @param gLinkImAdjustmentReceipt
     * @return
     */
    Response<ImAdjustmentReceiptDto> adjustImAdjustmentReceipt(KiddImAdjustmentReceiptDto gLinkImAdjustmentReceipt);

}
