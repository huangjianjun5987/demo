package com.yatang.sc.order;

import java.math.BigDecimal;
import java.util.List;

import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.order.domain.returned.OrderRefundPricePo;
import com.yatang.sc.order.service.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.ItemPrice;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.dubboservice.OrderRefundDubboService;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.purchase.dto.OrderRefundPriceDto;
import com.yatang.sc.purchase.dto.returned.OrderPreReturnRequestReceiptDto;
import com.yatang.sc.purchase.dto.returned.OrderPreReturnedCalculateAmountDto;
import com.yatang.sc.purchase.dto.returned.OrderReturnRequestItemDto;

/**
 * @描述:计算使用优惠卷后的退款金额实现类
 * @类名:OrderRefundDubboServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/11/3 14:19
 * @版本: v1.0
 */


@Service("orderRefundDubboService")
public class OrderRefundDubboServiceImpl implements OrderRefundDubboService {

    private Logger log = LoggerFactory.getLogger( this.getClass() );

    @Autowired
    private PaymentGroupService paymentGroupService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderPriceService orderPriceService;
    @Autowired
    private ReturnRequestService returnRequestService;


    @Autowired
    private CommerceItemService commerceItemService;

    private static final int DEF_DIV_SCALE = 10;

    /**
     * 计算使用优惠卷后的退款金额
     *
     * @param price   实际退货数量原价格
     * @param orderId 原订单id
     * @return
     */
    @Override
    public Response<OrderRefundPriceDto> refundPrice(Double price, String orderId) {
        log.info( "实际退货金额计算传入参数信息 price :{}, orderId:{} ", price, orderId );
        Response response = new Response();
        try {
            OrderRefundPricePo orderRefundPrice = returnRequestService.refundPrice(price, orderId);

            response.setResultObject(BeanConvertUtils.convert(orderRefundPrice, OrderRefundPriceDto.class));

            response.setSuccess( true );
            response.setCode( CommonsEnum.RESPONSE_200.getCode() );
            response.setErrorMessage( CommonsEnum.RESPONSE_200.getName() );
        } catch (Exception e) {
            log.info( "错误信息 " + e.getMessage() );
            response.setCode( CommonsEnum.RESPONSE_500.getCode() );
            response.setErrorMessage( CommonsEnum.RESPONSE_500.getName() );
            response.setSuccess( false );
        }
        return response;
    }

    @Override
    public Response<OrderPreReturnedCalculateAmountDto> calculatePreOrderReturnedAmount(OrderPreReturnRequestReceiptDto receiptDto) {
        log.info( "dubbo--calculatePreOrderReturnedAmount>>请求参数:{} ", JSON.toJSONString( receiptDto ) );

        Response<OrderPreReturnedCalculateAmountDto> response = new Response<OrderPreReturnedCalculateAmountDto>();
        try {
            OrderPreReturnedCalculateAmountDto calculateAmountDto = new OrderPreReturnedCalculateAmountDto();
            String orderId = receiptDto.getRequest().getOrderId();//获取订单信息
            Order order = orderService.selectByPrimaryKey( orderId );
            log.info( "查询订单信息 order {}" + JSON.toJSONString( order ) );
            if (null == order) {
                response.setCode( CommonsEnum.RESPONSE_10006.getCode() );
                response.setSuccess( false );
                response.setErrorMessage( CommonsEnum.RESPONSE_10006.getName() );
            }

          /*  //订单类型判断
            if ((!OrderTypes.NORMAL_SALE.equals( order.getOrderType() ) && (!OrderTypes.DIRECT_STORE.equals( order.getOrderType() )))) {//或者直营店,正常商品才能执行退换货操作
                log.error( "flow--addOrderReturnReceiptDetail>>订单号【{}】的订单类型为:{},不能执行退换货操作", orderId, order.getOrderType() );
                response.setSuccess( false );
                response.setCode( CommonsEnum.RESPONSE_20118.getCode() );
                response.setErrorMessage( CommonsEnum.RESPONSE_20118.getName() );
                return response;
            }
*/
            //1.计算退换货金额
            List<OrderReturnRequestItemDto> requestItems = receiptDto.getItems();//获取商品id
            List<CommerceItem> items = commerceItemService.getCommerceItemForOrderId( orderId );
            double productReturnedAmount = 0.0;//退货金额累加
            for (OrderReturnRequestItemDto requestItem : requestItems) {
                for (CommerceItem commerceItem : items) {
                    if (commerceItem.getProductId().equals( requestItem.getProductId() )) {
                        //校验退货数量小于等于(签收数量-已经退货数量）//已经签收和未签收判定

                        Long quantity = 0l;
                        if (OrderTotalStates.COMPLETED.getStateValue() == order.getState()) {//已完成
                            quantity = commerceItem.getCompletedQuantity();//已完成用签收数量
                        } else {
                            quantity = commerceItem.getQuantity();//未完成用下单数量
                        }
                        Long orderReturnQuantity = requestItem.getReturnQuantity() == null ? 0 : requestItem.getReturnQuantity();//将要退货的数量
                        Long alreadyReturnedQuantity = commerceItem.getReturnQuantity();//已经退货数量
                        if (orderReturnQuantity <= (quantity - alreadyReturnedQuantity)) {
                            //价格
                            ItemPrice itemPrice = commerceItem.getItemPrice();//获取销售价格
                            Double salePrice = itemPrice.getSalePrice();
                            BigDecimal bigDecimal = new BigDecimal( salePrice * orderReturnQuantity ).setScale( 2, BigDecimal.ROUND_HALF_UP );// 四舍五入
                            productReturnedAmount += bigDecimal.doubleValue();
                            //价格
                        } else {//校验失败
                            log.error( "退换货校验:订单编号:{}中的商品id:[{}]的退换货数量:{}大于订单的签收数量:{}减去已经退货数量:{}", receiptDto.getRequest().getOrderId(), commerceItem.getProductId(), orderReturnQuantity, quantity, alreadyReturnedQuantity );
                            response.setCode( CommonsEnum.RESPONSE_20103.getCode() );
                            response.setErrorMessage( "订单id为" + receiptDto.getRequest().getOrderId() + CommonsEnum.RESPONSE_20103.getName() );
                            response.setSuccess( false );
                            return response;
                        }
                        break;
                    }
                }

            }
            calculateAmountDto.setProductReturnedAmount( productReturnedAmount );
            //2.实际退款金额

            OrderPrice orderPrice = orderPriceService.selectByPrimaryKey( order.getPriceInfo() );
            log.info( "查询订单价格信息 order {}" + JSON.toJSONString( orderPrice ) );
            if (null == orderPrice) {
                response.setCode( CommonsEnum.RESPONSE_10006.getCode() );
                response.setSuccess( false );
                response.setErrorMessage( CommonsEnum.RESPONSE_10006.getName() );
            }
            Double amount = orderPrice.getAmount();//用户实际付款金额
            Double rawSubtotal = orderPrice.getRawSubtotal();//商品未进行优惠时的价格
            BigDecimal amountBigDecimal = new BigDecimal( amount.toString() );
            BigDecimal rawSubTotalBigDecimal = new BigDecimal( rawSubtotal.toString() );
            BigDecimal productReturnedAmountBigDecimal = new BigDecimal( productReturnedAmount );
            Double rate = amountBigDecimal.divide( rawSubTotalBigDecimal, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP ).doubleValue();//商品去除优惠实付比例
            double refundAmount = productReturnedAmountBigDecimal.multiply( new BigDecimal( rate ) ).doubleValue();//实际应退款金额
            BigDecimal bg = new BigDecimal(refundAmount);
            double doubleValue = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            calculateAmountDto.setRefundAmount( doubleValue );//实际应退款金额保留两位小数
            response.setSuccess( true );
            response.setResultObject( calculateAmountDto );
        } catch (Exception e) {
            log.error( ExceptionUtils.getFullStackTrace( e ) );
            response.setCode( CommonsEnum.RESPONSE_500.getCode() );
            response.setErrorMessage( CommonsEnum.RESPONSE_500.getName() );
            response.setSuccess( false );
        }
        return response;
    }

}
