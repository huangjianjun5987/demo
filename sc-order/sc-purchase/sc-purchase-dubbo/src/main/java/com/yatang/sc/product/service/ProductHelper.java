package com.yatang.sc.product.service;

import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;

import java.util.List;
import java.util.Map;

/**
 * Created by qiugang on 7/12/2017.
 */
public interface ProductHelper {

    public ProductIndexDto findProductById(String productId);

    public Map<String, ProductIndexDto> queryProductByIds(List<String> productIds);

    public boolean isCouponProduct(String productType);

    public boolean isBundleProduct(String productType);

    ProductIndexDto findProductByCode(String productCode);

    Map<String,ProductIndexDto> findProductByInternationalCodes(List<String> internationalCodes);

    /**
     * 是否供应商直送商品
     * @return
     */
    boolean isProviderProduct(String productId,String storeId);

}
