package com.yatang.sc.order.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.DeliverInfoDto;
import com.yatang.sc.dto.WebShippingGroupDto;
import com.yatang.sc.purchase.dto.*;

import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
public interface WebOrderDubboService {

    /**
     * 分页查询订单列表
     * @param condition
     * @return
     */
    Response<PageResult<WebQueryOrderDto>> queryOrder(WebQueryOrderConditionDto condition);

    /**
     * 发货
     * @param deliverInfoDto
     * @param userId
     * @return
     */
    Response<Boolean> deliverGoods(DeliverInfoDto deliverInfoDto, String userId);

    /**
     * 订单备注
     * @param orderId
     * @param description
     * @param userId
     * @return
     */
    Response<Boolean> orderDescription(String orderId, String description,String userId);

    /**
     * 获取打印信息
     * @param orderId
     * @return
     */
    Response<PrintOrderDto> getPrintInfo(String orderId);

    /**
     * 批量审核
     * @param ids
     * @param userId
     * @return
     */
    Response<Boolean> batchApproval(String[] ids,String userId);

    Response<OrderManageDetailDto> queryOrderDeatil(String id);

    Response<WebQueryPaymentInfoDto> getOrderPaymentInfo(String orderId);

    /**
     * 配送信息查询
     * @param id
     * @return
     */
    Response<WebShippingGroupDto> shippingGroupInfo(String id);


    Response<Boolean> approvalOrder(String orderId,String userId);

    /**
     * 更新订单的物流状态
     * @param orderId
     * @return
     */
    Response<Boolean> updateShippingState(String orderId);

    /**
     * 重新发送
     * @param id
     * @param userId
     * @return
     */
    Response<Boolean> resendOrder(String id, String userId);

    /**
     * 根据库存拆单
     * @param orderId,operator
     * @return
     */
    Response<Boolean> splitOrderByInventory(String orderId,String operator);

    /**
     * 手动拆单
     *
     * @param parentOrderId
     * @param groups
     * @param operator
     * @return
     */
    Response<Boolean> manualSplitOrder(String parentOrderId, List<Map<String, Long>> groups, String operator);

    /**
     * 分页查询增强版订单列表
     * @param condition
     * @return
     */
    Response<PageResult<OrderEnhancedDto>> getEnhancedOrderPageList(WebQueryOrderConditionDto condition);


    /**
     *
     * @param orderId
     * @return
     */
    Response<String> queryProfileIdByOrderId(String orderId);

}
