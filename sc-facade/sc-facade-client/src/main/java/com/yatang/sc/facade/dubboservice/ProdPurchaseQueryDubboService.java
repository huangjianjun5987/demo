package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.prod.ProdPurchaseExtDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.dto.prod.ProdPurchasePriceAuditDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseQueryParamDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @描述: 商品采购的queryDubbo服务接口定义
 * @类名: ProdPurchaseQueryDubboService
 * @作者: yangshuang
 * @创建时间: 2017/7/15 14:06
 * @版本: v1.0
 */
public interface ProdPurchaseQueryDubboService {
    /**
     * 根据采购关系主键查询商品采购关系主键
     *
     * @param id 采购关系主键
     * @return
     */
    Response<ProdPurchaseExtDto> getProdPurchaseById(Long id);

    /**
     * 根据条件查询商品采购关系list
     *
     * @param purchasePriceQueryParamDto 查询条件
     * @return
     */
    Response<PageResult<ProdPurchaseExtDto>> queryProdPurchaseExtByCondition(ProdPurchaseQueryParamDto purchasePriceQueryParamDto);

    /**
     * 检查是否有主供应商
     *
     * @param productId       商品id
     * @param branchCompanyId 分公司编号
     * @param supplierType    供应商类型:0：一般供应商,1:主供应商  @return
     */
    Response<Void> checkMainSupplier(String productId, String branchCompanyId, int supplierType);


    /**
     * 根据商品ids批量查询采购关系 map(productId,List)
     *
     * @param productIds 商品idlist
     * @return
     */
    Response<Map<String, List<ProdPurchaseExtDto>>> queryProdPurchaseExtVoMapByProductIds(List<String> productIds);

    /**
     * 根据条件查询商品采购关系
     *
     * @param queryParamDto 查询条件(productId,spId,spAdrId)
     * @return
     */
    Response<ProdPurchaseInfoDto> getProdPurchaseByParam(ProdPurchaseQueryParamDto queryParamDto);


    /**
     * 根据商品id,分公司id,是否启用,是否是主供应商查询商品信息()
     *
     * @param queryParamDto
     * @return
     */
    Response<List<ProdPurchaseInfoDto>> queryProdPurchaseListByParam(ProdPurchaseQueryParamDto queryParamDto);

    /**
     * 根据子公司id 商品id 查询商品的采购价（优先取主供应商）
     *
     * @param productId
     * @param branchCompanyId
     * @return
     * @author yinyuxin
     */
    Response<BigDecimal> queryPurchasePriceForProdSell(String productId, String branchCompanyId);


    /**
     * 根据采购关系id查询流程需要的采购关系
     *
     * @param id
     * @return
     */
    Response<ProdPurchasePriceAuditDto> getProdPurchasePriceAuditDtoById(Long id);


}
