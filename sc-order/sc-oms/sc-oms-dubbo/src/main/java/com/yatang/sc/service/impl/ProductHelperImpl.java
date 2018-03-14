package com.yatang.sc.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.sc.service.ProductHelper;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by qiugang on 7/12/2017.
 */

@Log
@Service("productHelper")
public class ProductHelperImpl implements ProductHelper {

    @Autowired
    ProductDubboService productDubboService;

    @Autowired
    private ProductScIndexDubboService productScIndexDubboService;

    @Override
    @LocalCache(group = "product", maximumSize = 1000,expireAfterWrite = 20)
    public ProductDto findProductById(String productId) {
        log.info("start find product by id:" + productId);
        if(StringUtils.isEmpty(productId)){
            return null;
        }
        Response<ProductDto> response = productDubboService.queryById(productId);
        if(response == null){
            log.warning("response is null, can't find product:" + productId);
            return null;
        }
        if(response.isSuccess()){
            return response.getResultObject();
        }else{
            log.warning(response.getErrorMessage() + ",can't find product:" + productId);
            return null;
        }

    }


    @Override
    @LocalCache(group = "productIndex", maximumSize = 1000,expireAfterWrite = 20)
    public ProductIndexDto findProductIndexById(String productId) {
        log.info("start find product by productId:" + productId);
        if (StringUtils.isEmpty(productId)) {
            return null;
        }
        Response<ProductIndexDto> productIndexDtoResponse = productScIndexDubboService.queryByProductId(productId);
        if (productIndexDtoResponse == null) {
            log.info("response is null, can't find product by productId:" + productId);
            return null;
        }
        if (productIndexDtoResponse.isSuccess()) {
            return productIndexDtoResponse.getResultObject();
        } else {
            log.info(productIndexDtoResponse.getErrorMessage() + ",can't find product by productId:" + productId);
            return null;
        }

    }

    @Override
    @LocalCache(group = "productIndex", maximumSize = 1000,expireAfterWrite = 20)
    public ProductIndexDto findProductIndexByCode(String productCode) {
        log.info("start find product by productCode:" + productCode);
        if (StringUtils.isEmpty(productCode)) {
            return null;
        }
        Response<ProductIndexDto> productIndexDtoResponse = productScIndexDubboService.queryByProductCode(productCode);
        if (productIndexDtoResponse == null) {
            log.info("response is null, can't find product by productCode:" + productCode);
            return null;
        }
        if (productIndexDtoResponse.isSuccess()) {
            return productIndexDtoResponse.getResultObject();
        } else {
            log.info(productIndexDtoResponse.getErrorMessage() + ",can't find productCode by productId:" + productCode);
            return null;
        }
    }
}
