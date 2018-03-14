package com.yatang.sc.purchase.service;

import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptItemsDto;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.split.SplitOrderRequestDto;

import java.util.List;

public interface ProviderOrderHelper {
    SplitOrderRequestDto genSplitOrderRequest(List<CommerceItem> pCommerceItems, List<PmPurchaseReceiptItemsDto> pReceiptItemsDtoList);
}
