package com.yatang.sc.product;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.datatable.TableDataResult;
import com.yatang.sc.common.PageResult;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.utils.BigDemicalUtil;
import com.yatang.sc.common.utils.DateUtil;
import com.yatang.sc.common.utils.MyBeanUtils;
import com.yatang.sc.dto.*;
import com.yatang.sc.flow.WebOrderServiceFlow;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.order.domain.*;
import com.yatang.sc.order.dubboservice.WebOrderDubboService;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.*;
import com.yatang.sc.order.split.SplitOrderItemDto;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.split.SplitOrderSubGroupDto;
import com.yatang.sc.order.split.enums.SplitOrderFromType;
import com.yatang.sc.order.split.enums.SubOrderType;
import com.yatang.sc.order.states.*;

import com.yatang.sc.payment.dto.PaymentRecordDto;
import com.yatang.sc.payment.dto.RefundDto;
import com.yatang.sc.payment.dubbo.service.PaymentDubboService;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundStatus;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.service.OrganizationHelper;
import com.yatang.sc.service.ProductHelper;
import com.yatang.sc.purchase.dto.PrintOrderDto;
import com.yatang.sc.purchase.dto.PrintOrderProductDto;
import com.yatang.sc.purchase.dto.WebQueryOrderConditionDto;
import com.yatang.sc.purchase.dto.WebQueryOrderDto;
import com.yatang.sc.purchase.dto.WebQueryPaymentGroupDto;
import com.yatang.sc.vo.UpdateOrderVO;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.DataCenterScDubboService;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.dto.OrderItemDto;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.dto.OrderListDto;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.dto.QueryOrderItemDto;
import com.yatang.xc.dc.biz.facade.dubboservice.sc.dto.QueryOrderListDto;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dto.FranchiseeDto;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.InternationalCodeDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
@Service("webOrderDubboService")
public class WebOrderDubboServiceImpl implements WebOrderDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${queryOrder.way}")
    private int way;
    @Value("${queryOrder.indexPageSize}")
    private int indexPageSize;
    @Value("${queryOrder.indexSettlementPageSize}")
    private int indexSettlementPageSize;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Autowired
    private OrderPriceService orderPriceService;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Autowired
    private CommerceItemService commerceItemService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private RefundDubboService mRefundDubboService;

    @Autowired
    private OrganizationHelper organizationHelper;

    @Autowired
    private OrderMessageSender orderMessageSender;

    @Autowired
    private PriceAdjustmentService priceAdjustmentService;

    @Autowired
    private OrderPriceAdjService orderPriceAdjService;

    @Autowired
    private WebOrderServiceFlow webOrderServiceFlow;

    @Autowired
    private ItemLocInventoryDubboService itemLocInventoryDubboService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private PaymentDubboService paymentDubboService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    DataCenterScDubboService dataCenterScDubboService;

    @Autowired
    private ReturnRequestService returnRequestService;

    @Autowired
    private ProviderShippingGroupService providerShippingGroupService;

    @Override
    public Response<PageResult<WebQueryOrderDto>> queryOrder(WebQueryOrderConditionDto condition) {

        log.info("method:queryOrder,param:{}", JSONObject.toJSONString(condition));
        Response<PageResult<WebQueryOrderDto>> response = new Response<PageResult<WebQueryOrderDto>>();
        try {
            PageResult<WebQueryOrderDto> pageResult = getWebQueryOrderDtoPageResult(condition);
            pageResult.setPageSize(condition.getPageSize());
            pageResult.setPageNum(condition.getPage());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(pageResult);

        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(e.getMessage(),e);
        }
        return response;
    }


    public PageResult<WebQueryOrderDto> getWebQueryOrderDtoPageResult(WebQueryOrderConditionDto condition)throws Exception {

        if(way==1){
            log.info("getWebQueryOrderDtoPageResult from main data.");
            //调用主数据索引服务
            return getWebQueryOrderDtoPageResultFromMainData(condition);
        }
        log.info("getWebQueryOrderDtoPageResult from locality.");
        return getWebQueryOrderDtoPageResultFromLocal(condition);
    }

    private PageResult<WebQueryOrderDto> getWebQueryOrderDtoPageResultFromLocal(WebQueryOrderConditionDto condition) throws Exception{
        PageResult<WebQueryOrderDto> pageResult = new PageResult<WebQueryOrderDto>();
        Map<String,Object> map = MyBeanUtils.beanToMap(condition);
        map.put("start", (condition.getPage() - 1) * condition.getPageSize());
        map.put("end", condition.getPageSize());
        long total = orderService.getOrderPageListCount(map);
        List<Order> orderList = orderService.getOrderPageList(map);
        List<WebQueryOrderDto> list = BeanConvertUtils.convertList(orderList, WebQueryOrderDto.class);
        for (WebQueryOrderDto webQueryOrderDto : list) {
            BranchCompanyDto branchCompany = organizationHelper.findBranchCompanyById(webQueryOrderDto.getBranchCompanyId());
            if (branchCompany != null) {
                webQueryOrderDto.setBranchCompanyName(branchCompany.getName());
            }
            webQueryOrderDto.setFranchiseeNo(webQueryOrderDto.getFranchiseeId());
            FranchiseeDto franchiseeDto = organizationHelper.findFranchiseeById(webQueryOrderDto.getFranchiseeId());
            if (franchiseeDto != null) {
                webQueryOrderDto.setFranchiseeName(franchiseeDto.getName());
            }
            StoreDto storeDto = organizationHelper.findStoreById(webQueryOrderDto.getFranchiseeStoreId());
            if(storeDto != null){
                webQueryOrderDto.setFranchiseeStoreName(storeDto.getName());
            }
            if (webQueryOrderDto.getSubmitTime() != null) {
                webQueryOrderDto.setSubmitTime(DateUtil.parseDateYMDhms(DateUtil.formatDateYMDhms(webQueryOrderDto.getSubmitTime())));
            }
        }
        pageResult.setTotal(total);
        pageResult.setData(list);
        return pageResult;
    }

    private PageResult<WebQueryOrderDto> getWebQueryOrderDtoPageResultFromMainData(WebQueryOrderConditionDto condition) throws Exception{
        PageResult<WebQueryOrderDto> pageResult = new PageResult<WebQueryOrderDto>();
        QueryOrderListDto queryDto = BeanConvertUtils.convert(condition,QueryOrderListDto.class);
        queryDto.setPageNum(condition.getPage());
        log.info("getWebQueryOrderDtoPageResult from main data condition:{}", JSON.toJSONString(queryDto));
        Response<TableDataResult<OrderListDto>> resultResponse = dataCenterScDubboService.queryOrderList(queryDto);
        if(resultResponse !=null && resultResponse.isSuccess() && !CollectionUtils.isEmpty(resultResponse.getResultObject().getData())){
            List<WebQueryOrderDto> webQueryOrderDtoList = BeanConvertUtils.convertList(resultResponse.getResultObject().getData(),WebQueryOrderDto.class);
            pageResult.setData(webQueryOrderDtoList);
            pageResult.setTotal(resultResponse.getResultObject().getRecordsTotal().longValue());
        }
        return pageResult;
    }

    @Override
    public Response<Boolean> deliverGoods(DeliverInfoDto deliverInfoDto, String userId) {
        log.debug("method:deliverGoods,userId:{},param:{}",userId , JSONObject.toJSONString(deliverInfoDto));
        Response<Boolean> response = new Response<Boolean>();
        try {
            OrderLog orderLog = new OrderLog();
            Order order = orderService.selectByPrimaryKey(deliverInfoDto.getOrderId());
            if (ShippingStates.PENDING_DELIVERY.equals(order.getShippingState())) {
                ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
                this.updateShippingGroup(shippingGroup, deliverInfoDto);
                this.updateCommerceItems(deliverInfoDto);
                order.setState(OrderTotalStates.PENDING_RECEIVING.getStateValue());
                order.setShippingState(ShippingStates.PENDING_RECEIVE);
                orderService.update(order);
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
                response.setResultObject(true);
                orderLog.setDescription("订单发货成功");
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setCode(CommonsEnum.RESPONSE_10022.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10022.getName());
                response.setSuccess(false);
                response.setResultObject(false);
                orderLog.setDescription("订单发货失败，订单状态错误");
            }

            orderLog.setOrderId(order.getId());
            orderLog.setOrderState(order.getState());
            orderLog.setCreateDate(new Date());
            orderLog.setOperater(userId);

            orderLogService.save(orderLog);

        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setResultObject(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    private void updateCommerceItems(DeliverInfoDto deliverInfoDto) {
        List<DeliveryGoodsDto> deliveryGoodsDtos = deliverInfoDto.getDeliveryGoodsDtos();
        for (DeliveryGoodsDto dto : deliveryGoodsDtos) {
            CommerceItem commerceItem = new CommerceItem();
            commerceItem.setId(Long.valueOf(dto.getId()));
            commerceItem.setShippedQuantity(dto.getShippedQuantity());
            commerceItemService.updateCommerceItem(commerceItem);
        }
    }

    private void updateShippingGroup(ShippingGroup shippingGroup, DeliverInfoDto deliverInfoDto) {
        shippingGroup.setShippingMethod(deliverInfoDto.getShippingMethod());
        shippingGroup.setShippingNo(deliverInfoDto.getShippingNo());
        shippingGroup.setDeliveryer(deliverInfoDto.getDeliveryer());
        shippingGroup.setCellphone(deliverInfoDto.getCellphone());
        shippingGroup.setShipOnDate(deliverInfoDto.getDeliveryDate());
        shippingGroup.setEstimatedArrivalDate(deliverInfoDto.getEstimatedArrivedDate());
        shippingGroupService.update(shippingGroup);
    }

    @Override
    public Response<Boolean> orderDescription(String orderId, String description, String userId) {
        log.debug("method:orderDescription,param:userId:{},orderId{},description{}",userId, orderId,description);
        Response<Boolean> response = new Response<Boolean>();
        try {
            OrderLog orderLog = new OrderLog();
            Order order = orderService.selectByPrimaryKey(orderId);
            if (order != null) {
                order.setDescription(description);
                orderService.update(order);
                orderLog.setDescription("web端订单备注成功");
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setResultObject(true);
            } else {
                orderLog.setDescription("web端订单备注失败，订单id不存在");
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setSuccess(false);
                response.setResultObject(false);
            }
            orderLog.setOrderId(order.getId());
            orderLog.setOrderState(order.getState());
            orderLog.setCreateDate(new Date());
            orderLog.setOperater(userId);
            orderLogService.save(orderLog);

        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setResultObject(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<PrintOrderDto> getPrintInfo(String orderId) {
        log.debug("method:getPrintInfo,orderId{}" ,orderId);
        Response<PrintOrderDto> response = new Response<PrintOrderDto>();
        try {
            Order order = orderService.selectByPrimaryKey(orderId);
            PrintOrderDto printOrderDto = BeanConvertUtils.convert(order, PrintOrderDto.class);
            ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());

            printOrderDto.setDetailAddress(shippingGroup.getDetailAddress());
            printOrderDto.setCellphone(shippingGroup.getCellphone());
            printOrderDto.setConsigneeName(shippingGroup.getConsigneeName());
            printOrderDto.setShippingMethod(shippingGroup.getShippingMethod());
            printOrderDto.setShippingNo(shippingGroup.getShippingNo());

            OrderPrice orderPrice = orderPriceService.getOrderPriceForId(order.getPriceInfo());
            printOrderDto.setAmount(orderPrice.getAmount());
            printOrderDto.setShipping(orderPrice.getShipping());
            printOrderDto.setDiscountAmount(orderPrice.getDiscountAmount());
            printOrderDto.setTotal(orderPrice.getTotal());

            List<PaymentGroup> paymentGroups = paymentGroupService.getPaymentGroupForOrderId(order.getId());
            if (CollectionUtils.isNotEmpty(paymentGroups)) {
                printOrderDto.setTransNum(paymentGroups.get(0).getTransNum());

                SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                printOrderDto.setPayDate(paymentGroups.get(0).getPayDate() != null ? sf.format(paymentGroups.get(0).getPayDate()) : "");
                printOrderDto.setPaymentMethod(paymentGroups.get(0).getPaymentMethod());
            }

            List<PrintOrderProductDto> printOrderProductDtoList = new ArrayList<PrintOrderProductDto>();
            List<OrderItems> orderItemsList = orderItemsService.getOrderItemsForOrderId(order.getId());
            for (OrderItems orderItems : orderItemsList) {
                CommerceItem commerceItem = commerceItemService.getCommerceItemForId(orderItems.getCommerceItemId());
                PrintOrderProductDto printOrderProductDto = BeanConvertUtils.convert(commerceItem, PrintOrderProductDto.class);
                ItemPrice itemPrice = itemPriceService.getItemPriceForId(commerceItem.getItemPriceInfo());
                printOrderProductDto.setAmount(itemPrice.getAmount());
                printOrderProductDto.setSale_price(itemPrice.getSalePrice());
                printOrderProductDtoList.add(printOrderProductDto);
            }
            printOrderDto.setNumberUrl("");
            printOrderDto.setProductlist(printOrderProductDtoList);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(printOrderDto);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }

        return response;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response<Boolean> batchApproval(String[] ids, String userId) {
        log.debug("method:batchApproval,userId{}" ,userId);
        Response<Boolean> response = new Response<Boolean>();
        Date nowTime = new Date();
        try {
            for (String id : ids) {
                if (id != null && !id.equals("")) {
                    approvalSingalOrder(id, userId);
                }
            }
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    public void approvalSingalOrder(String id, String userId) {
        Order order = orderService.selectByPrimaryKey(id);
        UpdateOrderVO updateOrderVO = new UpdateOrderVO();
        updateOrderVO.setOrder(order);
        updateOrderVO.setPreOrderState(order.getState());

        if (order.getOrderState().equals(OrderStates.PENDING_APPROVE) || order.getOrderState().equals(OrderStates.PENDING_MANUAL_APPROVE)) {
            DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);

            try {
                order.setOrderState(OrderStates.APPROVED);
                updateOrderVO.setDescription("web人工审核订单，id：" + order.getId());
                updateOrderVO.setOperator(userId);
                orderService.updateAndSaveLog(updateOrderVO);
            } catch (Exception ex) {
                transactionManager.rollback(txStatus);
                log.error("审核异常", ex);
                return;
            }
            transactionManager.commit(txStatus);
            OrderMessage approvedMsg = new OrderMessage();
            approvedMsg.setMssageType(OrderMessageType.ApprovedOrder_Manual);
            approvedMsg.setOrderId(order.getId());
            orderMessageSender.sendMsg(approvedMsg);
        }
    }

    @Override
    public Response<Boolean> approvalOrder(String orderId, String userId) {
        log.debug("method:approvalOrder,userId{},orderId{}",userId, orderId);
        Response<Boolean> response = new Response<Boolean>();
        try {
            approvalSingalOrder(orderId, userId);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Boolean> updateShippingState(String orderId) {
        log.debug("method:updateShippingState,orderId{}" , orderId);
        Response<Boolean> response = new Response<Boolean>();
        Order order = new Order();
        order.setId(orderId);
        order.setShippingState(ShippingStates.COMPLETED);
        try {
            orderService.update(order);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    @Override
    public Response<OrderManageDetailDto> queryOrderDeatil(String id) {
        log.debug("method:queryOrderDeatil,id{}" , id);
        Response<OrderManageDetailDto> response = new Response<OrderManageDetailDto>();
        try {
            OrderManageDetailDto orderManageDetailDto = new OrderManageDetailDto();
            Order order = orderService.selectByPrimaryKey(id);
            if (order == null) {
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }

            if (canSplit(order)) {
                if (ShippingStates.NO_INVENTORY.equalsIgnoreCase(order.getShippingState())) {
                    orderManageDetailDto.setCanSplitByInventory(true);
                }
                orderManageDetailDto.setCanSplitManual(true);
            }
            //配送
            String shippingGroupId = order.getShippingGroup();
            ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(shippingGroupId);
            //价格
            Long priceInfoId = order.getPriceInfo();
            OrderPrice orderPrice = orderPriceService.selectByPrimaryKey(priceInfoId);
            List<PriceAdjustment> priceAdjustmentList= priceAdjustmentService.queryByOrderPriceId(orderPrice.getId());
            //活动
            double promoPrice = 0.0;
            if (!CollectionUtils.isEmpty(priceAdjustmentList)) {
                for (PriceAdjustment promoRecordsPo : priceAdjustmentList) {
                    if (promoRecordsPo != null && promoRecordsPo.getPricingModelType() != null && promoRecordsPo.getPricingModelType() == 0) {
                        promoPrice += promoRecordsPo.getTotalAdjustment().doubleValue();
                    }
                }
            }
            //商品列表
            if (id != null) {
                List<ItemInventoryDto> itemInventoryDtos = new ArrayList<ItemInventoryDto>();
                ItemInventoryDto itemInventoryDto;
                List<OrderItems> itemsIds = orderItemsService.getOrderItemsForOrderId(id);
                //这是找到商品的id了 然后通过商品的id找到商品
                List<OMSCommerceItemDto> items = new ArrayList<OMSCommerceItemDto>();
                Map<Long, CommerceItem> commerceItemMap = new HashMap<Long, CommerceItem>();
                for (OrderItems itemsId : itemsIds) {
                    CommerceItem commerceItem = commerceItemService.getCommerceItemForId(itemsId.getCommerceItemId());
                    if (commerceItem.getQuantity() == 0) {
                        continue;
                    }
                    commerceItemMap.put(itemsId.getCommerceItemId(), commerceItem);
                    itemInventoryDto = new ItemInventoryDto();
                    itemInventoryDto.setProductId(commerceItem.getProductId());
                    itemInventoryDto.setLoc(order.getBranchCompanyArehouse());
                    itemInventoryDtos.add(itemInventoryDto);
                }
                Response<Map<String, Long>> inventoryRes = itemLocInventoryDubboService.queryAllAvailableInventory(itemInventoryDtos);
                if (inventoryRes == null || !inventoryRes.isSuccess()) {
                    throw new Exception("未查询到库存信息");
                }
                Map<String, Long> itemInventoryMap = inventoryRes.getResultObject();

                for (Map.Entry<Long, CommerceItem> commerceItemEntry : commerceItemMap.entrySet()) {
                    //通过商品的id 找到商品
                    CommerceItem commerceItem = commerceItemEntry.getValue();
                    ItemPrice itemPrice = itemPriceService.getItemPriceForId(commerceItem.getItemPriceInfo());
                    commerceItem.setItemPrice(itemPrice);
                    OMSCommerceItemDto commerceItemDto = BeanConvertUtils.convert(commerceItem, OMSCommerceItemDto.class);
                    ProductIndexDto productDto = productHelper.findProductIndexById(commerceItem.getProductId());
                    if(productDto!=null){
                        commerceItemDto.setProductImg(productDto.getThumbnailImage());
                        commerceItemDto.setProductName(productDto.getSaleName());
                        commerceItemDto.setProductCode(productDto.getProductCode());
                        commerceItemDto.setSecondLevelCategoryName(productDto.getSecondLevelCategoryName());
                        commerceItemDto.setThirdLevelCategoryName(productDto.getThirdLevelCategoryName());
                        List<InternationalCodeDto> internationalCodes = BeanConvertUtils.convertList(productDto.getInternationalCodes(),InternationalCodeDto.class);
                        commerceItemDto.setInternationalCodes(internationalCodes);
                        if (itemInventoryMap.get(commerceItem.getProductId()) != null) {
                            commerceItemDto.setAvailableStock(itemInventoryMap.get(commerceItem.getProductId()).longValue());
                        }
                        commerceItemDto.setAbnormalGoods(commerceItem.getAbnormalGoods());
                        commerceItemDto.setAbnormalResonse(commerceItem.getAbnormalResonse());
                        items.add(commerceItemDto);
                    }else{
                        log.info("queryOrderDeatil,productId{}",commerceItem.getProductId());
                    }

                }

                orderManageDetailDto.setItems(items);
                int count = 0;
                if (items != null) {
                    for (OMSCommerceItemDto commerceItemDto:items){
                        count+=commerceItemDto.getQuantity();
                    }
                    orderManageDetailDto.setCountOfItem(count);
                }
            }
            BranchCompanyDto branchCompany = organizationHelper.findBranchCompanyById(order.getBranchCompanyId());
            if (branchCompany != null) {
                orderManageDetailDto.setBranchCompanyName(branchCompany.getName());
            }
            FranchiseeDto franchiseeDto = organizationHelper.findFranchiseeById(order.getFranchiseeId());
            if (franchiseeDto != null) {
                orderManageDetailDto.setFranchiseeName(franchiseeDto.getName());
            } else {
                log.warn("加盟商信息未查询到:{}", order.getFranchiseeId());
                orderManageDetailDto.setFranchiseeName(order.getFranchiseeId());
            }

            StoreDto storeDto = organizationHelper.findStoreById(order.getFranchiseeStoreId());
            if (storeDto != null) {
                orderManageDetailDto.setFranchiseeStoreName(storeDto.getName());
            }
            orderManageDetailDto.setId(order.getId());
            orderManageDetailDto.setCreatedByOrderId(order.getCreatedByOrderId());
            orderManageDetailDto.setOrderType(order.getOrderType());
            orderManageDetailDto.setState(order.getState());
            orderManageDetailDto.setPaymentState(order.getPaymentState());
            orderManageDetailDto.setShippingState(order.getShippingState());
            orderManageDetailDto.setOrderState(order.getOrderState());
            orderManageDetailDto.setStateDetail(order.getStateDetail());
            orderManageDetailDto.setBranchCompanyId(order.getBranchCompanyId());
            orderManageDetailDto.setProfileId(order.getProfileId());
            orderManageDetailDto.setDescription(order.getDescription());
            orderManageDetailDto.setCreationTime(order.getSubmitTime());
            //添加取消原因字段 yinyuxin
            orderManageDetailDto.setCancelReason(order.getCancelReason());
            orderManageDetailDto.setProvince(shippingGroup.getProvince());
            orderManageDetailDto.setCity(shippingGroup.getCity());
            orderManageDetailDto.setDistrict(shippingGroup.getDistrict());
            orderManageDetailDto.setDetailAddress(shippingGroup.getDetailAddress());
            orderManageDetailDto.setPostcode(shippingGroup.getPostcode());
            orderManageDetailDto.setConsigneeName(shippingGroup.getConsigneeName());
            orderManageDetailDto.setCellphone(shippingGroup.getCellphone());
            orderManageDetailDto.setSingedCertImg(shippingGroup.getSingedCertImg());
            orderManageDetailDto.setRemarks(shippingGroup.getRemarks());

            if(ShippingModes.UNIFIED_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
                orderManageDetailDto.setShippingModes(ShippingModes.UNIFIED_SHIPPING_MODE.getDesc());
                orderManageDetailDto.setSpName("雅堂");
            }else if(ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){
                ProviderShippingGroup providerShippingGroup = providerShippingGroupService.selectByShippingGroupId(shippingGroupId);
                orderManageDetailDto.setSpName(providerShippingGroup.getSpName());
                orderManageDetailDto.setShippingModes(ShippingModes.PROVIDER_SHIPPING_MODE.getDesc());
            }

            orderManageDetailDto.setRawSubtotal(orderPrice.getRawSubtotal());
            orderManageDetailDto.setAmount(orderPrice.getAmount());
            orderManageDetailDto.setTotal(orderPrice.getTotal());
            orderManageDetailDto.setFranchiseeId(order.getFranchiseeId());
            orderManageDetailDto.setFranchiseeStoreId(order.getFranchiseeStoreId());
            orderManageDetailDto.setBranchCompanyArehouse(order.getBranchCompanyArehouse());
            orderManageDetailDto.setThirdPartOrderNo(order.getThirdPartOrderNo());
            orderManageDetailDto.setCouponDiscountAmount(orderPrice.getCouponDiscountAmount());
            orderManageDetailDto.setUserDiscountAmount(orderPrice.getUserDiscountAmount());
            orderManageDetailDto.setDiscount(promoPrice);
            orderManageDetailDto.setShipping(orderPrice.getShipping());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(orderManageDetailDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            //检查是否有有效的退货单存在
            Integer receiptCount = returnRequestService.getReturnReceiptCountByOrderId(id);
            orderManageDetailDto.setReturnOrder(1);
            //跟产品确认只有待收货或已签收才进行退换货,该订单已经存在有效的退货单或者为虚拟商品不能进行退换货
            boolean status = receiptCount > 0||"XNSP".equals(orderManageDetailDto.getOrderType())||"DSHYQS".indexOf(orderManageDetailDto.getShippingState())==-1;
            if (status) {
                orderManageDetailDto.setReturnOrder(0);
            }
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    private boolean canSplit(Order order) {
        if (!OrderFrom.SHIPPING_MODE_SPLIT.getValue().equals(order.getFrom()) && StringUtils.isNotEmpty(order.getCreatedByOrderId())) {
            return false;
        }
        if (!OrderStates.APPROVED.equalsIgnoreCase(order.getOrderState())) {
            return false;
        }
        if (!PaymentStates.DEBITED.equals(order.getPaymentState()) && !PaymentStates.GSN.equals(order.getPaymentState())) {
            return false;
        }
        if (
            //OrderTypes.ONLINE_RETAILERS_SALE.equalsIgnoreCase(order.getOrderType())||
                OrderTypes.ELECTRONIC_ORDER.equalsIgnoreCase(order.getOrderType())) {
            return false;
        }
        if (ShippingStates.NO_INVENTORY.equalsIgnoreCase(order.getShippingState())) {
            return true;
        }
        if (ShippingStates.PENDING_TRANSFER.equalsIgnoreCase(order.getShippingState()) && !order.getInteractivePendingRes()//物流：未传送
                && OrderStates.APPROVED.equalsIgnoreCase(order.getOrderState()) //订单：已审核
                && (PaymentStates.DEBITED.equalsIgnoreCase(order.getPaymentState()) || PaymentStates.GSN.equalsIgnoreCase(order.getPaymentState()))) {//支付：已支付
            return true;
        }
        return false;
    }
    public Response<WebQueryPaymentInfoDto> getOrderPaymentInfo(String orderId) {
        log.debug("method:getOrderPaymentInfo,orderId{}" , orderId);
        Response<WebQueryPaymentInfoDto> response = new Response<WebQueryPaymentInfoDto>();
        try {
            Order order = orderService.selectByPrimaryKey(orderId);
            if (order == null) {
                response.setSuccess(false);
                response.setErrorMessage("订单不存在");
                return response;
            }
            Double totalAmount = 0D;
            Double totalPaidAmount = 0D;
            Double totalRefundAmount = 0D;

            WebQueryPaymentInfoDto webQueryPaymentInfoDto = new WebQueryPaymentInfoDto();
            webQueryPaymentInfoDto.setTotalAmount(BigDemicalUtil.round(totalAmount, 2));
            webQueryPaymentInfoDto.setTotalPaidAmount(BigDemicalUtil.round(totalPaidAmount, 2));
            webQueryPaymentInfoDto.setTotalRefundedAmount(BigDemicalUtil.round(totalRefundAmount, 2));
            response.setResultObject(webQueryPaymentInfoDto);
            List<WebQueryPaymentGroupDto> webQueryPaymentGroupDtos = new ArrayList<WebQueryPaymentGroupDto>();
            webQueryPaymentInfoDto.setPaymentGroups(webQueryPaymentGroupDtos);
            response.setSuccess(true);

            //订单上的支付信息
            OrderPrice orderPrice = orderPriceService.getOrderPriceForId(order.getPriceInfo());
            if (orderPrice == null) {
                response.setSuccess(false);
                response.setErrorMessage("订单价格信息不存在");
                return response;
            }
            totalAmount = orderPrice.getTotal();//是否需要去最终价格

            //支付信息
            List<PaymentGroup> paymentGroups = paymentGroupService.getPaymentGroupForOrderId(order.getId());
            if (CollectionUtils.isEmpty(paymentGroups)) {
                response.setSuccess(false);
                response.setErrorMessage("订单支付信息不存在");
                return response;
            }

            for (PaymentGroup paymentGroup : paymentGroups) {
                if (PaymentGroupStates.INITIAL.getStateValue().equals(paymentGroup.getState())) {
                    continue;
                }
                if (PaymentGroupStates.SETTLED.getStateValue().equals(paymentGroup.getState())) {
                    totalPaidAmount += paymentGroup.getAmountDebited();
                }
                webQueryPaymentGroupDtos.add(convertPaymentGroupToWebQueryPaymentGroupDto(paymentGroup));
            }

            //优惠券
            List<OrderPriceAdj> orderPriceAdjs = orderPriceAdjService.selectByOrderPriceId(orderPrice.getId());
            PriceAdjustment priceAdjustment;
            for (OrderPriceAdj orderPriceAdj : orderPriceAdjs) {
                priceAdjustment = priceAdjustmentService.selectByPrimaryKey(orderPriceAdj.getAdjustmentId());
                if (priceAdjustment != null && priceAdjustment.getPricingModelType() != null && priceAdjustment.getPricingModelType() == 1) {
                    webQueryPaymentGroupDtos.add(convertAjustmentToWebQueryPaymentGroupDto(priceAdjustment, order));
                }
            }

            //退款信息
            Response<List<RefundDto>> refundDtos = mRefundDubboService.getRefundByOrderId(orderId);
            if (CollectionUtils.isNotEmpty(refundDtos.getResultObject())) {
                for (RefundDto refundDto : refundDtos.getResultObject()) {
                    webQueryPaymentGroupDtos.add(convertRefundDtoToWebQueryPaymentGroupDto(refundDto));
                    if (refundDto.getStatus().equals(RefundStatus.REFUNDED) || refundDto.getStatus().equals(RefundStatus.REFUNDED_CONFIRM)) {
                        totalRefundAmount += refundDto.getRefundAmount();
                    }
                }
            }

            webQueryPaymentInfoDto.setTotalAmount(BigDemicalUtil.round(totalAmount,2));
            webQueryPaymentInfoDto.setTotalPaidAmount(BigDemicalUtil.round(totalPaidAmount,2));
            webQueryPaymentInfoDto.setTotalRefundedAmount(BigDemicalUtil.round(totalRefundAmount,2));
        } catch (Exception ex) {
            log.error("获取支付信息异常", ex);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
        }
        return response;
    }

    private WebQueryPaymentGroupDto convertPaymentGroupToWebQueryPaymentGroupDto(PaymentGroup paymentGroup) {
        WebQueryPaymentGroupDto webQueryPaymentGroupDto = new WebQueryPaymentGroupDto();

        if (StringUtils.isEmpty(paymentGroup.getPayAccount()) && StringUtils.isNotEmpty(paymentGroup.getPayRecordNo())) {
            Response<PaymentRecordDto> response = paymentDubboService.queryPaymentRecordByPayNo(paymentGroup.getPayRecordNo());
            log.info("z支付记录信息：{}--》{}", paymentGroup.getPayRecordNo(), JSON.toJSONString(response));
            if (response != null && response.isSuccess() && response.getResultObject() != null) {
                webQueryPaymentGroupDto.setPayAccount(response.getResultObject().getPayAccount());
            }
        }else {
            webQueryPaymentGroupDto.setPayAccount(paymentGroup.getPayAccount());
        }
        webQueryPaymentGroupDto.setId(paymentGroup.getId());
        webQueryPaymentGroupDto.setType(1);
        webQueryPaymentGroupDto.setState(String.valueOf(paymentGroup.getState()));
        webQueryPaymentGroupDto.setStateDetail(paymentGroup.getStateDetail());
        webQueryPaymentGroupDto.setChannel(PayType.parse(paymentGroup.getChannel()).getName());
        webQueryPaymentGroupDto.setAmount(paymentGroup.getAmountDebited());
        webQueryPaymentGroupDto.setTransNum(paymentGroup.getTransNum());
        webQueryPaymentGroupDto.setCreateTime(paymentGroup.getPayDate());
        webQueryPaymentGroupDto.setOperationTime(paymentGroup.getPayDate());
        webQueryPaymentGroupDto.setOrderId(paymentGroup.getOrderId());
        webQueryPaymentGroupDto.setComment(paymentGroup.getComment());
        webQueryPaymentGroupDto.setRemark(paymentGroup.getComment());
        webQueryPaymentGroupDto.setLastOperator(paymentGroup.getLastOperator());
        webQueryPaymentGroupDto.setOperatorName(paymentGroup.getLastOperator());
        if (PaymentGroupStates.AUTHORIZED.getStateValue().equals(paymentGroup.getState())) {
            webQueryPaymentGroupDto.setShouldConfirm(true);
        }
        return webQueryPaymentGroupDto;
    }

    private WebQueryPaymentGroupDto convertRefundDtoToWebQueryPaymentGroupDto(RefundDto pRefundDto) {
        WebQueryPaymentGroupDto webQueryPaymentGroupDto = new WebQueryPaymentGroupDto();
        webQueryPaymentGroupDto.setId(pRefundDto.getRefundNo());
        webQueryPaymentGroupDto.setType(2);
        webQueryPaymentGroupDto.setChannel(pRefundDto.getPayType().getName());
        webQueryPaymentGroupDto.setAmount(pRefundDto.getRefundAmount());
        webQueryPaymentGroupDto.setTransNum(pRefundDto.getRefundTradeNo());
        webQueryPaymentGroupDto.setState(pRefundDto.getStatus().getCode());
        webQueryPaymentGroupDto.setStateDetail(pRefundDto.getStatus().getName());
        webQueryPaymentGroupDto.setRefundReason(pRefundDto.getReason().getName());
        webQueryPaymentGroupDto.setRemark(pRefundDto.getRemark());
        webQueryPaymentGroupDto.setOrderId(pRefundDto.getOrderNo());
        webQueryPaymentGroupDto.setOperationTime(pRefundDto.getApprovedTime());
        webQueryPaymentGroupDto.setCreateTime(pRefundDto.getCreateTime());
        webQueryPaymentGroupDto.setOperatorName(StringUtils.isEmpty(pRefundDto.getRefundOperatorName()) ? pRefundDto.getApprovedOperatorName() : pRefundDto.getRefundOperatorName());
        webQueryPaymentGroupDto.setOperatorName(StringUtils.isEmpty(pRefundDto.getRefundOperatorId()) ? pRefundDto.getApprovedOperatorId() : pRefundDto.getRefundOperatorName());
        return webQueryPaymentGroupDto;
    }

    private WebQueryPaymentGroupDto convertAjustmentToWebQueryPaymentGroupDto(PriceAdjustment priceAdjustment, Order order) {
        WebQueryPaymentGroupDto webQueryPaymentGroupDto = new WebQueryPaymentGroupDto();
        webQueryPaymentGroupDto.setId(String.valueOf(priceAdjustment.getId()));
        webQueryPaymentGroupDto.setType(1);
        webQueryPaymentGroupDto.setState(String.valueOf(PaymentGroupStates.SETTLED.getStateValue()));
        webQueryPaymentGroupDto.setStateDetail(PaymentGroupStates.SETTLED.getDescription());
        webQueryPaymentGroupDto.setChannel("优惠券");
        webQueryPaymentGroupDto.setAmount(priceAdjustment.getTotalAdjustment());
        webQueryPaymentGroupDto.setTransNum("");
        webQueryPaymentGroupDto.setCreateTime(order.getSubmitTime());
        webQueryPaymentGroupDto.setOperationTime(order.getSubmitTime());
        webQueryPaymentGroupDto.setOrderId(order.getId());
        return webQueryPaymentGroupDto;
    }

    @Override
    public Response<WebShippingGroupDto> shippingGroupInfo(String id) {
        log.debug("method:shippingGroupInfo,id{}" , id);
        Response<WebShippingGroupDto> response = new Response<WebShippingGroupDto>();
        try {
            Order order = orderService.selectByPrimaryKey(id);
            if(order == null){
                log.error("order is null, order id :" + id);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("订单不存在，订单ID：" + id);
                return response;
            }
            ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
            if(shippingGroup == null){
               log.error("shippingGroup is null, order id :" + id);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("服务器异常！");
                return response;
            }
            WebShippingGroupDto webShippingGroupDto = BeanConvertUtils.convert(shippingGroup, WebShippingGroupDto.class);
            webShippingGroupDto.setShipOnDate(shippingGroup.getActualShipDate());
            //添加物流状态 yinyuxin
            webShippingGroupDto.setShippingState(order.getShippingState());
            webShippingGroupDto.setShippingStateDesc(ShippingStates.shippingStateDesc.get(order.getShippingState()));
            if (ShippingModes.UNIFIED_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())) {
                webShippingGroupDto.setDistributionName("雅堂");
            } else if (ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())) {
                ProviderShippingGroup providerShippingGroup = providerShippingGroupService.selectByShippingGroupId(shippingGroup.getId());
                webShippingGroupDto.setDistributionName(providerShippingGroup.getSpName());
            }
            List<CommerceItem> list = commerceItemService.getCommerceItemAndPriceForOrderId(id);
            List<WebShippingProductDto> shippingProductDtos = BeanConvertUtils.convertList(list, WebShippingProductDto.class);
            for (WebShippingProductDto webShippingProductDto : shippingProductDtos) {
                ProductIndexDto productDto = productHelper.findProductIndexById(webShippingProductDto.getSkuId());
                if (productDto != null) {
                    webShippingProductDto.setSkuId(productDto.getProductCode());
                    webShippingProductDto.setProductName(productDto.getSaleName());
                    webShippingProductDto.setUnit(productDto.getMinUnit());
                    List<InternationalCodeDto> internationalCodes = BeanConvertUtils.convertList(productDto.getInternationalCodes(),InternationalCodeDto.class);
                    webShippingProductDto.setInternationalCodes(internationalCodes);
                }
                if (ShippingStates.COMPLETED.equals(order.getShippingState())) {
                    webShippingProductDto.setCompletedMulQuantity(webShippingProductDto.getQuantity() - webShippingProductDto.getCompletedQuantity());
                    webShippingProductDto.setCompletedMulAmount(webShippingProductDto.getCompletedMulQuantity() * webShippingProductDto.getSalePrice());
                }
            }

            webShippingGroupDto.setShippingProductDtos(shippingProductDtos);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(webShippingGroupDto);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }

        return response;
    }

    @Override
    public Response<Boolean> resendOrder(String id, String userId) {
        log.debug("method:shippingGroupInfo,id{},userId{}" , id , userId);
        Response<Boolean> response = new Response<Boolean>();
        try {

            Order order = orderService.selectByPrimaryKey(id);
            UpdateOrderVO updateOrderVO = new UpdateOrderVO();
            updateOrderVO.setOrder(order);
            updateOrderVO.setPreOrderState(order.getState());
            if (order == null) {
                orderLogService.saveOrderLog(id, (short) 999, "重新发送订单失败，订单不存在id:" + id, userId);
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setResultObject(true);
                return response;
            }
            if (order.getShippingState().equals(ShippingStates.WMS_REJECT) || order.getShippingState().equals(ShippingStates.PENDING_TRANSFER)) {
                if (order.getShippingState().equals(ShippingStates.WMS_REJECT)) {
                    order.setShippingState(ShippingStates.PENDING_TRANSFER);
                }
                if (order.getInteractivePendingRes()) {
                    response.setSuccess(false);
                    response.setErrorMessage("订单已传送到仓库，等待仓库接单，无需再传送.");
                    response.setCode(CommonsEnum.RESPONSE_500.getCode());
                    response.setResultObject(false);
                    return response;
                }
                updateOrderVO.setDescription("重新发送订单成功");
                updateOrderVO.setOperator(userId);
                orderService.updateAndSaveLog(updateOrderVO);

                OrderMessage wmsMessage = new OrderMessage();
                wmsMessage.setMssageType(OrderMessageType.SendWMSDeliveryMessage);
                wmsMessage.setOrderId(order.getId());
                orderMessageSender.sendMsg(wmsMessage);
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setResultObject(true);
            } else {
                orderLogService.saveOrderLog(id, (short) 999, "重新发送订单失败，订单状态不正确id:" + id, userId);
                response.setSuccess(false);
                response.setErrorMessage("重新发送订单失败，订单状态不正确：" + order.getShippingState());
                response.setCode(CommonsEnum.RESPONSE_10022.getCode());
                response.setResultObject(true);
            }

        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Boolean> splitOrderByInventory(String orderId, String operator) {
        log.info("PC端实时拆单的请求参数:订单id{},操作人{}", orderId, operator);
        Response<Boolean> response = new Response<Boolean>();
        try {

            response = validateSplitOrder(orderId);
            if (!response.isSuccess()) {
                return response;
            }

            OrderMessage orderMessage = new OrderMessage();
            orderMessage.setOrderId(orderId);
            orderMessage.setMssageType(OrderMessageType.Reserve_Order_Inventory_SPLIT);
            orderMessageSender.sendMsg(orderMessage);

            //保存日志//
            orderLogService.saveOrderLog(orderId, null, "根据实时库存拆单", operator);
            response.setSuccess(true);
            response.setResultObject(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setResultObject(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Boolean> manualSplitOrder(String parentOrderId, List<Map<String, Long>> groups, String operator) {
        log.info("PC端手动拆单的请求参数:订单id{},操作人{},分组：{}", parentOrderId, operator, groups);
        Response<Boolean> response = validateSplitOrder(parentOrderId);
        if (!response.isSuccess()) {
            return response;
        }

        try {
            SplitOrderRequestDto splitOrderRequestDto = new SplitOrderRequestDto();
            splitOrderRequestDto.setFromType(SplitOrderFromType.SPLIT_FROM_MANUAL);
            splitOrderRequestDto.setParentOrderId(parentOrderId);
            List<SplitOrderSubGroupDto> effectiveGroups = getEffectiveSplitGroup(groups);
            if (CollectionUtils.isEmpty(effectiveGroups) || effectiveGroups.size() == 1) {
                response.setSuccess(false);
                response.setErrorMessage("无有效拆单分组，不进行拆单");
                return response;
            }
            splitOrderRequestDto.getGroups().addAll(effectiveGroups);
            if (webOrderServiceFlow.manualSplitOrder(parentOrderId, groups, operator)) {
                OrderMessage orderMessage = new OrderMessage();
                orderMessage.setOrderId(parentOrderId);
                orderMessage.setBody(splitOrderRequestDto.toJson());
                orderMessage.setMssageType(OrderMessageType.MANUAL_SPLIT_ORDER);
                orderMessageSender.sendMsg(orderMessage);
                response.setSuccess(true);
                response.setResultObject(true);
            } else {
                log.info("手动拆单不成功");
                response.setSuccess(true);
                response.setResultObject(false);
            }

        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setResultObject(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    private Response<Boolean> validateSplitOrder(String orderId) {
        Response<Boolean> response = new Response<Boolean>();
        response.setSuccess(true);

        if (StringUtils.isEmpty(orderId)) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("参数错误");
            response.setResultObject(false);
            return response;
        }
        //根据订单id找到对应的订单
        Order order = orderService.selectByPrimaryKey(orderId);
        if(order.getInteractivePendingRes()){
            log.error("订单已传送到仓库，等待接单，不允许拆单:{}", order.getId());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("订单已传送到仓库，等待接单，不允许拆单");
            response.setResultObject(false);
            return response;
        }
        if (order.isPendingSplit()) {
            log.error("正在拆单，请勿重复提交拆单请求:{}", order.getId());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("正在拆单，请勿重复提交拆单请求");
            response.setResultObject(false);
            return response;
        }
        if (OrderTypes.ELECTRONIC_ORDER.equalsIgnoreCase(order.getOrderType())) {
            log.error("虚拟订单,不允许拆单:{}", order.getId());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("虚拟订单,不允许拆单");
            response.setResultObject(false);
            return response;
        }
       /* if (OrderTypes.ONLINE_RETAILERS_SALE.equalsIgnoreCase(order.getOrderType())) {
            log.error("电商销售订单,不允许拆单:{}", order.getId());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("电商销售订单,不允许拆单");
            response.setResultObject(false);
            return response;
        }*/
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(order.getOrderState())) {
            log.error("订单已拆单:{}", order.getId());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("订单已拆单");
            response.setResultObject(false);
            return response;
        }
        return response;
    }

    private List<SplitOrderSubGroupDto> getEffectiveSplitGroup(List<Map<String, Long>> groups) {
        List<SplitOrderSubGroupDto> effectiveGroups =new ArrayList<SplitOrderSubGroupDto>();
        if (CollectionUtils.isEmpty(groups)) {
            return effectiveGroups;
        }
        SplitOrderSubGroupDto splitOrderSubGroupDto;
        for (Map<String, Long> map : groups) {
            if (map == null || map.size() == 0) {
                continue;
            }
            splitOrderSubGroupDto = SplitOrderSubGroupDto.newInstance(SubOrderType.YT_SHIPPING);
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                if (entry.getValue() == null || entry.getValue() == 0) {
                    continue;
                }
                splitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(Long.valueOf(entry.getKey()), entry.getValue()));
            }
            if (CollectionUtils.isEmpty(splitOrderSubGroupDto.getItems())) {
                continue;
            }
            effectiveGroups.add(splitOrderSubGroupDto);
        }
        return effectiveGroups;
    }

    //@Override
    //public Response<Boolean> updateOfflinePaymentInfo(UpdatePaymentInfoDto updatePaymentInfoDto) {
    //    log.debug("/updateOfflinePaymentInfo:request param,", JSONObject.toJSON(updatePaymentInfoDto));
    //    Response<Boolean> resultResponse = new Response<Boolean>();
    //    try {
    //        Order orderRecord = orderService.selectByPrimaryKey(updatePaymentInfoDto.getOrderId());
    //        if (orderRecord == null) {
    //            resultResponse.setSuccess(false);
    //            resultResponse.setErrorMessage("订单不存在");
    //            return resultResponse;
    //        }
	//
    //        mOrderPaymentsService.savePaymentInfo(orderRecord.getId(), updatePaymentInfoDto.getAmount(), updatePaymentInfoDto.getPayDate(), "xianxia", updatePaymentInfoDto.getCashier(), "XianXia");
	//
    //        orderRecord.setPaymentState(PaymentStates.DEBITED);
    //        orderRecord.setState(OrderTotalStates.PENDING_PROCESS.getStateValue());
    //        orderRecord.setStateDetail(OrderTotalStates.PENDING_PROCESS.getDescription());
    //        orderService.updateOrderSelective(orderRecord);
    //        orderLogService.saveOrderLog(orderRecord.getId(), orderRecord.getState(), "订单已支付！", StringUtils.isEmpty(updatePaymentInfoDto.getOperator()) ? "system" : updatePaymentInfoDto.getOperator());
	//
    //        //发生支付成功消息
    //        OrderMessage wmsMessage = new OrderMessage();
    //        wmsMessage.setMssageType(OrderMessageType.ORDER_PAY_SUCCESS);
    //        wmsMessage.setOrderId(updatePaymentInfoDto.getOrderId());
    //        orderMessageSender.sendMsg(wmsMessage);
    //        resultResponse.setSuccess(true);
    //    } catch (Exception ex) {
    //        log.error("保存支付异常", ex);
    //        resultResponse.setSuccess(false);
    //        resultResponse.setErrorMessage(ex.getMessage());
    //    }
    //    return resultResponse;
    //}

    /**
     * 查询增强版订单列表
     *
     * @param condition
     * @return
     */

    @Override
    public Response<PageResult<OrderEnhancedDto>> getEnhancedOrderPageList(WebQueryOrderConditionDto condition) {
        log.info("method:queryOrder,param:{}", JSONObject.toJSONString(condition));
        Response<PageResult<OrderEnhancedDto>> response = new Response<PageResult<OrderEnhancedDto>>();
        try {
            return getOrderEnhancedDtoPageResult(condition);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    private Response<PageResult<OrderEnhancedDto>> getOrderEnhancedDtoPageResult(WebQueryOrderConditionDto condition) throws Exception{
        if(way == 1){
            log.info("getOrderEnhancedDtoPageResult from main data.");
            return getOrderEnhancedDtoPageResultFromMainData(condition);
        }
        log.info("getOrderEnhancedDtoPageResult from locality.");
        return getOrderEnhancedDtoPageResultFromLocal(condition);
    }

    private Response<PageResult<OrderEnhancedDto>> getOrderEnhancedDtoPageResultFromMainData(WebQueryOrderConditionDto condition) throws Exception{
        Response<PageResult<OrderEnhancedDto>> response = new Response<PageResult<OrderEnhancedDto>>();
        PageResult<OrderEnhancedDto> pageResult = new PageResult<OrderEnhancedDto>();

        /*QueryOrderItemDto queryOrderItemDto = BeanConvertUtils.convert(condition,QueryOrderItemDto.class);
        queryOrderItemDto.setPageNum(1);
        QueryOrderListDto queryDto = queryOrderItemDtoTOQueryOrderListDto(queryOrderItemDto);
        queryDto.setBranchCompanyIds(condition.getBranchCompanyIds());
        queryDto.setPageSize(indexPageSize);*/

        QueryOrderListDto queryOrderListDto = BeanConvertUtils.convert(condition, QueryOrderListDto.class);
        queryOrderListDto.setPageNum(1);
        queryOrderListDto.setPageSize(indexPageSize);
        log.info("getOrderEnhancedDtoPageResult from main data condition:{}", JSON.toJSONString(queryOrderListDto));
        Response<TableDataResult<OrderListDto>> orderCountResponse = dataCenterScDubboService.queryOrderList(queryOrderListDto);
        if(orderCountResponse.getResultObject().getRecordsTotal() > indexPageSize){
            log.info("getOrderEnhancedDtoPageResult from main data order count:{},current limit:{}", orderCountResponse.getResultObject().getRecordsTotal(),indexPageSize);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_10023.getCode());
            response.setErrorMessage(String.valueOf(indexPageSize));
            return response;
        }

        log.info("====getOrderEnhancedDtoPageResultFromMainData data==== " + JSON.toJSON(orderCountResponse.getResultObject().getData()));
        List<OrderEnhancedDto> orderEnhancedDtoList = encapOrderEnhancedDtoPageResultFromMainData(orderCountResponse.getResultObject().getData());
//        log.info("getOrderEnhancedDtoPageResult from main data condition:{}", JSON.toJSONString(queryOrderItemDto));
//        Response<TableDataResult<OrderItemDto>> resultResponse = dataCenterScDubboService.queryOrderItem(queryOrderItemDto);
//        if(resultResponse == null || !resultResponse.isSuccess()){
//            response.setSuccess(false);
//            response.setCode(CommonsEnum.RESPONSE_500.getCode());
//            response.setCode(CommonsEnum.RESPONSE_500.getName());
//            return response;
//        }
//        List<OrderEnhancedDto> orderEnhancedDtoList = BeanConvertUtils.convertList(resultResponse.getResultObject().getData(),OrderEnhancedDto.class);
        pageResult.setData(orderEnhancedDtoList);
        response.setSuccess(true);
        response.setResultObject(pageResult);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        return response;
    }

    private List<OrderEnhancedDto> encapOrderEnhancedDtoPageResultFromMainData(List<OrderListDto> data) {

        List<OrderEnhancedDto> orderEnhancedDtoList = new ArrayList<OrderEnhancedDto>();

        if(CollectionUtils.isEmpty(data)){
            return orderEnhancedDtoList;
        }

        for (OrderListDto orderListDto : data){
            OrderEnhancedDto orderEnhancedDto = BeanConvertUtils.convert(orderListDto,OrderEnhancedDto.class);
            if(CollectionUtils.isEmpty(orderListDto.getOrderItemPoList())){
                orderEnhancedDtoList.add(orderEnhancedDto);
                continue;
            }
            for (OrderItemDto orderItemDto : orderListDto.getOrderItemPoList()){
                orderEnhancedDto.setProductId(orderItemDto.getProductId());
                orderEnhancedDto.setProductCode(orderItemDto.getProductCode());
                orderEnhancedDto.setProductName(orderItemDto.getProductName());
                orderEnhancedDto.setInternationalCode(orderItemDto.getInternationalCode());
                orderEnhancedDto.setFirstLevelCategoryName(orderItemDto.getFirstLevelCategoryName());
                orderEnhancedDto.setSecondLevelCategoryName(orderItemDto.getSecondLevelCategoryName());
                orderEnhancedDto.setThirdLevelCategoryName(orderItemDto.getThirdLevelCategoryName());
                orderEnhancedDto.setQuantity(StringUtils.isBlank(orderItemDto.getQuantity())?0:Long.valueOf(orderItemDto.getQuantity()));
                orderEnhancedDto.setAmount(StringUtils.isBlank(orderItemDto.getAmount())?0:Double.valueOf(orderItemDto.getAmount()));
                orderEnhancedDto.setSalePrice(StringUtils.isBlank(orderItemDto.getSalePrice())?0:Double.valueOf(orderItemDto.getSalePrice()));
                orderEnhancedDto.setAvCost((StringUtils.isBlank(orderItemDto.getAvCost())?0:Double.valueOf(orderItemDto.getAvCost())));
                orderEnhancedDto.setCost((StringUtils.isBlank(orderItemDto.getCost())?0:Double.valueOf(orderItemDto.getCost())));
                orderEnhancedDto.setStateDetail(orderItemDto.getStateDetail());
                orderEnhancedDtoList.add(orderEnhancedDto);
            }
        }
        return orderEnhancedDtoList;

    }

    private QueryOrderListDto queryOrderItemDtoTOQueryOrderListDto(QueryOrderItemDto queryOrderItemDto) {
        QueryOrderListDto queryOrderListDto = new QueryOrderListDto();
        queryOrderListDto.setId(queryOrderItemDto.getId());
        queryOrderListDto.setCreatedByOrderId(queryOrderItemDto.getCreatedByOrderId());
        queryOrderListDto.setOrderType(queryOrderItemDto.getOrderType());
        queryOrderListDto.setOrderState(queryOrderItemDto.getOrderState());
        queryOrderListDto.setPaymentState(queryOrderItemDto.getPaymentState());
        queryOrderListDto.setShippingState(queryOrderItemDto.getShippingState());
        queryOrderListDto.setFranchiseeId(queryOrderItemDto.getFranchiseeId());
        queryOrderListDto.setBranchCompanyId(queryOrderItemDto.getBranchCompanyId());
        queryOrderListDto.setCellphone(queryOrderItemDto.getCellphone());
        queryOrderListDto.setSubmitStartTime(queryOrderItemDto.getSubmitStartTime());
        queryOrderListDto.setSubmitEndTime(queryOrderItemDto.getSubmitEndTime());
        queryOrderListDto.setCompletedTimeStart(queryOrderItemDto.getCompletedTimeStart());
        queryOrderListDto.setCompletedTimeEnd(queryOrderItemDto.getCompletedTimeEnd());
        queryOrderListDto.setPayTimeStart(queryOrderItemDto.getPayTimeStart());
        queryOrderListDto.setPayTimeEnd(queryOrderItemDto.getPayTimeEnd());
        queryOrderListDto.setRefundTimeStart(queryOrderItemDto.getRefundTimeStart());
        queryOrderListDto.setRefundTimeEnd(queryOrderItemDto.getRefundTimeEnd());
        queryOrderListDto.setProductId(queryOrderItemDto.getProductId());
        queryOrderListDto.setThirdPartOrderNo(queryOrderItemDto.getThirdPartOrderNo());
        queryOrderListDto.setPaymentStateOrQuery(queryOrderItemDto.getPaymentStateOrQuery());
        queryOrderListDto.setProductName(queryOrderItemDto.getProductName());
        queryOrderListDto.setFranchiseeStoreId(queryOrderItemDto.getFranchiseeStoreId());
        queryOrderListDto.setTransNum(queryOrderItemDto.getTransNum());
        queryOrderListDto.setContainParent(queryOrderItemDto.getContainParent());
        queryOrderListDto.setPageNum(queryOrderItemDto.getPageNum());
        return queryOrderListDto;
    }

    private Response<PageResult<OrderEnhancedDto>> getOrderEnhancedDtoPageResultFromLocal(WebQueryOrderConditionDto condition) {
        Response<PageResult<OrderEnhancedDto>> response = new Response<PageResult<OrderEnhancedDto>>();
        PageResult<OrderEnhancedDto> pageResult = new PageResult<OrderEnhancedDto>();
        Map<String,Object> map = MyBeanUtils.beanToMap(condition);
        map.put("start",0);
        map.put("end", condition.getPageSize());
        long total = orderService.getOrderPageListCount(map);
        if(total > indexPageSize){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_10023.getCode());
            response.setErrorMessage(String.valueOf(indexPageSize));
            return response;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        List<OrderEnhancedPo> orderEnhancedPos = orderService.getEnhancedOrderPageList(map);
        //计算金额
        for (OrderEnhancedPo order:orderEnhancedPos) {
//            order.setCost(Double.valueOf(df.format(new BigDecimal(order.getAvCost()).multiply(new BigDecimal(order.getQuantity())))));
            double couponDiscountAmount = order.getCouponDiscountAmount()==null?0:order.getCouponDiscountAmount();
            double discountAmount = order.getDiscountAmount()==null?0:order.getDiscountAmount();
            double userDiscountAmount = order.getUserDiscountAmount()==null?0:order.getUserDiscountAmount();
            order.setDiscountTotalAmount(Double.valueOf(df.format(couponDiscountAmount + discountAmount + userDiscountAmount)));
        }

        List<OrderEnhancedDto> list = BeanConvertUtils.convertList(orderEnhancedPos, OrderEnhancedDto.class);
        BranchCompanyDto company = null;
        StoreDto storeDto = null;
        for (OrderEnhancedDto orderEnhancedDto : list) {
            if(company !=null && company.getId().equals(orderEnhancedDto.getBranchCompanyId())){
                orderEnhancedDto.setBranchCompanyName(company.getName());
            }else{
                company = organizationHelper.findBranchCompanyById(orderEnhancedDto.getBranchCompanyId());
                if (company != null) {
                    orderEnhancedDto.setBranchCompanyName(company.getName());
                }
            }
            if(storeDto !=null && storeDto.getId().equals(orderEnhancedDto.getFranchiseeStoreId())) {
                orderEnhancedDto.setFranchiseeStoreName(storeDto.getName());
            }else{
                storeDto = organizationHelper.findStoreById(orderEnhancedDto.getFranchiseeStoreId());
                if (storeDto != null) {
                    orderEnhancedDto.setFranchiseeStoreName(storeDto.getName());
                }
            }
            ProductIndexDto productDto = productHelper.findProductIndexById(orderEnhancedDto.getProductId());
            if (productDto != null) {
                if (!CollectionUtils.isEmpty(productDto.getInternationalCodes())) {
                    if (productDto.getInternationalCodes().get(0) != null) {
                        orderEnhancedDto.setInternationalCode(productDto.getInternationalCodes().get(0).getInternationalCode());
                    }
                }
                orderEnhancedDto.setFirstLevelCategoryName(productDto.getFirstLevelCategoryName());
                orderEnhancedDto.setSecondLevelCategoryName(productDto.getSecondLevelCategoryName());
                orderEnhancedDto.setThirdLevelCategoryName(productDto.getThirdLevelCategoryName());
                orderEnhancedDto.setProductCode(productDto.getProductCode());
                orderEnhancedDto.setProductName(productDto.getSaleName());
            }
        }
        pageResult.setData(list);
        response.setResultObject(pageResult);
        response.setSuccess(true);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return response;
    }

    @Override
    public Response<String> queryProfileIdByOrderId(String orderId) {
        Response<String> response = new Response<String>();
        try {
            response.setSuccess(true);
            response.setResultObject(orderService.queryProfileIdByOrderId(orderId));
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
