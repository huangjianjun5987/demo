package com.yatang.sc.purchase.service;

import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.exception.PricingException;

import java.util.List;
import java.util.Map;

/**
 * Created by qiugang on 7/10/2017.
 */
public  interface ItemPricingEngine {

    public void pricingItems(List<CommerceItemDto> items, Map contentMap,boolean isMock)  throws PricingException;

    void pricingItem(CommerceItemDto item, Map contentMap)  throws PricingException;

    public ProdSellPriceInfoDto getProductPriceInfo(String productId, String branchCompanyId);
}
