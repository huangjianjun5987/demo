package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.FranchiseeDao;
import com.yatang.sc.order.domain.FranchiseePayment;
import com.yatang.sc.order.domain.FranchiseeSettlement;
import com.yatang.sc.order.service.FranchiseeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/9/4.
 */
@Service("franchiseeService")
public class FranchiseeServiceImpl implements FranchiseeService {

    @Autowired
    private FranchiseeDao franchiseeDao;

    @Override
    public long queryFranchiseeSettlementCount(Map<String, Object> map) {
        return franchiseeDao.queryFranchiseeSettlementCount(map);
    }

    @Override
    public List<FranchiseeSettlement> queryFranchiseeSettlementList(Map<String, Object> map) {
        return franchiseeDao.queryFranchiseeSettlementList(map);
    }

    @Override
    public long queryFranchiseePayementCount(Map<String, Object> map) {
        return franchiseeDao.queryFranchiseePayementCount(map);
    }

    @Override
    public List<FranchiseePayment> queryFranchiseePaymentList(Map<String, Object> map) {
        return franchiseeDao.queryFranchiseePaymentList(map);
    }
}
