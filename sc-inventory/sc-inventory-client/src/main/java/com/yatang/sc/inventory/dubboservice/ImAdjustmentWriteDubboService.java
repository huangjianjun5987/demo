package com.yatang.sc.inventory.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentDataDto;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentReceiptDto;

/**
 * @描述: 库存调整写入dubbo的Service接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午9:11
 * @版本: v1.0
 */
public interface ImAdjustmentWriteDubboService {


    /**
     * 新增库存调整单
     *
     * @param imAdjustmentReceiptDto
     * @return
     */
    Response<Void> addAdjustmentReceipt(ImAdjustmentReceiptDto imAdjustmentReceiptDto);


    /**
     * 调整库存中的商品
     *
     * @param imAdjustmentReceiptDto
     * @return
     */
    Response<Void> adjustInventoryItem(ImAdjustmentReceiptDto imAdjustmentReceiptDto);

    /**
     * glink库存调整
     *
     * @param gLinkImAdjustmentReceipt
     * @return
     */
    Response<Void> acceptGLinkImAdjustmentReceipt(KiddImAdjustmentReceiptDto gLinkImAdjustmentReceipt);


    /***
     * 调整第三方调整单为scm系统调整单
     * @param kiddImAdjustmentDataDto
     * @return
     */
    Response<Void> adjustImReceipt2SCMImAdjustmentReceipt(KiddImAdjustmentDataDto kiddImAdjustmentDataDto);
}
