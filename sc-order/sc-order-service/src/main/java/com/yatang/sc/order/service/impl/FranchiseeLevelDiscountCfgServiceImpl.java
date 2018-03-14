package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.FranchiseeLevelDiscountCfgDao;
import com.yatang.sc.order.domain.FranchiseeLevelDiscountCfg;
import com.yatang.sc.order.service.FranchiseeLevelDiscountCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("franchiseeLevelDiscountCfgServiceImpl")
public class FranchiseeLevelDiscountCfgServiceImpl implements FranchiseeLevelDiscountCfgService {

    @Autowired
    private FranchiseeLevelDiscountCfgDao franchiseeLevelDiscountCfgDao;

    @Override
    public FranchiseeLevelDiscountCfg getFranchiseeLevelDiscountCfgById(Long id) {
        return franchiseeLevelDiscountCfgDao.selectByPrimaryKey(id);
    }

    @Override
    public List<FranchiseeLevelDiscountCfg> getFranchiseeLevelDiscountCfgByCondition(Map<String, Object> condition) {
        return franchiseeLevelDiscountCfgDao.getFranchiseeLevelDiscountCfgByCondition(condition);
    }

    @Override
    public FranchiseeLevelDiscountCfg getFranchiseeLevelDiscountCfgByLv(String fLevel) {
        return franchiseeLevelDiscountCfgDao.getFranchiseeLevelDiscountCfgByLv(fLevel);
    }
}
