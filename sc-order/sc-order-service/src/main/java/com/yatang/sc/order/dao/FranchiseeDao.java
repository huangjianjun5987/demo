package com.yatang.sc.order.dao;


import com.yatang.sc.order.domain.FranchiseePayment;
import com.yatang.sc.order.domain.FranchiseeSettlement;

import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/9/4.
 */
public interface FranchiseeDao {

    long queryFranchiseeSettlementCount(Map<String, Object> map);

    List<FranchiseeSettlement> queryFranchiseeSettlementList(Map<String, Object> map);

    long queryFranchiseePayementCount(Map<String, Object> map);

    List<FranchiseePayment> queryFranchiseePaymentList(Map<String, Object> map);
}
