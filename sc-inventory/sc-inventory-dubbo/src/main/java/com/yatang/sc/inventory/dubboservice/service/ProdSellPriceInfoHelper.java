package com.yatang.sc.inventory.dubboservice.service;

import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;

import java.util.List;

/**
 * Created by qiugang on 7/12/2017.
 */
public interface ProdSellPriceInfoHelper {

    ProdSellPriceInfoDto getGoodsSellPrice(String productId, String branchCompanyId);

    List<ProdSellPriceInfoDto> getGoodsSellPriceByProductIdsAndCompanyId(String branchCompanyId, List<String> productIds);

    boolean isPreHarvestProd(ProdSellPriceInfoDto pProdSellPriceInfoDto);
}
