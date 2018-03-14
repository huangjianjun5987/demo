package com.yatang.sc.purchase.service.impl;

import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptItemsDto;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.split.SplitOrderItemDto;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.split.SplitOrderSubGroupDto;
import com.yatang.sc.order.split.enums.SplitOrderFromType;
import com.yatang.sc.order.split.enums.SubOrderType;
import com.yatang.sc.purchase.service.ProviderOrderHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 直配订单帮助类
 */
@Service
public class ProviderOrderHelperImpl implements ProviderOrderHelper {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public SplitOrderRequestDto genSplitOrderRequest(List<CommerceItem> pCommerceItems, List<PmPurchaseReceiptItemsDto> pReceiptItemsDtoList) {
        SplitOrderRequestDto splitOrderRequestDto = new SplitOrderRequestDto();
        splitOrderRequestDto.setFromType(SplitOrderFromType.ASN_NOTICE_SPLIT);
        SplitOrderSubGroupDto notDeliveriedSplitOrderSubGroupDto = new SplitOrderSubGroupDto(SubOrderType.PROVIDER_SHIPPING);//未发货分组
        SplitOrderSubGroupDto deliveriedSplitOrderSubGroupDto = new SplitOrderSubGroupDto(SubOrderType.PROVIDER_SHIPPING_ASN);//已发货分组
        splitOrderRequestDto.getGroups().add(notDeliveriedSplitOrderSubGroupDto);
        splitOrderRequestDto.getGroups().add(deliveriedSplitOrderSubGroupDto);

        boolean isFind;
        for (CommerceItem item : pCommerceItems) {
            isFind = false;
            for (PmPurchaseReceiptItemsDto receiptItemsDto : pReceiptItemsDtoList) {
                if (item.getProductId().equals(receiptItemsDto.getProductId())) {//收货单找到对应商品
                    isFind = true;
                    if (item.getQuantity().intValue() == receiptItemsDto.getDeliveryNumber()) {//商品全部发货
                        deliveriedSplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(item.getId(), Long.valueOf(receiptItemsDto.getDeliveryNumber())));
                    } else {
                        notDeliveriedSplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(item.getId(), item.getQuantity() - Long.valueOf(receiptItemsDto.getDeliveryNumber())));
                        deliveriedSplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(item.getId(), Long.valueOf(receiptItemsDto.getDeliveryNumber())));
                    }
                    break;
                }
            }
            if (!isFind) {//收货单中未找到商品信息
                notDeliveriedSplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(item.getId(), item.getQuantity()));
            }
        }
        return splitOrderRequestDto;
    }
}
