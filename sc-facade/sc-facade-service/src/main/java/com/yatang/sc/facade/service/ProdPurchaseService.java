package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.ProPurchaseBatchChangeStatusPo;
import com.yatang.sc.facade.domain.ProdPurchaseExtPo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoPo;
import com.yatang.sc.facade.domain.ProdPurchaseListPo;
import com.yatang.sc.facade.domain.ProdPurchaseModifyParamPo;
import com.yatang.sc.facade.domain.ProdPurchasePriceAuditPo;
import com.yatang.sc.facade.domain.ProdPurchaseQueryParamPo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @描述: 商品采购价格Service接口定义
 * @类名: ProdPurchaseService
 * @作者: yangshuang
 * @创建时间: 2017/7/15 14:12
 * @版本: v1.0
 */
public interface ProdPurchaseService {

    /**
     * 通过供应商地点id和商品id查看 商品采信息是否存在 0表示不存在，1表示存在(id是用来处理更新的时候的判断)
     *
     * @param id 主键id
     * @param spAdrId 供应商地点信息id
     * @param productId 商品id
     * @return
     */
    int getPurchasePriceCount(Long id, String spAdrId, String productId);
    /**
     * 新增商品采购信息
     * @param prodPurchaseInfoPo
     * @author yangshuang
     * @return
     */
    boolean addProdPurchase(ProdPurchaseInfoPo prodPurchaseInfoPo);
    /**
     * 根据 id删除商品采购关系(伪删除)
     * @param
     * @param prodPurchaseModifyParamPo
     * @return
     */
    boolean deleteProdPurchaseById(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo);
    /**
     * 根据采购关系主键查询商品采购关系主键
     * @param id 采购关系主键
     * @return
     */
    ProdPurchaseExtPo getProdPurchaseById(Long id);



    /**
     * 根据主键查询采购关系信息
     * @param id 采购关系主键
     * @return
     */
    ProdPurchaseInfoPo selectByPrimaryId(Long id);








    /**
     * 更新商品价格信息更新
     * @param prodPurchaseInfoPo
     * @return
     */
    boolean updateProdPurchaseSelective(ProdPurchaseInfoPo prodPurchaseInfoPo);
    /**
     * 根据 id改变当前的供应商类型
     * @param id  采购关系主键
     * @param prodPurchaseModifyParamPo  供应商的类型(0：一般供应商,1:主供应商)(需要将其他的主供应商改为一般供应商状态)
     * @return
     */
    boolean changeSupplierType(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo);
    /**
     * 根据 id改变当前的商品采购关系的启禁用状态
     * @param prodPurchaseModifyParamPo  （采购关系主键 status  采购关系的状态:0,,失效,1启用)
     * @return
     */
    boolean changeProPurchaseStatus(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo);

    /**
     * 分页查询商品采购关系信息
     * @param purchasePriceQueryParamPo
     * @return
     */
    PageInfo<ProdPurchaseExtPo> queryProdPurchaseExtByCondition(ProdPurchaseQueryParamPo purchasePriceQueryParamPo);
    /**
     * 批量更新商品的采购关系的启停用(采购关系的状态:0,,失效,1启用)
     *
     * @param proPurchaseBatchChangeStatusPo
     * @return
     */
    boolean batchChangeProPurchaseStatus(ProPurchaseBatchChangeStatusPo proPurchaseBatchChangeStatusPo);
    /**
     * 检查是否有主供应商
     * @param productId  商品id
     * @param branchCompanyId 分公司编号
     *@param supplierType  供应商类型:0：一般供应商,1:主供应商  @return
     */
    boolean checkMainSupplier(String productId, String branchCompanyId, int supplierType);

    /**
     * 检查采购价是否被更改
     * @param id pk
     * @param purchasePrice 采购价格
     * @return
     */
    boolean checkPurchasePriceModifyById(Long id, BigDecimal purchasePrice);

    /**
     * 更新旧数据
     * @param purchaseInfoPo
     * @return
     */
    boolean updateProdPurchaseByPrimaryKeySelective(ProdPurchaseInfoPo purchaseInfoPo);

    /**
     * 根据商品ids批量查询采购关系 map(productId,List)
     *
     * @param productIds 商品id
     * @return
     */
    List<ProdPurchaseListPo> queryProdPurchaseListPoByProductIds(List<String> productIds);
    /**
     * 根据条件查询商品采购关系
     *
     * @param queryParamPo 查询条件(productId,spId,spAdrId)
     * @return
     */
    ProdPurchaseInfoPo getProdPurchaseByParam(ProdPurchaseQueryParamPo queryParamPo);



    /**
     *根据商品id,分公司id,是否启用,是否是主供应商查询商品信息()
     */
    List<ProdPurchaseInfoPo> queryProdPurchaseListByParam(ProdPurchaseQueryParamPo queryParamPo);

    /**
     * 为销售关系计算毛利率查询采购价格
     * @param productId 商品id
     * @param branchCompanyId 子公司id
     * @author yinyuxin
     * @return
     */
    BigDecimal queryPurchasePriceForProdSell(String productId,String branchCompanyId);


    /**
     * 根据采购关系id查询流程需要的采购关系
     *
     * @param id 根据id查询
     * @return
     */
    ProdPurchasePriceAuditPo getProdPurchasePriceAuditDtoById(Long id);
}
