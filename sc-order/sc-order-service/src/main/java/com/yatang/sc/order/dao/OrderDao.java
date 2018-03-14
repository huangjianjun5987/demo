package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderEnhancedPo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<Order> getOrder(Map<String, Object> map);

    Long getOrderCount(Map<String, Object> map);

    Long getOrderPageListCount(Map<String, Object> map);

    List<Order> getOrderPageList(Map<String, Object> condition);

    Integer updateOrderDes(Map<String, String> map);

    List<Order> getOrdersByState(Map<String, Object> condition);

    Integer updateOrderStateByNewState(List<Order> orders);

    List<Order> getSubOrders(String parentId);

    /**
     * 查询所有的订单id
     */
    List<String> getAllOrderId(Map<String,Object> condition);

    long getAllOrderCount();

    /**
     * 查询增强版订单列表
     */
    List<OrderEnhancedPo> getEnhancedOrderPageList(Map<String, Object> map);

    /**
     * 查询增强版订单数量
     */
    Long getEnhancedOrderPageCount(Map<String, Object> map);


    Order queryThirdOrderByThirdOrderNo(String thirdPartOrderNo);

    String queryProfileIdByOrderId(String orderId);

    /**
     * 查询所有需要自动收货订单ID
     * @param time 发货时间节点(节点之前会被自动收货)
     * @return
     */
    List<String> getReadyToAutoReceiveOrderId(Date time);

    List<Order> getOrdersForCheckInventory();

    /**
     * 通过收货单 查询订单
     * @param receiptNo
     * @return
     */
    Order getOrderByReceiptNo(String receiptNo);

}