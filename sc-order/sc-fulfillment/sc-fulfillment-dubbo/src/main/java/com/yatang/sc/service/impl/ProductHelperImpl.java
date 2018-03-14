package com.yatang.sc.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.service.ProductHelper;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qiugang on 7/12/2017.
 */

@Service("productHelper")
public class ProductHelperImpl implements ProductHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String PRODUCT_TYPE_COUPON = "7";

    @Autowired
    ProductDubboService productDubboService;

    @Autowired
    ProductScIndexDubboService productScIndexDubboService;

    @Override
    @LocalCache(group = "purchase", expireAfterWrite = 5, maximumSize = 100)
    public ProductDto findProductById(String productId) {
        logger.info("start find product by id:" + productId);
        if (StringUtils.isEmpty(productId)) {
            return null;
        }
        Response<ProductDto> response = productDubboService.queryById(productId);
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


    public boolean isCouponProduct(String productType){

        return PRODUCT_TYPE_COUPON.equals(productType);
    }

    @Override
    @LocalCache(group = "productCacheById", expireAfterWrite = 5, maximumSize = 500)
    public ProductIndexDto findProductIndexById(String productId) {
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

}
