package com.yatang.sc.order.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.FranchiseeConditionDto;
import com.yatang.sc.dto.FranchiseePaymentDto;
import com.yatang.sc.dto.FranchiseeSettlementDto;


/**
 * @描述: 简易结算dubbo服务接口
 * @作者: tankejia
 * @创建时间: 2017/8/31-9:25 .
 * @版本: 1.0 .
 */
public interface FranchiseeSimpleQueryDubboService {


    /**
     * 查询加盟商结算
     * @param conditionDto
     * @return
     */
    Response<PageResult<FranchiseeSettlementDto>> queryFranchiseeSettlement(FranchiseeConditionDto conditionDto);

    /**
     * 查询加盟商支付
     * @param conditionDto
     * @return
     */
    Response<PageResult<FranchiseePaymentDto>> queryFranchiseePayment(FranchiseeConditionDto conditionDto);


}
