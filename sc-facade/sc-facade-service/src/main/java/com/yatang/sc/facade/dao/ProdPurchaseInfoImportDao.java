package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProdPurchaseInfoImportPo;
import com.yatang.sc.facade.domain.ProdPurchasePriceUpdateParamPo;

import java.util.List;

/**
 * @描述: 商品采购价导入记录DTO类
 * @类名: ProdPurchaseInfoImportDao
 * @作者: kangdong
 * @创建时间: 2017/12/6 09:30
 * @版本: v1.0
 */
public interface ProdPurchaseInfoImportDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProdPurchaseInfoImportPo record);

    int insertSelective(ProdPurchaseInfoImportPo record);

    ProdPurchaseInfoImportPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProdPurchaseInfoImportPo record);

    int updateByPrimaryKey(ProdPurchaseInfoImportPo record);


    List<ProdPurchaseInfoImportPo> getProdPurchaseInfoImportByParam(ProdPurchasePriceUpdateParamPo paramPo);

    /**
     * 批量插入
     * @param prodPurchaseInfoImportPo
     * @return
     */
    int batchInsertProdPurchaseInfoImport(List<ProdPurchaseInfoImportPo> prodPurchaseInfoImportPo);


    /**
     * 通过上传id查询商品售价导入记录
     * @param importsId
     * @return
     */
    List<ProdPurchaseInfoImportPo> queryByImportsId(Long importsId);

    /**
     * 根据主键更新处理结果:0:错误;1:已验证;2:已提交
     * @param record
     * @return
     */
    int updateHandleResult(ProdPurchaseInfoImportPo record);
}