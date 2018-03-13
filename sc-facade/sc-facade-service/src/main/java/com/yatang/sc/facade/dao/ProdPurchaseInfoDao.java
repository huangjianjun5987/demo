package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProPurchaseBatchChangeStatusPo;
import com.yatang.sc.facade.domain.ProdPurchaseExtPo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoPo;
import com.yatang.sc.facade.domain.ProdPurchaseListPo;
import com.yatang.sc.facade.domain.ProdPurchaseModifyParamPo;
import com.yatang.sc.facade.domain.ProdPurchasePriceAuditPo;
import com.yatang.sc.facade.domain.ProdPurchaseQueryParamPo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProdPurchaseInfoDao {
    int deleteByPrimaryKey(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo);


    int insertSelective(ProdPurchaseInfoPo record);

    ProdPurchaseExtPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProdPurchaseInfoPo record);


    /**
     * 通过供应商地点id和商品id查看 商品采信息是否存在 0表示不存在，1表示存在(id为主键 更新时校验)
     *
     * @param id
     * @param spAdrId   供应商地点信息id
     * @param productId 商品id
     * @return
     */
    int getPurchasePriceCount(@Param(value = "id") Long id, @Param(value = "spAdrId") String spAdrId, @Param(value = "productId") String productId);


    /**
     * 根据 id改变非当前的供应商类型
     *
     * @param purchaseModifyParamPo 包裹内容：采购关系主键,productId,供应商的类型(0：一般供应商,1:主供应商)(需要将其他的主供应商改为一般供应商状态)
     * @param
     * @return
     */
    int changeOtherSupplierType(ProdPurchaseModifyParamPo purchaseModifyParamPo);

    /**
     * 根据 id改变当前的供应商类型
     *
     * @param prodPurchaseModifyParamPo 包裹内容 采购关系主键供应商的类型(0：一般供应商,1:主供应商)(需要将其他的主供应商改为一般供应商状态)
     * @return
     */
    int changeSupplierType(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo);

    /**
     * 根据 id改变当前的商品采购关系的启禁用状态
     *
     * @param prodPurchaseModifyParamPo 包含id 采购关系的状态:0,,失效,1启用
     * @return
     */
    int changeProPurchaseStatus(ProdPurchaseModifyParamPo prodPurchaseModifyParamPo);

    /**
     * 分页查询商品采购关系信息
     *
     * @param purchasePriceQueryParamPo
     * @return
     */
    List<ProdPurchaseExtPo> queryProdPurchaseExtByCondition(ProdPurchaseQueryParamPo purchasePriceQueryParamPo);

    /**
     * 批量更新商品的采购关系的启停用(采购关系的状态:0,,失效,1启用)
     *
     * @param proPurchaseBatchChangeStatusPo
     * @return
     */
    int batchChangeProPurchaseStatus(ProPurchaseBatchChangeStatusPo proPurchaseBatchChangeStatusPo);

    /**
     * 查看是当前的当前分公司下productId下否存在主供应商(1:存在,0：不存在
     *
     * @param productId
     * @param branchCompanyId 分公司编号
     * @param supplierType
     * @return
     */
    int getValidSupplierTypeCount(@Param(value = "productId") String productId, @Param(value = "branchCompanyId") String branchCompanyId, @Param(value = "supplierType") int supplierType);

    //检查当前的id对应的是否是有效数据
    int getValidCountById(Long id);

    /**
     * 检查采购价是否被更改
     *
     * @param id            pk
     * @param purchasePrice 采购价格
     * @return
     */
    int checkPurchasePriceModifyById(@Param(value = "id") Long id, @Param(value = "purchasePrice") BigDecimal purchasePrice);

    /**
     * 更新旧数据
     *
     * @param purchaseInfoPo
     * @return
     */
    int updateProdPurchaseByPrimaryKeySelective(ProdPurchaseInfoPo purchaseInfoPo);

    /**
     * 根据商品ids批量查询采购关系
     *
     * @param productIds 商品ids
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

    ProdPurchaseInfoPo selectByPrimaryId(Long id);

    List<ProdPurchaseInfoPo> queryProdPurchaseListByParam(ProdPurchaseQueryParamPo queryParamPo);

    /**
     * 根据销售关系业务中计算毛利率的需要 查询采购价格
     *
     * @param branchCompanyId 子公司id
     * @param productId       商品id
     * @return
     * @author yinyuxin
     */
    List<ProdPurchaseInfoPo> queryPurchasePriceForProdSell(@Param("branchCompanyId") String branchCompanyId, @Param("productId") String productId);


    /**
     * 根据采购关系id查询流程需要的采购关系
     *
     * @param id
     * @author yangshuang
     */
    ProdPurchasePriceAuditPo getProdPurchasePriceAuditDtoById(Long id);
}