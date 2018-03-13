package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProdPurchaseInfoImportsPo;

/**
 * @描述: 商品采购价导入表(调价单)DTO类
 * @类名: ProdPurchaseInfoImportsDao
 * @作者: kangdong
 * @创建时间: 2017/12/6 14:55
 * @版本: v1.0
 */
public interface ProdPurchaseInfoImportsDao {

    int deleteByPrimaryKey(Long id);

    int insert(ProdPurchaseInfoImportsPo record);

    int insertSelective(ProdPurchaseInfoImportsPo record);

    ProdPurchaseInfoImportsPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProdPurchaseInfoImportsPo record);

    int updateByPrimaryKey(ProdPurchaseInfoImportsPo record);
}