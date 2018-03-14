package com.yatang.sc.service;

import com.yatang.sc.common.localcache.annotation.LocalCache;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;

/**
 * Created by qiugang on 7/12/2017.
 */
public interface ProductHelper {

    public ProductDto findProductById(String productId);

    public boolean isCouponProduct(String productType);

    @LocalCache(group = "productCacheById", expireAfterWrite = 5, maximumSize = 500)
    ProductIndexDto findProductIndexById(String productId);
}
