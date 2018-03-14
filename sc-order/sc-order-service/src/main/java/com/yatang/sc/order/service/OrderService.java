package com.yatang.sc.order.service;

import com.busi.common.resp.Response;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderEnhancedPo;
import com.yatang.sc.order.domain.orderIndex.OrderDetailIndex;
import com.yatang.sc.vo.UpdateOrderVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/7/10.
 */
public interface OrderService {
    List<Order> getOrder(Map<String,Object> map);

    Order  selectByPrimaryKey(String id);

    Long getOrderCount(Map<String,Object> map);

    int updateOrderSelective(Order order);

    void save(Order order);

    Long getOrderPageListCount(Map<String,Object> map);

    List<Order> getOrderPageList(Map<String, Object> condition);

    void update(Order order);

    void updateAndSaveLog(UpdateOrderVO pUpdateOrderVO);

    boolean updateOrderDes(Map<String, String> map);

    List<Order> getOrdersByState(Map<String, Object> condition);

    Boolean updateOrderStateByNewState(List<Order> orders);

    List<Order> getSubOrders(String parentId);

    OrderDetailIndex loadOrderDetail(String orderId);

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

    /**
     * 根据第三方订单号查询订单
     * @param thirdPartOrderNo
     * @return
     */
    Order queryThirdOrderByThirdOrderNo(String thirdPartOrderNo);
    
    /**
	 * 确认收货返券
	 * @param refuseAmount 拒收金额
	 * @param discountNumeric 打折比率
	 * @param priceInfoId 价格信息ID
	 * @return 
	 */
	Response<String> giveCouponToStore(String franchiseeStoreId,double refuseAmount, Long priceInfoId);

	/**
	 * 回滚优惠券
	 * @param priceInfoId
	 * @return
	 */
	Response<String> revertCoupons(String orderId,Long priceInfoId);

    /**
     * 通过订单号获取加盟商编号
     * @param orderId
     * @return
     */
    String queryProfileIdByOrderId(String orderId);

    /**
     * 获取需要自动收货的订单
     * @param time 发货时间节点(节点之前会被自动收货)
     * @return
     */
    List<String> queryReadyToAutoReceiveOrderId(Date time);

    /**
     * 获取需要检查库存的订单
     * @param
     * @return
     */
    List<Order> getOrdersForCheckInventory();

    /**
     * 通过收货单 查询订单
     * @param receiptNo
     * @return
     */
    Order getOrderByReceiptNo(String receiptNo);
}
