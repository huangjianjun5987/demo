package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProdSellInfoImportsPo;

/**
 * @描述: 商品售价导入表(调价单)DTO类
 * @类名: ProdSellInfoImportsDao
 * @作者: kangdong
 * @创建时间: 2017/12/6 14:49
 * @版本: v1.0
 */
public interface ProdSellInfoImportsDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProdSellInfoImportsPo record);

    int insertSelective(ProdSellInfoImportsPo record);

    ProdSellInfoImportsPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProdSellInfoImportsPo record);

    int updateByPrimaryKey(ProdSellInfoImportsPo record);
}