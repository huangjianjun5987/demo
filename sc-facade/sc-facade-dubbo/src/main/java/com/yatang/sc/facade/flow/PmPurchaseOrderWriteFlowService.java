package com.yatang.sc.facade.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptSHDto;

/**
 * @描述: 商品采购单的writeFlow接口
 * @类名:
 * @作者: liuxiaokun
 * @创建时间: 2017/12/20 10:20
 */
public interface PmPurchaseOrderWriteFlowService {
    /**
     * 新增商品采购订单，如果为已提交，则下发审批流
     * @author liuxiaokun
     * @param pmPurchaseOrderExtDto
     * @return String 返回采购订单主键
     */
    Response<String> addPmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto);
    /**
     * 更新商品采购订单，如果为已提交，则下发审批流
     * @author liuxiaokun
     * @param pmPurchaseOrderExtDto
     * @return
     */
    Boolean updatePmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto);



	/**
	 *
	 * <method description>采购订单收货
	 *@author zhoubaiyun
	 * @return
	 */
    Response<Boolean> updatePmPurchaseOrderReceipt(PmPurchaseReceiptSHDto pmPurchaseReceiptSHDto);
}
