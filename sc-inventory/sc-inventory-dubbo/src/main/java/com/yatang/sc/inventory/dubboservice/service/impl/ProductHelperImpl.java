package com.yatang.sc.inventory.dubboservice.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.inventory.dubboservice.service.ProductHelper;
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
    ProductScIndexDubboService productScIndexDubboService;

    @Override
    @LocalCache(group = "productCacheById", expireAfterWrite = 10, maximumSize = 500)
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

    @Override
    @LocalCache(group = "productIndex", maximumSize = 1000,expireAfterWrite = 10)
    public ProductIndexDto findProductIndexByCode(String productCode) {
        logger.info("start find product by productCode:" + productCode);
        if (StringUtils.isEmpty(productCode)) {
            return null;
        }
        Response<ProductIndexDto> productIndexDtoResponse = productScIndexDubboService.queryByProductCode(productCode);
        if (productIndexDtoResponse == null) {
            logger.info("response is null, can't find product by productCode:" + productCode);
            return null;
        }
        if (productIndexDtoResponse.isSuccess()) {
            return productIndexDtoResponse.getResultObject();
        } else {
            logger.info(productIndexDtoResponse.getErrorMessage() + ",can't find productCode by productId:" + productCode);
            return null;
        }
    }

}
