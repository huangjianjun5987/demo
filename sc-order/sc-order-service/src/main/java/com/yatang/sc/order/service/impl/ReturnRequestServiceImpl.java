package com.yatang.sc.order.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.yatang.sc.order.domain.ReturnOrderLogPo;
import com.yatang.sc.order.domain.returned.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.order.dao.ReturnOrderLogDao;
import com.google.common.base.Objects;
import com.yatang.sc.order.dao.ReturnRequestDao;
import com.yatang.sc.order.dao.ReturnRequestItemDao;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.service.ReturnRequestService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @描述: 退换货service实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/17 11:23
 * @版本: v1.0
 */
@Service
@Transactional
public class ReturnRequestServiceImpl implements ReturnRequestService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    private static final int DEF_DIV_SCALE = 10;

    @Autowired
    private ReturnRequestItemDao returnRequestItemDao;
    @Autowired
    private ReturnRequestDao returnRequestDao;

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderPriceService orderPriceService;
    @Autowired
    private PaymentGroupService paymentGroupService;
    @Autowired
    private ReturnOrderLogDao returnOrderLogDao;

    @Override
    public PageInfo<ReturnRequestListPo> queryReturnRequestList(ReturnRequestQueryParamPo convert) {
        PageHelper.startPage(convert.getPageNum(), convert.getPageSize());
        log.info("dao param:{}",JSON.toJSONString(convert));
        List<ReturnRequestListPo> returnRequestList = returnRequestDao.queryReturnRequestList(convert);
        PageInfo<ReturnRequestListPo> pageInfo = new PageInfo<ReturnRequestListPo>(returnRequestList);
        return pageInfo;
    }

    @Override
    public ReturnRequestPo selectByPrimaryKey(String id) {
        return returnRequestDao.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateDescriptionAndQuantity(ReturnRequestPo po,List<ReturnQuantityPo> returnQuantityPos) {
        if(CollectionUtils.isNotEmpty(returnQuantityPos)) {
            returnRequestItemDao.batchUpdateQuantityAndRawTotalPrice(returnQuantityPos);
        }
        return returnRequestDao.updateByPrimaryKeySelective(po) > 0;
    }


    /**********************************************ys******************************************************/
    @Override
    public boolean createOrderReturnedReceipt(ReturnRequestReceiptPo requestReceiptPo) {
        log.info("service--createOrderReturnedReceipt>>参数:{}", JSON.toJSONString(requestReceiptPo));
        //1.生成主表
        ReturnRequestPo returnRequest = requestReceiptPo.getReturnRequest();
        Date date = new Date();
        returnRequest.setCreationTime(date);
        boolean mainInsertSuccess = returnRequestDao.insertSelective(returnRequest) >= 1;
        //2.生成子表
        for (ReturnRequestItemPo requestItemPo : requestReceiptPo.getRequestItems()) {
            requestItemPo.setReturnId(returnRequest.getId());
            requestItemPo.setCreationTime(date);
        }
        //批量插入
        boolean itemsInsetSuccess = returnRequestItemDao.batchInsertRequestItems(requestReceiptPo.getRequestItems()) >= 1;
        return mainInsertSuccess && itemsInsetSuccess;
    }

    @Override
    public int operateOrderReturnedReceipt(OrderReturnOperateMessagePo operateMessageDto) {

        log.info("service--operateOrderReturnedReceipt>>退换货单请求参数:{}", JSON.toJSONString(operateMessageDto));
        //dto2po
        OrderReturnOperateMessagePo operateMessagePo = BeanConvertUtils.convert(operateMessageDto, OrderReturnOperateMessagePo.class);
        int i = returnRequestDao.operateOrderReturnedReceipt(operateMessagePo);
        ReturnOrderLogPo returnOrderLogPo = new ReturnOrderLogPo();
        returnOrderLogPo.setCreateDate(new Date());//操作时间
        returnOrderLogPo.setReturnState(operateMessagePo.getOperateType());//操作状态
        returnOrderLogPo.setOperater(operateMessagePo.getUserId());//操作人员id
        returnOrderLogPo.setReturnId(operateMessagePo.getReturnId());//退货单号
        returnOrderLogDao.insert(returnOrderLogPo);
        return i;
    }

    @Override
    public Integer getReturnReceiptCountByOrderId(String orderId) {
        return returnRequestDao.getReturnReceiptCountByOrderId(orderId);
    }


    @Override
    public Short getOrderReturnedReceiptState(String returnId) {
        return returnRequestDao.getOrderReturnedReceiptState(returnId);
    }

    @Override
    public Integer updateOrderReturnedReturnQuantity(String returnId) {
        return null;
    }

    @Override
    public boolean updateReturnedOrderReceipt(ReturnRequestReceiptPo receiptPo) {

        log.info("service---updateReturnedOrderReceipt>>更新换货数据:{}", JSON.toJSONString(receiptPo));
        //1.更新主表
        boolean mainUpdateSuccess = returnRequestDao.updateByPrimaryKeySelective(receiptPo.getReturnRequest()) == 1;
        boolean itemUpdateSuccess = returnRequestItemDao.batchUpdateRequestItems(receiptPo.getRequestItems()) >= 1;
        //2.批量更新item
        return mainUpdateSuccess && itemUpdateSuccess;
    }

    @Override
    public boolean deliveryExchangeOrderReceipt(ReturnRequestPo returnRequest) {
        log.info("service---deliveryExchangeOrderReceipt>>更新商品状态为已发出请求参数:{}", JSON.toJSONString(returnRequest));
        return returnRequestDao.updateByPrimaryKeySelective(returnRequest) == 1;
    }

    @Override
    public Integer getReturnReceiptCountByOrderIdAndReturnRequestType(String orderId, String returnRequestType) {
        return returnRequestDao.getReturnReceiptCountByOrderIdAndReturnRequestType(orderId,returnRequestType);
    }

    /**
     * 通过OrderId查询退货单信息
     * @param orderId
     * @return
     */
    @Override
    public ReturnRequestPo queryReturnRequestByOrderId(String orderId) {
         return returnRequestDao.queryReturnRequestByOrderId(orderId);
    }

    @Override
    public OrderRefundPricePo refundPrice(Double price, String orderId) {
        Order order = orderService.selectByPrimaryKey( orderId );
        log.info( "查询订单信息 order {}" + JSON.toJSONString( order ) );
        if (null == order) {
            throw new RuntimeException("订单不存在");
        }
        OrderPrice orderPrice = orderPriceService.selectByPrimaryKey( order.getPriceInfo() );
        log.info( "查询订单价格信息 order {}" + JSON.toJSONString( orderPrice ) );
        if (null == orderPrice) {
            throw new RuntimeException("订单价格不存在");
        }
        Double amount = orderPrice.getAmount();//用户实际付款金额
        Double rawSubtotal = orderPrice.getRawSubtotal();//商品未进行优惠时的价格
        BigDecimal decimalAmount = new BigDecimal( amount.toString() );
        BigDecimal decimalRawSubtotal = new BigDecimal( rawSubtotal.toString() );
        BigDecimal decimalPrice = new BigDecimal( price.toString() );
        Double value = decimalAmount.divide( decimalRawSubtotal, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP ).doubleValue();//商品去除优惠实付比例
        BigDecimal decimalValue = new BigDecimal( value.toString() );
        double value1 = decimalPrice.multiply( decimalValue ).doubleValue();//实际应退款金额
        BigDecimal bg = new BigDecimal(value1);
        double doubleValue = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//实际应退款金额保留两位小数

        OrderRefundPricePo orderRefundPrice = new OrderRefundPricePo();
        orderRefundPrice.setAmount( doubleValue );

        List<PaymentGroup> groupForOrderId = paymentGroupService.getPaymentGroupForOrderId( orderId );
        log.info( "查询订单支付信息 order {}" + JSON.toJSONString( groupForOrderId ) );
        if (null == groupForOrderId || groupForOrderId.size() == 0) {
            throw new RuntimeException("订单价格支付不存在");
        }
        PaymentGroup paymentGroup = groupForOrderId.get( 0 );
        orderRefundPrice.setPaytype( paymentGroup.getChannel() );//设置支付渠道
        orderRefundPrice.setPayTradeNo(paymentGroup.getTransNum());//当前支付流水交易号
        orderRefundPrice.setTotalAmount(amount);//订单实际付款总额
        return orderRefundPrice;
    }
}
