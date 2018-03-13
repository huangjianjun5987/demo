package com.yatang.sc.inventory.dubboservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.inventory.dubboservice.service.ProdSellPriceInfoHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiugang on 7/12/2017.
 */

@Service("prodSellPriceInfoHelper")
public class ProdSellPriceInfoHelperImpl implements ProdSellPriceInfoHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsPriceQueryDubboService goodsPriceQueryDubboService;

    @Override
    @LocalCache(group = "inventoryPriceInfo", expireAfterWrite = 1, maximumSize = 2000)
    public ProdSellPriceInfoDto getGoodsSellPrice(String productId, String branchCompanyId) {
        logger.info("start find GoodsSellPrice by productId:{}，branchCompanyId:{}", productId, branchCompanyId);
        if (StringUtils.isEmpty(productId) || StringUtils.isEmpty(branchCompanyId)) {
            return null;
        }
        Response<ProdSellPriceInfoDto> prdRep = goodsPriceQueryDubboService.getGoodsSellPrice(productId, branchCompanyId);
        logger.info("销售关系:{}", JSON.toJSONString(prdRep.getResultObject()));
        if (prdRep.isSuccess()) {
            return prdRep.getResultObject();
        } else {
            logger.warn("{}->Can't findGoodsSellPrice:{}--》{}", prdRep.getErrorMessage(), productId, branchCompanyId);
            return null;
        }
    }

    @Override
    public List<ProdSellPriceInfoDto> getGoodsSellPriceByProductIdsAndCompanyId(String branchCompanyId, List<String> productIds) {
        Response<List<ProdSellPriceInfoDto>> response = goodsPriceQueryDubboService.getGoodsSellPriceByProductIdsAndCompanyId(branchCompanyId, productIds);
        if (response.isSuccess()) {
            return response.getResultObject();
        }
        logger.info("批量查询销售关系失败:{}", response.getErrorMessage());
        return new ArrayList<>();
    }

    @Override
    /**
     * true 先采后销
     * false 先销后采
     */
    public boolean isPreHarvestProd(ProdSellPriceInfoDto pProdSellPriceInfoDto) {
        if (pProdSellPriceInfoDto != null && pProdSellPriceInfoDto.getPreHarvestPinStatus() == 0) {
            return false;
        }
        return true;
    }

}
