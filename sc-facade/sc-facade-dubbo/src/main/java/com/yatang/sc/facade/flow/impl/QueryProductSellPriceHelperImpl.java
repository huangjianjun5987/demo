package com.yatang.sc.facade.flow.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.facade.flow.QueryProductSellPriceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by qiugang on 11/4/2017.
 */
@Service("queryProductSellPriceHelper")
public class QueryProductSellPriceHelperImpl implements QueryProductSellPriceHelper {

    Logger log = LoggerFactory.getLogger(QueryProductSellPriceHelperImpl.class);

    @Autowired
    private GoodsPriceQueryDubboService goodsPriceQueryDubboService;// 销售价格查询

    @Override
    @LocalCache(group = "facadePriceInfo", expireAfterWrite = 1, maximumSize = 10000)
    public ProdSellPriceInfoDto queryProdSellPriceInfo(String productId, String branchCompanyId) {
        Response<ProdSellPriceInfoDto> goodsSellPrice = goodsPriceQueryDubboService
                .getGoodsSellPrice(productId, branchCompanyId);
        if (goodsSellPrice != null && goodsSellPrice.getResultObject() != null) {
            log.info("查询销售信息失败：{}", goodsSellPrice);
            return goodsSellPrice.getResultObject();
        }
        return null;
    }
}
