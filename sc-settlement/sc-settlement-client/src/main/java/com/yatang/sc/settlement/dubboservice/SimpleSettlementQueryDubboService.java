package com.yatang.sc.settlement.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.settlement.dto.*;

import java.util.List;


/**
 * @描述: 简易结算dubbo服务接口
 * @作者: tankejia
 * @创建时间: 2017/8/31-9:25 .
 * @版本: 1.0 .
 */
public interface SimpleSettlementQueryDubboService {

    /**
     * @Description: 根据条件查询供应商结算列表
     * @author tankejia
     * @date 2017/8/31- 11:13
     * @param record
     */
    Response<List<SupplierSettledDto>> listSupplierSettlementByMultParam(SupplierSettlementMultQueryDto record);

}
