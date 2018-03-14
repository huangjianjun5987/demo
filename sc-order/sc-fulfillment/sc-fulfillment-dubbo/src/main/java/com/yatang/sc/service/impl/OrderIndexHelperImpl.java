package com.yatang.sc.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.utils.DateUtil;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.inventory.dto.AvcostConditionDto;
import com.yatang.sc.inventory.dto.AvcostResultDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryQueryDubboService;
import com.yatang.sc.order.domain.*;
import com.yatang.sc.order.domain.orderIndex.OrderDetailIndex;
import com.yatang.sc.order.domain.orderIndex.OrderItemIndex;
import com.yatang.sc.order.domain.orderIndex.PaymentGroupIndex;
import com.yatang.sc.order.domain.orderIndex.RefundIndex;
import com.yatang.sc.order.sender.OrderIndexMessage;
import com.yatang.sc.order.sender.OrderIndexMessageSender;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.payment.dto.RefundDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.service.OrderIndexHelper;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderIndexHelperImpl implements OrderIndexHelper {

    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    OrderService orderService;

    @Autowired
    OrderIndexMessageSender orderIndexMessageSender;

    @Autowired
    ProductDubboService productDubboService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RefundDubboService refundDubboService;

    @Autowired
    private WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    @Autowired
    ItemLocInventoryQueryDubboService itemLocInventoryQueryDubboService;

    @Autowired
    PaymentGroupService paymentGroupService;

    @Override
    public void sendOrderIndexMessage(String id){

        OrderDetailIndex orderDetailIndex = orderService.loadOrderDetail(id);
        if(orderDetailIndex == null){
            log.error("sendOrderIndexMessage can not find order {}",id);
            return ;
        }
        Response<BranchCompanyDto> companyRep = organizationService.querySimpleByBranchCompanyId(orderDetailIndex.getBranchCompanyId());
        if(companyRep != null && companyRep.isSuccess() && companyRep.getResultObject()!=null){
            BranchCompanyDto branchCompanyDto = companyRep.getResultObject();
            orderDetailIndex.setBranchCompanyName(branchCompanyDto.getName());
            orderDetailIndex.setOperationCenterId(branchCompanyDto.getOperationCenterId());
        }

        Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(orderDetailIndex.getBranchCompanyArehouseCode());
        if (logicWarehouseDtoResponse != null && logicWarehouseDtoResponse.isSuccess() && logicWarehouseDtoResponse.getResultObject() !=null) {
            orderDetailIndex.setBranchCompanyArehouseName(logicWarehouseDtoResponse.getResultObject().getWarehouseName());
        }

        Response<StoreDto> storeDtoResponse = organizationService.queryStoreById(orderDetailIndex.getFranchiseeStoreId());
        if(storeDtoResponse != null && storeDtoResponse.isSuccess() && storeDtoResponse.getResultObject() != null){
            orderDetailIndex.setFranchiseeStoreName(storeDtoResponse.getResultObject().getName());
        }

        Response<FranchiseeDto> franchiseeDto = organizationService.querySimpleById(orderDetailIndex.getFranchiseeId());
        if (franchiseeDto != null && franchiseeDto.isSuccess() && franchiseeDto.getResultObject() != null) {
            orderDetailIndex.setFranchiseeName(franchiseeDto.getResultObject().getName());
        }

        //付款记录
        encapPaymentGroup(orderDetailIndex);
        //退款记录
        encapRefund(orderDetailIndex);
        //设置商品相关属性
        encapOrderItemPo(orderDetailIndex);

        OrderIndexMessage indexMsg = BeanConvertUtils.convert(orderDetailIndex,OrderIndexMessage.class);
//        log.info("OrderIndexMessage id:{}", orderDetailIndex.getId());
        orderIndexMessageSender.sendOrderIndexMsg(indexMsg);
    }


    private void encapPaymentGroup(OrderDetailIndex orderDetailIndex) {
        List<PaymentGroupIndex> paymentGroupIndexList = new ArrayList<PaymentGroupIndex>();
        List<PaymentGroup> paymentGroupList = paymentGroupService.getPaymentGroupForOrderId(orderDetailIndex.getId());
        if(!CollectionUtils.isEmpty(paymentGroupList)){
            for(PaymentGroup paymentGroup:paymentGroupList){
                paymentGroup.setPayDate(paymentGroup.getPayDate()!=null? DateUtil.parseDateYMDhms((DateUtil.formatDateYMDhms(paymentGroup.getPayDate()))):null);
                PaymentGroupIndex paymentGroupIndex = BeanConvertUtils.convert(paymentGroup,PaymentGroupIndex.class);
                if(orderDetailIndex.getOrderType().equals(OrderTypes.DIRECT_STORE)){
                    paymentGroupIndex.setPayDate(orderDetailIndex.getSubmitTime());
                }else{
                    paymentGroupIndex.setPayDate(paymentGroup.getPayDate()==null?null: DateUtil.formatDateYMDhms(paymentGroup.getPayDate()));
                }

                paymentGroupIndexList.add(paymentGroupIndex);
            }
        }
        orderDetailIndex.setPaymentGroupList(paymentGroupIndexList);

    }

    private void encapRefund(OrderDetailIndex orderDetailIndex) {
        Response<List<RefundDto>> refundRep = refundDubboService.getRefundByOrderId(orderDetailIndex.getId());
        if(refundRep !=null && refundRep.isSuccess() && !CollectionUtils.isEmpty(refundRep.getResultObject())){
            List<RefundIndex> refundIndexList = new ArrayList<RefundIndex>();
            for (RefundDto refundDto:refundRep.getResultObject()){
                RefundIndex refundIndex = BeanConvertUtils.convert(refundDto,RefundIndex.class);
                refundIndex.setRefundTime(refundDto.getRefundTime()!=null?DateUtil.formatDateYMDhms(refundDto.getRefundTime()):null);
                refundIndexList.add(refundIndex);
            }
            orderDetailIndex.setRefundList(refundIndexList);
        }
    }

    private void encapOrderItemPo(OrderDetailIndex orderDetailIndex) {
        Map<String,String> avCostMap = new HashMap<String,String>();
        List<AvcostConditionDto> avcostConditionDtos = new ArrayList<AvcostConditionDto>();
        for(OrderItemIndex orderItemIndex : orderDetailIndex.getOrderItemPoList()){
            AvcostConditionDto avcostConditionDto = new AvcostConditionDto();
            avcostConditionDto.setItemId(orderItemIndex.getProductId());
            avcostConditionDto.setLoc(orderDetailIndex.getBranchCompanyArehouseCode());
            avcostConditionDtos.add(avcostConditionDto);
        }
        DecimalFormat df = new DecimalFormat("0.00");
        //批量获取商品移动加权平均成本
        Response<List<AvcostResultDto>> reponse = itemLocInventoryQueryDubboService.queryBatchItemLocInventory(avcostConditionDtos);
        if(reponse != null && reponse.isSuccess() && !CollectionUtils.isEmpty(reponse.getResultObject())){
            for (AvcostResultDto avcostResultDto : reponse.getResultObject()){
                //同一个订单上逻辑仓库编码相同，直接使用商品id作为key
                avCostMap.put(avcostResultDto.getItemId(),avcostResultDto.getAvCost()!=null?df.format(avcostResultDto.getAvCost()):null);
            }
        }


        for (OrderItemIndex orderItemIndex:orderDetailIndex.getOrderItemPoList()){
            Response<ProductDto> productDtoResponse = productDubboService.queryById(orderItemIndex.getProductId());
            if(productDtoResponse.isSuccess() && productDtoResponse.getResultObject() !=null){
                ProductDto productDto = productDtoResponse.getResultObject();
                orderItemIndex.setInternationalCode(productDto.getInternationalCodes().get(0).getInternationalCode());
                orderItemIndex.setFirstLevelCategoryName(productDto.getFirstLevelCategoryName());
                orderItemIndex.setSecondLevelCategoryName(productDto.getSecondLevelCategoryName());
                orderItemIndex.setThirdLevelCategoryName(productDto.getThirdLevelCategoryName());
                orderItemIndex.setOrderId(orderDetailIndex.getId());
                orderItemIndex.setProductName(productDto.getSaleName());
                orderItemIndex.setProductCode(productDto.getProductCode());
                orderItemIndex.setGroupsCode(productDto.getFirstLevelCategoryId());
                orderItemIndex.setGroupsName(productDto.getFirstLevelCategoryName());
                orderItemIndex.setDeptCode(productDto.getSecondLevelCategoryId());
                orderItemIndex.setDeptName(productDto.getSecondLevelCategoryName());
                orderItemIndex.setSaleTax(productDto.getTaxRate()==null?"":productDto.getTaxRate().toString());
                //商品移动加权平均成本
                String avCost = avCostMap.get(orderItemIndex.getProductId());
                if(!StringUtils.isBlank(avCost)){
                    orderItemIndex.setAvCost(Double.valueOf(avCost));
                }else{
                    orderItemIndex.setAvCost(0D);
                }
                //计算签收金额、签收差额、成本金额
                calcuSomeAmount(orderItemIndex);
            }
        }
    }

    private void calcuSomeAmount(OrderItemIndex orderItemIndex) {
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal quantity = new BigDecimal(orderItemIndex.getQuantity()==null?0:orderItemIndex.getQuantity());
        BigDecimal orderDiscountShare = new BigDecimal(orderItemIndex.getOrderDiscountShare()==null?0:orderItemIndex.getOrderDiscountShare());
        BigDecimal amount = new BigDecimal(orderItemIndex.getAmount()==null?0:orderItemIndex.getAmount());
        BigDecimal qty = new BigDecimal(orderItemIndex.getCompletedQuantity()==null?0:orderItemIndex.getCompletedQuantity());
        if(quantity.intValue() != 0){
            //优惠后的销售价格=(商品价格-优惠的金额)/数量
            BigDecimal sp = amount.subtract(orderDiscountShare).divide(quantity,2, BigDecimal.ROUND_HALF_UP);
            //签收金额=签收数量*优惠后的销售价格
            orderItemIndex.setCompletedAmount(df.format(qty.multiply(sp).doubleValue()));
            BigDecimal mulNumber = quantity.subtract(qty);
            //签收差额=商品价格-签收金额（签收差数*优惠后的销售价格）
            orderItemIndex.setCompletedMulAmount(df.format(mulNumber.multiply(sp).doubleValue()));
            //成本金额=移动加权平均价*数量
            orderItemIndex.setCost(Double.valueOf(df.format(quantity.multiply(sp))));
        }

    }

}
