package com.yatang.sc.service;

import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;

/**
 * Created by qiugang on 7/12/2017.
 */
public interface ProductHelper {

    public ProductDto findProductById(String productId);

    ProductIndexDto findProductIndexById(String productId);

    ProductIndexDto findProductIndexByCode(String productCode);
}
