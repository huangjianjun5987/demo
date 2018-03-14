package com.yatang.sc.purchase.service;

import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;

public interface ProdSellPriceInfoHelper {
    ProdSellPriceInfoDto getGoodsSellPrice(String productId, String branchCompanyId);
}
