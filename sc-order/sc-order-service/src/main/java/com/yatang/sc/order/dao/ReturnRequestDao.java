package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.returned.OrderReturnOperateMessagePo;
import com.yatang.sc.order.domain.returned.ReturnRequestListPo;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.domain.returned.ReturnRequestQueryParamPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReturnRequestDao {
    int deleteByPrimaryKey(String id);

    int insert(ReturnRequestPo record);

    int insertSelective(ReturnRequestPo record);

    ReturnRequestPo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReturnRequestPo record);

    int updateByPrimaryKey(ReturnRequestPo record);

    List<ReturnRequestListPo> queryReturnRequestList(ReturnRequestQueryParamPo convert);


/**********************************************ys******************************************************/
    /**
     * 根据订单编号编号查询
     *
     * @param orderId
     * @return
     */
    int selectCountByOrderId(String orderId);

    /**
     * 检查能否创建退换货单(是否有处于处理状态的退货单(已取消除外))
     *
     * @param orderId
     * @return
     */
    Integer getReturnReceiptCountByOrderId(String orderId);

    /**
     * 获取退货单状态
     *
     * @param returnId
     * @return
     */
    Short getOrderReturnedReceiptState(String returnId);

    /**
     * 退货单操作(取消,确认,关闭)
     *
     * @return
     */
    int operateOrderReturnedReceipt(OrderReturnOperateMessagePo operateMessagePo);

    Integer getReturnReceiptCountByOrderIdAndReturnRequestType(@Param(value = "orderId") String orderId, @Param(value = "returnRequestType") String returnRequestType);

    /**
     * 通过OrderId查询退货单信息
     * @param orderId
     * @return
     */
    ReturnRequestPo queryReturnRequestByOrderId(String orderId);
}