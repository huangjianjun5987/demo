package com.yatang.sc.purchase.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.purchase.service.ProdSellPriceInfoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("prodSellPriceInfoHelper")
public class ProdSellPriceInfoHelperImpl implements ProdSellPriceInfoHelper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GoodsPriceQueryDubboService goodsPriceDubboService;

    @Override
    @LocalCache(group = "purchasePriceInfo", expireAfterWrite = 1, maximumSize = 10000)
    public ProdSellPriceInfoDto getGoodsSellPrice(String productId, String branchCompanyId) {

        if (StringUtils.isEmpty(productId)) {
            return null;
        }
        Response<ProdSellPriceInfoDto> response = goodsPriceDubboService.getGoodsSellPrice(productId, branchCompanyId);
        if (response == null || !response.isSuccess()) {
            logger.info("商品销售关系不存在:{}->{}", productId, branchCompanyId);
            return null;
        }
        return response.getResultObject();
    }
}
