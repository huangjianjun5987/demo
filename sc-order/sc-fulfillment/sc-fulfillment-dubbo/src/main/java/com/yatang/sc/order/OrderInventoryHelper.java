package com.yatang.sc.order;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dto.OrderInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.OrderFrom;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiugang on 8/7/2017.
 */
@Service("orderInventoryHelper")
public class OrderInventoryHelper {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemsService orderItemsService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    ItemLocInventoryDubboService itemLocInventoryDubboService;


    public Integer reserveOrderInventory(Order order, boolean shouldSplit) {
        OrderInventoryDto orderInventoryDto = getOrderInventoryDto(order);
        Response<Integer> reRes = itemLocInventoryDubboService.reserveOrder(orderInventoryDto, shouldSplit);
        log.info("Reserve order response:{}", JSON.toJSONString(reRes));
        return reRes.getResultObject();
    }

    public boolean cancelReserveOrder(Order order) {
        boolean result = false;
        OrderInventoryDto orderInventoryDto = getOrderInventoryDto(order);
        Response<Boolean> reRes = itemLocInventoryDubboService.cancelReserveOrder(orderInventoryDto);
        log.info("Cancel reserve order response:{}", JSON.toJSONString(reRes));
        if (reRes != null && reRes.isSuccess() && Boolean.TRUE.equals(reRes.getResultObject())) {
            result = true;
        }
        return result;
    }

    private OrderInventoryDto getOrderInventoryDto(Order order) {
        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId(order.getId());
        OrderInventoryDto orderInventoryDto = new OrderInventoryDto();
        List<ItemInventoryDto> itemInventoryDtos = new ArrayList<ItemInventoryDto>();
        for (OrderItems orderItem : orderItems) {
            CommerceItem commerceItem = commerceItemService.getCommerceItemForId(orderItem.getCommerceItemId());
            ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
            itemInventoryDto.setItemId(String.valueOf(commerceItem.getId()));
            itemInventoryDto.setProductId(commerceItem.getProductId());
            itemInventoryDto.setItemQty(commerceItem.getQuantity());
            itemInventoryDto.setLoc(order.getBranchCompanyArehouse());
            itemInventoryDto.setActualPrice(commerceItem.getItemPrice().getSalePrice());
            if(commerceItem.getUnitQuantity()== null){
            log.info("{}-->{}:商品内装数不存在",commerceItem.getId(),commerceItem.getProductId());
                itemInventoryDto.setUnitQuantity(0);
            }else {
                itemInventoryDto.setUnitQuantity(commerceItem.getUnitQuantity());
            }
            itemInventoryDto.setBranchCompanyId(order.getBranchCompanyId());
            itemInventoryDtos.add(itemInventoryDto);
        }
        orderInventoryDto.setId(order.getId());
        orderInventoryDto.setItemInventoryDtos(itemInventoryDtos);
        return orderInventoryDto;
    }

    public boolean canSplitOrder(Order pOrder) {
        log.info("canSplitOrder:{}",JSON.toJSONString(pOrder));
        if (pOrder == null) {
            return false;
        }

        if (!OrderFrom.SHIPPING_MODE_SPLIT.getValue().equals(pOrder.getFrom()) && StringUtils.isNotEmpty(pOrder.getCreatedByOrderId())) {
            return false;
        }
        return true;
    }
}
