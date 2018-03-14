package com.yatang.sc.timedtask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.staticvalue.DefaultNoPayEnum;
import com.yatang.sc.facade.dto.WarehouseLogicDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.order.domain.CompanyCancelNoPayPo;
import com.yatang.sc.order.service.OrderPaymentsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.OrderDictionaryPropertiesService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.CancelOrderStates;
import com.yatang.sc.order.states.PaymentStates;

/**
 * Created by liusongjie on 2017/7/27.
 */
@Component("cancelNonpaymentOrderScheduler")
public class CancelNonpaymentOrderScheduler implements Scheduler {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    @Autowired
    OrderService orderService;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    OrderDictionaryPropertiesService orderDictionaryPropertiesService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private OrderPaymentsService orderPaymentsService;

    @Autowired
    private WarehouseLogicQueryDubboService mWarehouseLogicQueryDubboService;

    public void execute() {
        log.info("cancelNonpaymentOrderScheduler execute start.");

        //获得子公司设置的未按时付款的时间信息
        List<CompanyCancelNoPayPo> listNoPays = orderPaymentsService.getCompanyCancelNoPay();
        log.info("取消未付款配置信息：{}",JSON.toJSONString(listNoPays));
        Response<List<WarehouseLogicDto>> warehouseLogic = mWarehouseLogicQueryDubboService.queryWarehouseLogicList();
        List<WarehouseLogicDto> warehouseLogicDtos = warehouseLogic.getResultObject();
        if (CollectionUtils.isEmpty(warehouseLogicDtos)) {
            log.warn("未查询到开仓数据");
            return;
        }

        for (WarehouseLogicDto warehouseLogicDto : warehouseLogicDtos) {
            int cancelTime = getCancelTime(listNoPays, warehouseLogicDto.getBranchCompanyId());
            log.info("子公司的Id:{},设置的最大未付款时间:{}", warehouseLogicDto.getBranchCompanyId(), cancelTime);
            taskExecutor.execute(new CancelNonpaymentOrderWorker(cancelTime, warehouseLogicDto.getBranchCompanyId()));
        }
        log.info("cancelNonpaymentOrderScheduler execute end.");
    }

    protected int getCancelTime(List<CompanyCancelNoPayPo> pCompanyCancelNoPayPos, String companyId) {
        if (CollectionUtils.isEmpty(pCompanyCancelNoPayPos)) {
            return DefaultNoPayEnum.CANCEL_MINUTE.getTime();
        }
        for (CompanyCancelNoPayPo companyCancelNoPayPo : pCompanyCancelNoPayPos) {
            if (companyCancelNoPayPo.getCompanyId().equals(companyId)) {
                return companyCancelNoPayPo.getCancelTime() == 0 ? DefaultNoPayEnum.CANCEL_MINUTE.getTime() : companyCancelNoPayPo.getCancelTime();
            }
        }
        return DefaultNoPayEnum.CANCEL_MINUTE.getTime();
    }

    class CancelNonpaymentOrderWorker implements Runnable {

        private int cancelTime;

        private String companyId;

        public CancelNonpaymentOrderWorker(int cancelTime, String companyId) {
            this.cancelTime = cancelTime;
            this.companyId = companyId;
        }

        @Override
        public void run() {
            Calendar submitEndTime = Calendar.getInstance();
            submitEndTime.setTime(new Date());
            // 子公司设置的取消时间
            submitEndTime.add(Calendar.MINUTE, -(cancelTime));
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("paymentState", PaymentStates.PENDING_PAYMENT);
            condition.put("submitEndTime", submitEndTime.getTime());
            condition.put("companyId", companyId);
            List<Order> nonpaymentOrders = orderService.getOrdersByState(condition);
            log.info("未付款取消订单:{}", JSON.toJSONString(nonpaymentOrders));

            if (CollectionUtils.isEmpty(nonpaymentOrders)) {
                return;
            }
            for (Order order : nonpaymentOrders) {
                updateOrderToCancel(order);
            }
        }

        public void updateOrderToCancel(Order order) {
            DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            transactionDefinition.setTimeout(300);
            transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
            boolean isRollback = false;
            try {

                order.setCancelReason(cancelTime + "分钟未付款，自动取消");
                order.setCancelStatus(CancelOrderStates.QXZ.getStateValue());
                order.setDescription(CancelOrderStates.QXZ.getDescription());
                orderService.updateOrderSelective(order);
                orderLogService.saveOrderLog(order.getId(), order.getState(), cancelTime + "分钟未付款，自动取消", "系统");

//          取消时回滚优惠券
//            String orderState = order.getOrderState();
//            if(!OrderStates.CANCELLED.equals(orderState)){
//                Response<String> revertCoupons = orderService.revertCoupons(order.getId(),order.getPriceInfo());
//                if(revertCoupons!=null){
//                	log.debug("回滚优惠券完成,order Id "+order.getId()+" revert coupons:"+revertCoupons.getResultObject());
//                }
//            }
                // order.setCancelStatus( CancelOrderStates.QXCG.getStateValue() );
            } catch (Exception ex) {
                isRollback = true;
                log.error("自动取消未付款订单异常", ex);
                return;
            } finally {
                if (isRollback) {
                    transactionManager.rollback(txStatus);
                } else {
                    log.info("update order to cancel:{}", order.getId());
                    transactionManager.commit(txStatus);
                }
            }

            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.CANCELED_ORDER);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);
        }
    }
}
