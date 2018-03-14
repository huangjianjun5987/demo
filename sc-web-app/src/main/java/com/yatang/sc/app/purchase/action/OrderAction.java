package com.yatang.sc.app.purchase.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yatang.sc.order.states.*;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.app.vo.CommerceItemAppVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.app.action.AppBaseAction;
import com.yatang.sc.app.res.ActionResponse;
import com.yatang.sc.app.vo.CancelOrderRequestVo;
import com.yatang.sc.app.vo.ItemAndPriceVo;
import com.yatang.sc.app.vo.OrderDetailVo;
import com.yatang.sc.app.vo.imgconfig.AppConfig;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.utils.DateUtil;
import com.yatang.sc.dto.CancelOrderRequestDto;
import com.yatang.sc.order.dubboservice.CancelOrderDubboService;
import com.yatang.sc.order.dubboservice.OrderPaymentDubboService;
import com.yatang.sc.purchase.dto.OrderClassificationDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.OrderProductListDto;
import com.yatang.sc.purchase.dto.OrderSummaryDto;
import com.yatang.sc.purchase.dto.PaymentGroupDto;
import com.yatang.sc.purchase.dubboservice.QueryNoPayTimeDubboService;
import com.yatang.sc.purchase.dubboservice.QueryOrderDetailDubboService;
import com.yatang.sc.purchase.dubboservice.QueryOrderDubboService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/13 10:49
 * @版本:v1.0
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/sc/order")
public class OrderAction extends AppBaseAction {
    private final QueryOrderDetailDubboService queryOrderDetailDubboService;

    private final QueryOrderDubboService queryOrderDubboService;

    private final AppConfig appConfig;

    private final CancelOrderDubboService mCancelOrderDubboService;

    private final OrderPaymentDubboService mOrderPaymentDubboService;

    private final QueryNoPayTimeDubboService queryNoPayTimeDubboService;

    /**
     * 查询订单明细
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "queryOrderDetail", method = RequestMethod.GET)
    public Response<OrderDetailVo> loadShoppingCart(@RequestParam String id) {
        Response<OrderDetailVo> orderDetailVoResponse = new Response<>();
        //先判断订单是不是这个user的  通过orderId找到userId
        String userId = getUserId();
        Response<OrderDto> orderDtoResponse = queryOrderDetailDubboService.queryOrderDetaiById(id);
        log.debug("查询到订单：{}", JSON.toJSONString(orderDetailVoResponse));
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        if (orderDtoResponse.isSuccess() && orderDtoResponse.getResultObject() != null) {
            OrderDto orderDto = orderDtoResponse.getResultObject();
            if (!orderDto.getProfileId().equals(userId)) {
                orderDetailVoResponse.setErrorMessage("订单所属用户信息不同于当前用户");
                orderDetailVoResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
                orderDetailVoResponse.setSuccess(false);
                return ActionResponse.wrap(orderDetailVoResponse);
            }
            //得到当前未付款的剩余时间
            if (PaymentStates.PENDING_PAYMENT.equals(orderDto.getPaymentState())) {
                Response<Long> res = queryNoPayTimeDubboService.getLeftNoPayTime(orderDto.getBranchCompanyId());
                Long noPayTime = res.getResultObject();
                orderDetailVo.setLeftNoPayTime(noPayTime);
                if (CommonsEnum.RESPONSE_500.getCode().equals(res.getCode())) {
                    orderDetailVoResponse.setErrorMessage("查询订单未付款剩余时间出错");
                    orderDetailVoResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
                    orderDetailVoResponse.setSuccess(false);
                    return ActionResponse.wrap(orderDetailVoResponse);
                }
            }
            orderDetailVo.setState(orderDto.getState());
            orderDetailVo.setReturnState(orderDto.getReturnState());
            orderDetailVo.setReturnStateDesc(orderDto.getReturnStateDesc());
            orderDetailVo.setConsigneeName(orderDto.getShippingGroupDto().getConsigneeName());
            orderDetailVo.setCellphone(orderDto.getShippingGroupDto().getCellphone());
            orderDetailVo.setProvince(orderDto.getShippingGroupDto().getProvince());
            orderDetailVo.setCity(orderDto.getShippingGroupDto().getCity());
            orderDetailVo.setDistrict(orderDto.getShippingGroupDto().getDistrict());
            orderDetailVo.setDetailAddress(orderDto.getShippingGroupDto().getDetailAddress());
            if (orderDto.getCompletedTime() != null) {
                boolean deadLine = DateUtil.isDeadLine(orderDto.getCompletedTime(), ReturnedOrderConstants.VALID_DAYS);//判定
                orderDetailVo.setValid(deadLine);
            }

            orderDetailVo.setItems(BeanConvertUtils.convertList(orderDto.getItems(), CommerceItemAppVo.class));
            orderDetailVo.setRawSubtotal(orderDto.getPriceInfoDto().getRawSubtotal());
            orderDetailVo.setShipping(orderDto.getPriceInfoDto().getShipping());
            if(orderDto.getShippingGroupDto() != null){
                orderDetailVo.setShippingModes(orderDto.getShippingGroupDto().getShippingModes());
            }
            orderDetailVo.setDiscountAmount(orderDto.getPriceInfoDto().getDiscountAmount()); //活动优惠
            orderDetailVo.setCouponDiscountAmount(orderDto.getPriceInfoDto().getCouponDiscountAmount()); //优惠券折扣
            orderDetailVo.setUserDiscountAmount(orderDto.getPriceInfoDto().getUserDiscountAmount());//会员等级折扣
            orderDetailVo.setTotal(orderDto.getPriceInfoDto().getTotal());
            List<Date> dateList = new ArrayList<>();
            List<String> paymentmethodLists = new ArrayList<>();
            List<PaymentGroupDto> paymentGroupDtos = orderDto.getPaymentGroupDtos();
            for (PaymentGroupDto paymentGroupDto : paymentGroupDtos) {
                if (paymentGroupDto == null) {
                    continue;
                }
                if (paymentGroupDto.getPayDate() != null) {
                    dateList.add(paymentGroupDto.getPayDate());
                }
                String payChannel = paymentGroupDto.getChannel();
                if (StringUtils.isNotEmpty(payChannel)) {
                    paymentmethodLists.add(PayChannel.payChannelDesc.get(payChannel));
                }
            }

            orderDetailVo.setPayDates(dateList);
            orderDetailVo.setPaymentMethods(paymentmethodLists);
            orderDetailVo.setId(orderDto.getId());
            orderDetailVo.setCreationTime(orderDto.getCreationTime());
            orderDetailVo.setActualShipDate(orderDto.getShippingGroupDto().getActualShipDate());
            orderDetailVo.setInvoiceType(orderDto.getInvoiceInfoDto().getInvoiceType());
            orderDetailVo.setInvoiceTitle(orderDto.getInvoiceInfoDto().getInvoiceTitle());
            orderDetailVo.setCompanyName(orderDto.getInvoiceInfoDto().getCompanyName());
            orderDetailVo.setShippingMethod(orderDto.getShippingGroupDto().getShippingMethod());
            orderDetailVo.setShippingDetail(OrderTotalStates.getStateByValue(orderDto.getState()).getDescription());
            orderDetailVoResponse.setResultObject(orderDetailVo);
            orderDetailVoResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
            orderDetailVoResponse.setSuccess(true);
        }
        return ActionResponse.wrap(orderDetailVoResponse);
    }

    @RequestMapping(value = "queryOrderList", method = RequestMethod.POST)
    public Response<PageResult<OrderSummaryDto>> queryOrder(@RequestBody OrderClassificationDto orderClassificationDto) {
        String userId = getUserId();
        orderClassificationDto.setProfileId(userId);
        Response<PageResult<OrderSummaryDto>> dfs = ActionResponse.wrap(queryOrderDubboService.queryOrder(orderClassificationDto));
        PageResult<OrderSummaryDto> pageResult = dfs.getResultObject();
        if (pageResult == null) {
            return dfs;
        }
        List<OrderSummaryDto> orderSummaryDtos = pageResult.getData();
        if (orderSummaryDtos != null && orderSummaryDtos.size() != 0) {
            for (OrderSummaryDto orderSummaryDto : orderSummaryDtos) {
                List<OrderProductListDto> orderProductListDtos = orderSummaryDto.getProductList();
                if (orderProductListDtos != null && orderProductListDtos.size() != 0) {
                    for (OrderProductListDto orderProductListDto : orderProductListDtos) {
                        orderProductListDto.setIconUrl(appConfig.getAppImageUrl(orderProductListDto.getIconUrl()));
                    }
                }
            }
        }
        return dfs;
    }

    @RequestMapping(value = "queryOrderTotal", method = RequestMethod.GET)
    public Response<ItemAndPriceVo> getTotal(String orderId) {
        Response<ItemAndPriceVo> itemAndPriceVoResponse = new Response<>();
        ItemAndPriceVo itemAndPriceVo = new ItemAndPriceVo();
        String userId = getUserId();
        Response<String> useIdResponse = queryOrderDetailDubboService.queryUserIdByOrderId(orderId);
        String userIdInDB = useIdResponse.getResultObject();
        if (userIdInDB != null) {
            if (userId.equals(userIdInDB)) {
                Response<Map<String, Object>> mapResponse = queryOrderDetailDubboService.queryPaymentTotal(orderId);
                if (mapResponse.isSuccess() && mapResponse.getResultObject() != null) {
                    Map<String, Object> map = mapResponse.getResultObject();
                    if (map.get("item") != null) {
                        itemAndPriceVo.setItem((String) map.get("item"));
                    }
                    if (map.get("total") != null) {
                        itemAndPriceVo.setTotal((double) map.get("total"));
                    }
                }
                itemAndPriceVoResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                itemAndPriceVoResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
                itemAndPriceVoResponse.setResultObject(itemAndPriceVo);
                itemAndPriceVoResponse.setSuccess(true);
            }
        }
        return ActionResponse.wrap(itemAndPriceVoResponse);
    }

    /**
     * 取消单个订单
     * @param cancelOrderRequestVo
     * @return
     */
    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    public Response<String> cancelOrder(@RequestBody @Validated CancelOrderRequestVo cancelOrderRequestVo) {
        Response<String> orderDetailVoResponse = new Response<>();
        log.debug("request param:", cancelOrderRequestVo);
        String userId = getUserId();
        Response<OrderDto> orderDtoResponse = queryOrderDetailDubboService.queryOrderDetaiById(cancelOrderRequestVo.getId());
        if (orderDtoResponse.isSuccess() && orderDtoResponse.getResultObject() != null) {
            OrderDto orderDto = orderDtoResponse.getResultObject();
            if (!orderDto.getProfileId().equals(userId)) {
                orderDetailVoResponse.setErrorMessage("订单所属用户信息不同于当前用户");
                orderDetailVoResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
                orderDetailVoResponse.setSuccess(false);
                return ActionResponse.wrap(orderDetailVoResponse);
            }
        }
        CancelOrderRequestDto cancelOrderRequestDto = new CancelOrderRequestDto();
        cancelOrderRequestDto.setOrderId(cancelOrderRequestVo.getId());
        cancelOrderRequestDto.setRemark(cancelOrderRequestVo.getRemark());
        cancelOrderRequestDto.setOperatorName(userId);
        cancelOrderRequestDto.setOperatorId(userId);
        return ActionResponse.wrap(mCancelOrderDubboService.cancelOrder(cancelOrderRequestDto));
    }
}
