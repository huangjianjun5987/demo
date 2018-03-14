package com.yatang.sc.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.yatang.sc.common.localcache.LocalCacheContext;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.common.localcache.aop.LocalCacheAop;
import com.yatang.sc.common.staticvalue.ProductType;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceQueryDubboService;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by qiugang on 7/12/2017.
 */

@Service("productHelper")
public class ProductHelperImpl implements ProductHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProductDubboService productDubboService;

    @Autowired
    private ProductScIndexDubboService productScIndexDubboService;

    @Autowired
    private ProdPlaceQueryDubboService prodPlaceQueryDubboService;

    @Override
    @LocalCache(group = "productCacheById", expireAfterWrite = 5, maximumSize = 500)
    public ProductIndexDto findProductById(String productId) {
        logger.info("start find product by id:" + productId);
        if (StringUtils.isEmpty(productId)) {
            return null;
        }
        Response<ProductIndexDto> response = productScIndexDubboService.queryByProductId(productId);
        if (response == null) {
            logger.warn("response is null, can't find product:" + productId);
            return null;
        }
        if (response.isSuccess()) {
            return response.getResultObject();
        } else {
            logger.warn(response.getErrorMessage() + ",can't find product:" + productId);
            return null;
        }
    }


    public Map<String, ProductIndexDto> queryProductByIds(List<String> productIds) {
        if(productIds == null){
            return null;
        }
        Map<String, ProductIndexDto> result = new HashMap<String, ProductIndexDto>();
        List<String>  missProductIds = new ArrayList<String>();
        for(String productId : productIds){
            String key = LocalCacheAop.genKey(new String[]{productId});
            key = key.toUpperCase();
            ProductIndexDto val = LocalCacheContext.get("productCacheById", key,ProductIndexDto.class);
            if(val != null){
                result.put(productId, val);
            }else{
                missProductIds.add(productId);
            }
        }
        Response<Map<String, ProductIndexDto>> response = productScIndexDubboService.queryByProductIds(missProductIds);
        if (response != null && response.getResultObject() != null) {
            Map<String, ProductIndexDto> queryResult = response.getResultObject();
            result.putAll(queryResult);
            Set<Map.Entry<String, ProductIndexDto>> entries = queryResult.entrySet();
            for(Map.Entry<String, ProductIndexDto> entry : entries){
                String key = LocalCacheAop.genKey(new String[]{entry.getKey()});
                key = key.toUpperCase();
                LocalCacheContext.put("productCacheById", key, entry.getValue(), ProductIndexDto.class,100, 5, -1L, 500, 4);
            }

        }
        return result;
    }


    public boolean isCouponProduct(String productType){

        return ProductType.PRODUCT_TYPE_COUPON.equals(productType);
    }

    public boolean isBundleProduct(String productType){

        return ProductType.PRODUCT_TYPE_BUNDLE.equals(productType);
    }

    @Override
    @LocalCache(group = "productCacheByCode", expireAfterWrite = 5, maximumSize = 100)
    public ProductIndexDto findProductByCode(String productCode) {
        logger.info("start find product by code:" + productCode);
        if (StringUtils.isEmpty(productCode)) {
            return null;
        }
        Response<ProductIndexDto> productIndexDtoResponse = productScIndexDubboService.queryByProductCode(productCode);
        if (productIndexDtoResponse == null) {
            logger.warn("response is null, can't find product by code:" + productCode);
            return null;
        }
        if (productIndexDtoResponse.isSuccess()) {
            return productIndexDtoResponse.getResultObject();
        } else {
            logger.warn(productIndexDtoResponse.getErrorMessage() + ",can't find product by code:" + productCode);
            return null;
        }

    }

    @Override
    public Map<String,ProductIndexDto> findProductByInternationalCodes(List<String> internationalCodes) {

        logger.info("start find product by internationalCodes:" + JSON.toJSONString(internationalCodes));
        if (CollectionUtils.isEmpty(internationalCodes)) {
            return null;
        }
        Response<Map<String, ProductIndexDto>> productIndexDtoResponse = productScIndexDubboService.queryByInternationalCodes(internationalCodes);
        if (productIndexDtoResponse == null) {
            logger.warn("response is null, can't find product by internationalCodes:" + JSON.toJSONString(internationalCodes));
            return null;
        }
        if (productIndexDtoResponse.isSuccess()) {
            return productIndexDtoResponse.getResultObject();
        } else {
            logger.warn(productIndexDtoResponse.getErrorMessage() + ",can't find product by internationalCodes:" + JSON.toJSONString(internationalCodes));
            return null;
        }
    }

    @Override
    public boolean isProviderProduct(String productId, String storeId) {
        Response<List<ProdPlaceDto>> prodPlaceResponse = prodPlaceQueryDubboService.queryDirectDeliveryProduct(storeId, Lists.newArrayList(productId));
        if (prodPlaceResponse == null || !prodPlaceResponse.isSuccess() || CollectionUtils.isEmpty(prodPlaceResponse.getResultObject())) {
            return false;
        }
        if (prodPlaceResponse.getResultObject().get(0).getLogisticsModel() == 0) {//0:直送，1:配送
            return true;
        }
        return false;
    }

}
