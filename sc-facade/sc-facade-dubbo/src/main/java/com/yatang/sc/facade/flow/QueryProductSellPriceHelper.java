package com.yatang.sc.facade.flow;

import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;

/**
 * Created by qiugang on 11/4/2017.
 */
public interface QueryProductSellPriceHelper {

    public ProdSellPriceInfoDto queryProdSellPriceInfo(String productId, String branchCompanyId);
}
