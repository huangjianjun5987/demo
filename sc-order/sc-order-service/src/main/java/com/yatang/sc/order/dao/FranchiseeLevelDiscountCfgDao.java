package com.yatang.sc.order.dao;

import com.yatang.sc.order.domain.FranchiseeLevelDiscountCfg;

import java.util.List;
import java.util.Map;

public interface FranchiseeLevelDiscountCfgDao {
    int deleteByPrimaryKey(Long id);

    int insert(FranchiseeLevelDiscountCfg record);

    int insertSelective(FranchiseeLevelDiscountCfg record);

    FranchiseeLevelDiscountCfg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FranchiseeLevelDiscountCfg record);

    int updateByPrimaryKey(FranchiseeLevelDiscountCfg record);

    List<FranchiseeLevelDiscountCfg> getFranchiseeLevelDiscountCfgByCondition(Map<String, Object> condition);

    FranchiseeLevelDiscountCfg getFranchiseeLevelDiscountCfgByLv(String fLevel);
}