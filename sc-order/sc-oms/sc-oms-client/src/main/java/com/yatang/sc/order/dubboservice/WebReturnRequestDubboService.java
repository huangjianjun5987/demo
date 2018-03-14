package com.yatang.sc.order.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.OrderRefundDto;
import com.yatang.sc.purchase.dto.ReturnRequestDescriptionDto;
import com.yatang.sc.purchase.dto.ReturnRequestDetailDto;
import com.yatang.sc.purchase.dto.ReturnRequestListDto;
import com.yatang.sc.purchase.dto.ReturnRequestQueryParamDto;

/**
 * @描述: Web端退换货
 * @类名: WebReturnRequestDubboService
 * @作者: kangdong
 * @创建时间: 2017/10/23 14:54
 * @版本: v1.0
 */
public interface WebReturnRequestDubboService {
    /**
     *  根据条件查询退换货单列表
     * @param paramDto
     * @return
     */
    Response<PageResult<ReturnRequestListDto>> queryReturnRequestList(ReturnRequestQueryParamDto paramDto);

    /**
     * 查询退换货单详情
     * @param id
     * @return
     */
    Response<ReturnRequestDetailDto> returnRequestDetail(String id);

    /**
     * 保存备注
     * @param descriptionDto
     * @return
     */
    Response<Boolean> updateDescriptionAndQuantity(ReturnRequestDescriptionDto descriptionDto);

    /**
     * 通过订单ID查询退货单订单总金额
     * @param orderId
     * @return
     */
    Response<ReturnRequestDetailDto> queryReturnRequestByOrderId(String orderId);

    /**
     * 根据退货单号查询退款记录条数
     * @param returnOrderId
     * @return
     */
    Response<Integer> selectByReturnOrderId(String returnOrderId);

    /**
     * 插入退款退货单关系记录
     * @param orderRefundDto
     * @return
     */
    Response<Boolean> insert(OrderRefundDto orderRefundDto);
}
