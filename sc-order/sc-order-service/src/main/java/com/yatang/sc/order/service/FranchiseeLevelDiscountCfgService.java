package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.FranchiseeLevelDiscountCfg;

import java.util.List;
import java.util.Map;

public interface FranchiseeLevelDiscountCfgService {

    FranchiseeLevelDiscountCfg getFranchiseeLevelDiscountCfgById(Long id);

    List<FranchiseeLevelDiscountCfg> getFranchiseeLevelDiscountCfgByCondition(Map<String,Object> condition);

    FranchiseeLevelDiscountCfg getFranchiseeLevelDiscountCfgByLv(String lv);
}
