package com.yatang.sc.facade.flow;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.ProductDetailDto;
import com.yatang.sc.facade.dto.ProductListForAppDto;
import com.yatang.sc.facade.dto.ProductListForAppQueryParamDto;

/**
 * @描述:查询商品详情页面（app）
 * @类名:ProductQueryFlowService
 * @作者: yangshuang
 * @创建时间: 2017/7/7 19:29
 * @版本: v1.0
 */
public interface ProductQueryFlowService {

    /**
     * 根据商品id和子公司id查询商品信息
     *
     * @param productCode 商品编号
     * @param branchCompanyId 子公司id
     * @author yangshuang
     * @return
     */
    Response<ProductDetailDto> getProductDetail(String productCode, String branchCompanyId,String deliveryWarehouseCode);
    
    /**
     * 查询商品列表（三级分类、商品名称、商品参数）
     * @param dto
     * @return
     */
    Response<PageResult<ProductListForAppDto>> queryProductList(ProductListForAppQueryParamDto dto);
}
