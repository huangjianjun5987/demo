package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ProductDetailDto;
import com.yatang.sc.facade.dto.ProductListForAppDto;
import com.yatang.sc.facade.dto.ProductListForAppQueryParamDto;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/7 19:44
 * @版本: v1.0
 */
public interface ProductQueryDubboService {

    /**
     * 根据商品id和区域编码查询商品信息
     *
     * @param productCode 商品编号
     * @param branchCompanyId 区域编号
     * @author yangshuang
     * @return
     */
    Response<ProductDetailDto> getProductDetail(String productCode, String branchCompanyId,String deliveryWarehouseCode);
    
    /**
     * 查询商品列表（三级分类、商品名称、商品参数）
     * 
     * @param dto
     * @author yinyuxin
     * @return
     */
    Response<PageResult<ProductListForAppDto>> queryProductList(ProductListForAppQueryParamDto dto);

    /**
     * 手动同步商品
     * @param productId
     * @return
     */
    Response<Boolean> syncProduct(String productId);
}
