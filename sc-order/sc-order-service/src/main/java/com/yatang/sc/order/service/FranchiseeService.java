package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.FranchiseePayment;
import com.yatang.sc.order.domain.FranchiseeSettlement;

import java.util.List;
import java.util.Map;

/**
 * Created by xiangyonghong on 2017/9/4.
 */
public interface FranchiseeService {

    /**
     * 查询满足条件的加盟商结算记录总数
     * @param map
     * @return
     */
    long queryFranchiseeSettlementCount(Map<String, Object> map);

    /**
     * 分页查询满足条件的加盟商结算数据
     * @param map
     * @return
     */
    List<FranchiseeSettlement> queryFranchiseeSettlementList(Map<String, Object> map);

    /**
     * 查询满足条件的加盟商支付记录总数
     * @param map
     * @return
     */
    long queryFranchiseePayementCount(Map<String, Object> map);

    /**
     * 查询满足条件的加盟商支付记录总数
     * @param map
     * @return
     */
    List<FranchiseePayment> queryFranchiseePaymentList(Map<String, Object> map);
}
