package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProdPurchaseInfoLogPo;

public interface ProdPurchaseInfoLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProdPurchaseInfoLogPo record);

    int insertSelective(ProdPurchaseInfoLogPo record);

    ProdPurchaseInfoLogPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProdPurchaseInfoLogPo record);

    int updateByPrimaryKey(ProdPurchaseInfoLogPo record);
}