package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProdSellInfoImportPo;

import java.util.List;

import com.yatang.sc.facade.domain.ProdSellPriceUpdateParamPo;

/**
 * @描述: 商品售价导入记录DTO类
 * @类名: ProdSellInfoImportDao
 * @作者: kangdong
 * @创建时间: 2017/12/6 13:44
 * @版本: v1.0
 */
public interface ProdSellInfoImportDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProdSellInfoImportPo record);

    int insertSelective(ProdSellInfoImportPo record);

    ProdSellInfoImportPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProdSellInfoImportPo record);

    int updateByPrimaryKey(ProdSellInfoImportPo record);

    /**
     * 批量插入
     * @param imports
     * @return
     */
    Boolean batchInsertProdSellInfoImport(List<ProdSellInfoImportPo> imports);

    List<ProdSellInfoImportPo> getProdSellInfoImportByParam(ProdSellPriceUpdateParamPo paramPo);

    List<ProdSellInfoImportPo> queryByImportsId(Long importsId);

    /**
     * 根据主键更新处理结果:0:错误;1:已验证;2:已提交
     * @param record
     * @return
     */
    int updateHandleResult(ProdSellInfoImportPo record);
}