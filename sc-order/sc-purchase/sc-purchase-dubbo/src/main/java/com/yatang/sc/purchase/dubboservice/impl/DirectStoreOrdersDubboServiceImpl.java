package com.yatang.sc.purchase.dubboservice.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;
import com.yatang.sc.purchase.dubboservice.DirectStoreOrdersDubboService;
import com.yatang.sc.purchase.service.ItemPricingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("directStoreOrdersDubboService")
public class DirectStoreOrdersDubboServiceImpl implements DirectStoreOrdersDubboService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ItemPricingEngine itemPricingEngine;

    public Response<Double> getPrice(String productId, String branchCompanyId, long quantity) {
        Response<Double> doubleResponse = new Response<>();
        try {

            ProdSellPriceInfoDto sellPrice = itemPricingEngine.getProductPriceInfo(productId, branchCompanyId);
            Double price = 0.0;
            if(sellPrice == null ||  sellPrice.getStatus() == null || sellPrice.getStatus() ==0) {
                doubleResponse.setSuccess(false);
                doubleResponse.setCode(CommonsEnum.RESPONSE_10044.getCode());
                doubleResponse.setErrorMessage(CommonsEnum.RESPONSE_10044.getName());
                doubleResponse.setResultObject(price);
                return doubleResponse;
            }
            for(ProdSellSectionPriceDto rangePrice : sellPrice.getSellSectionPrices()){
                if(quantity >= rangePrice.getStartNumber() && (rangePrice.getEndNumber() == null || quantity <= rangePrice.getEndNumber())){
                    price = rangePrice.getPrice().doubleValue();
                }
            }
            if(0 == price){
                price = sellPrice.getLowestPrice().doubleValue();
            }
            doubleResponse.setSuccess(true);
            doubleResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
            doubleResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            doubleResponse.setResultObject(price);
        } catch (Exception e) {
            logger.error("查询价格失败:{}",e.getMessage());
            e.printStackTrace();
            doubleResponse.setSuccess(false);
            doubleResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            doubleResponse.setErrorMessage("查询价格失败");
            doubleResponse.setResultObject(null);
        }
        return doubleResponse;
    }
}
