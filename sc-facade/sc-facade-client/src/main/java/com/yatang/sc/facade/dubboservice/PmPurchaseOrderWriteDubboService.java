package com.yatang.sc.facade.dubboservice;

import java.util.List;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.pm.ClosePurchaseDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptSHDto;

/**
 * @描述: 商品采购单的写操作dubbo服务接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:18
 * @版本: v1.0
 */
public interface PmPurchaseOrderWriteDubboService {


    /**
     * 新增商品采购订单
     *
     * @param pmPurchaseOrderExtDto
     * @return
     */
    Response<String> addPmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto);
    /**
     * 批量删除(草稿状态的商品,物理删除 草稿状态)
     *
     * @param pmPurchaseOrderIdList 订单集合
     * @return
     */
    Response<Void> batchDeletePmPurchaseOrderByIds(List<String> pmPurchaseOrderIdList);

    /**
     * 更改商品采购订单
     *
     * @param pmPurchaseOrderExtDto 采购订单ext类
     * @return
     */
    Response<Void> updatePmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto);

    /**
     * 审批流回调接口
     * @param keyId 采购退货单主键id
     * @param auditorId 审核人id
     * @param auditorName 审核人姓名
     * @param auditorResult 审核结果 true 同意  false 拒绝
     * @return
     */
    Response<Boolean> auditCallbackPurchaseOrderInfo(Long keyId,String auditorId,String auditorName,Boolean auditorResult);
    /**
     *
     * <method description> 批量关闭订单服务
     *
     * @param closePurchaseDto
     * @return
     */
    Response<Boolean> batchClosePurcharse(ClosePurchaseDto closePurchaseDto);

    /**
     * 根据订单id删除(草稿状态的商品,物理删除 草稿状态)
     *
     * @param orderId 订单集合
     * @return
     */
    Response<Void> deletePmPurchaseOrderById(Long orderId);
    
	/**
	 * @Description: 根据单号更新采购单状态
	 * @author huangjianjun
	 * @date 2017年12月11日下午4:11:01
	 * @param orderNo
	 * @param status
	 */
    Response<Boolean> updateStatus(String orderNo,Integer status);



    /**
     * 更新供应商接单状态
     *
     * @param orderId 采购单id
     * @param updateUserId 更新人id
     * @author yangshuang
     * @return Void
     */
    Response<Void> updateSpAcceptStatus(Long orderId, String updateUserId);

    /**
     * 新增直送商品采购订单（供应商直送）
     *
     * @param pmPurchaseOrderExtDto
     * @return
     */
    Response<Void> addDirectSendingPmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto);
	/**
	 *
	 * <method description>更新收货单状态为已收货(直送到仓库收货)-接口设计-采购订单收货 [2.5]
	 *
	 * @author zhoubaiyun
	 * @param pmPurchaseReceiptSHDto
	 * @return
	 */
	public Response<Boolean> updatePurchaseReceiptSHStatus(PmPurchaseReceiptSHDto pmPurchaseReceiptSHDto);
}
