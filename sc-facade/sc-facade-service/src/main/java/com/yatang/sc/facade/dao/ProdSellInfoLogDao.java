package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.ProdSellInfoLogPo;

public interface ProdSellInfoLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProdSellInfoLogPo record);

    int insertSelective(ProdSellInfoLogPo record);

    ProdSellInfoLogPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProdSellInfoLogPo record);

    int updateByPrimaryKey(ProdSellInfoLogPo record);
}