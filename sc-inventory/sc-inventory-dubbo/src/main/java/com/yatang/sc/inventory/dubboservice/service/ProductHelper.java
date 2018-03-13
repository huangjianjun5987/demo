package com.yatang.sc.inventory.dubboservice.service;

import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;

/**
 * Created by qiugang on 7/12/2017.
 */
public interface ProductHelper {

    ProductIndexDto findProductIndexById(String productId);

    ProductIndexDto findProductIndexByCode(String productCode);
}
