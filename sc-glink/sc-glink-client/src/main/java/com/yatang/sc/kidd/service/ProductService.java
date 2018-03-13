package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryDto;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryResponseDto;
import com.yatang.sc.kidd.dto.product.ProductSynchronizeDto;

public interface ProductService {

    /**
     * 商品信息的新增及修改
     *
     * @param productSynchronizeDto
     * @return
     */
    Response<String> synchronizeProduct(ProductSynchronizeDto productSynchronizeDto);

    /**
     * 库存查询
     *
     * @param kiddInventoryQueryDto
     * @return
     */
    Response<KiddInventoryQueryResponseDto> inventoryQuery(KiddInventoryQueryDto kiddInventoryQueryDto);

}
