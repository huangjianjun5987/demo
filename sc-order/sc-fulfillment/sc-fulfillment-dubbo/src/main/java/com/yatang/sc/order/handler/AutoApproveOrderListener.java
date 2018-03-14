package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.ItemPrice;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderDictionary;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.ItemPriceService;
import com.yatang.sc.order.service.OrderDictionaryPropertiesService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.vo.UpdateOrderVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("autoApproveOrderListener")
public class AutoApproveOrderListener extends OrderMsgListener {
    protected Logger log = LoggerFactory.getLogger(AutoApproveOrderListener.class);
    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private CommerceItemService commerceItemService;
    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private OrderDictionaryPropertiesService orderDictionaryPropertiesService;

    @Autowired
    private OrderPriceService orderPriceService;

    @Autowired
    private ItemLocInventoryDubboService itemLocInventoryDubboService;

    @Autowired
    private ShippingGroupService mShippingGroupService;

    @Override
    public void handle(OrderMessage msg) throws MqBizFailureException {
        log.info("AutoApproveOrderListener handle order:" + msg.getOrderId());
        Order order = orderService.selectByPrimaryKey(msg.getOrderId());
        if (order == null) {
            log.error("can't find order by id: " + msg.getOrderId());
            return;
        }
        if (!OrderStates.PENDING_APPROVE.equals(order.getOrderState())) {
            log.info("{}-->订单状态不正确:{}", order.getId(), order.getOrderState());
            return;
        }
        UpdateOrderVO updateOrderVO = new UpdateOrderVO();
        updateOrderVO.setOrder(order);
        updateOrderVO.setPreOrderState(order.getState());
        if (checkOrder(order)) {
            order.setOrderState(OrderStates.APPROVED);
            updateOrderVO.setDescription("订单自动审核通过");
            updateOrderVO.setOperator("系统");
            orderService.updateAndSaveLog(updateOrderVO);

            OrderMessage approvedMsg = new OrderMessage();
            approvedMsg.setMssageType(OrderMessageType.ApprovedOrder);
            approvedMsg.setOrderId(order.getId());
            orderMessageSender.sendMsg(approvedMsg);
        } else {
            order.setOrderState(OrderStates.PENDING_MANUAL_APPROVE);
            updateOrderVO.setDescription("订单自动审核不通过，进入人工审核！");
            updateOrderVO.setOperator("系统");
            orderService.updateAndSaveLog(updateOrderVO);
        }
    }

    /*  整体毛利率计算公式：((行商品销售价格 – 行商品在出货仓的平均成本) ) / (行商品销售价格  )   */

    public boolean checkOrder(Order order) {
        try {
            if (order == null) {
                return false;
            }
            if (!allowAutoApproveUsers(order)) {
                log.info("加盟商账号不允许自动审核:{}", order.getProfileId());
                return false;
            }

            if (OrderTypes.ELECTRONIC_ORDER.equals(order.getOrderType())) {
                return true;
            }
            ShippingGroup shippingGroup = mShippingGroupService.selectByPrimaryKey(order.getShippingGroup());
            if (ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())) {
                //todo 添加直配检查
                return true;
            }
            boolean hasAbnomalGoods = false;
            //通过订单的id找到对应的商品
            List<OrderItems> itemsIds = orderItemsService.getOrderItemsForOrderId(order.getId());
            for (OrderItems itemsId : itemsIds) {
                //通过商品的id 找到商品
                CommerceItem commerceItem = commerceItemService.getCommerceItemForId(itemsId.getCommerceItemId());
                if (commerceItem == null) {
                    log.error("商品行不存在:{}", itemsId.getCommerceItemId());
                    hasAbnomalGoods = true;
                    commerceItem.setAbnormalGoods(true);
                    commerceItem.setAbnormalResonse("商品不存在");
                    commerceItemService.updateCommerceItem(commerceItem);
                    continue;
                }

                if (CommerceItemTypes.PROMOTION.equals(commerceItem.getType())) {
                    log.info("赠品，不检查毛利率异常:{}", commerceItem.getId());
                    continue;
                }
                if (CommerceItemTypes.SUB_ITEM.equals(commerceItem.getType())) {
                    log.info("套餐商品，不检查毛利率异常:{}", commerceItem.getId());
                    continue;
                }
                ItemPrice itemPrice = itemPriceService.getItemPriceForId(commerceItem.getItemPriceInfo());
                double salePrice = itemPrice.getSalePrice();
                if (salePrice == 0) {
                    log.error("商品销售价格为0:{}->{}", commerceItem.getId(), commerceItem.getItemPriceInfo());
                    hasAbnomalGoods = true;
                    commerceItem.setAbnormalGoods(true);
                    commerceItem.setAbnormalResonse("商品销售价格为0");
                    commerceItemService.updateCommerceItem(commerceItem);
                    continue;
                }

                ItemInventoryQueryParamDto queryParamDto = new ItemInventoryQueryParamDto();
                queryParamDto.setLogicWareHouseCode(order.getBranchCompanyArehouse());
                queryParamDto.setProductId(commerceItem.getProductId());
                Response<List<ItemLocSohDto>> itemLocSohDtosResponse = itemLocInventoryDubboService.queryItemInventoryListByParam(queryParamDto);
                if (!itemLocSohDtosResponse.isSuccess()) {
                    log.error("{}:商品库存信息不存在:{}", JSON.toJSONString(queryParamDto), itemLocSohDtosResponse.getErrorMessage());
                    hasAbnomalGoods = true;
                    commerceItem.setAbnormalGoods(true);
                    commerceItem.setAbnormalResonse("商品库存信息不存在");
                    commerceItemService.updateCommerceItem(commerceItem);
                    continue;
                }
                List<ItemLocSohDto> itemLocSohDtos = itemLocSohDtosResponse.getResultObject();
                if (CollectionUtils.isEmpty(itemLocSohDtos)) {
                    log.error("库存信息不存在-->商品id:{},仓库是:{}", commerceItem.getProductId(), order.getBranchCompanyArehouse());
                    hasAbnomalGoods = true;
                    commerceItem.setAbnormalGoods(true);
                    commerceItem.setAbnormalResonse("商品库存信息不存在");
                    commerceItemService.updateCommerceItem(commerceItem);
                    continue;
                }

                Double purchasePrice = getValidPurchasePrice(itemLocSohDtos);
                if (purchasePrice == null || purchasePrice == 0.0) {
                    log.error("采购价格为0-->商品id:{},仓库是:{}", commerceItem.getProductId(), order.getBranchCompanyArehouse());
                    hasAbnomalGoods = true;
                    commerceItem.setAbnormalGoods(true);
                    commerceItem.setAbnormalResonse("商品采购价格为0");
                    commerceItemService.updateCommerceItem(commerceItem);
                    continue;
                }

                double grossProfitMargin = (salePrice - purchasePrice) / salePrice;
                double salePriceDicDouble = 0.0;
                //获取数据字典中毛利率的值
                OrderDictionary orderDictionary = orderDictionaryPropertiesService.selectByPrimaryKey("grossProfitRate");
                if (orderDictionary == null || StringUtils.isEmpty(orderDictionary.getPropertyValue())) {
                    log.warn("[grossProfitRate] 未配置");
                } else {
                    salePriceDicDouble = Double.parseDouble(orderDictionary.getPropertyValue());
                }
                if (grossProfitMargin < salePriceDicDouble) {
                    log.info("[{}商品毛利率异常]{}---->{}毛利率异常", commerceItem.getProductId(), grossProfitMargin, salePriceDicDouble);
                    hasAbnomalGoods = true;
                    commerceItem.setAbnormalGoods(true);
                    commerceItem.setAbnormalResonse("商品毛利率异常");
                    commerceItemService.updateCommerceItem(commerceItem);
                    continue;
                }
            }
            if (hasAbnomalGoods) {
                return false;
            }
            //订单金额大于“订单商品最大订货额”（系统参数），则进入异常状态；
            //根据订单中订单价格的id 找到对应的订单价格信息
            Long orderPriceInfoId = order.getPriceInfo();
            OrderPrice orderPriceInfo = orderPriceService.getOrderPriceForId(orderPriceInfoId);
            double orderPrice = orderPriceInfo.getAmount();
            double orderPriceDicDouble = Double.MAX_VALUE;
            //从数据字典中取出订单商品最大订货额
            OrderDictionary orderDictionary = orderDictionaryPropertiesService.selectByPrimaryKey("orderTotal");
            if (orderDictionary == null || StringUtils.isEmpty(orderDictionary.getPropertyValue())) {
                log.warn("[orderTotal] 未配置");
            } else {
                orderPriceDicDouble = Double.parseDouble(orderDictionary.getPropertyValue());
            }
            if (orderPrice > orderPriceDicDouble) {
                log.info("[{}订单商品最大订货额异常]最大订货额应为{},实际为：{}", order.getId(), orderPriceDicDouble, orderPrice);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("订单自动审核抛异常");
            return false;
        }
    }

    /**
     * 获取有效的销售价格
     *
     * @param pItemLocSohDtos
     * @return
     */
    private Double getValidPurchasePrice(List<ItemLocSohDto> pItemLocSohDtos) {
        Double purchasePrice = null;
        for (ItemLocSohDto itemLocSohDto : pItemLocSohDtos) {
            if (itemLocSohDto != null) {
                purchasePrice = itemLocSohDto.getAvCost();
                if (purchasePrice != null && purchasePrice != 0.0) {
                    break;
                }
            }
        }
        return purchasePrice;
    }

    /**
     * 是否允许该订单用户的订单通过自动审核
     *
     * @param pOrder
     * @return true 允许自定审核，false 不允许自动审核
     */
    protected boolean allowAutoApproveUsers(Order pOrder) {
        OrderDictionary orderDictionary = orderDictionaryPropertiesService.selectByPrimaryKey("notAllowAutoApproveUsers");
        if (orderDictionary == null || StringUtils.isEmpty(orderDictionary.getPropertyValue())) {
            return true;
        }
        String[] userIds = orderDictionary.getPropertyValue().split(",");
        List<String> userIdsList = Arrays.asList(userIds);
        if (CollectionUtils.isEmpty(userIdsList)) {
            return true;
        }
        log.info("不允许自动审名单：{}", JSON.toJSONString(userIdsList));
        if (userIdsList.contains(pOrder.getFranchiseeStoreId())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.AutoApproveOrder.equals(msg.getMssageType());
    }
}
