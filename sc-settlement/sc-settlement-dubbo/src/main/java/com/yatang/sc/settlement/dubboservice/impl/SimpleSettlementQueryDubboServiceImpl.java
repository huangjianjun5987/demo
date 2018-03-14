package com.yatang.sc.settlement.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.settlement.common.CommonsEnum;
import com.yatang.sc.settlement.domain.SupplierSettledPo;
import com.yatang.sc.settlement.domain.SupplierSettlementMultQueryPo;
import com.yatang.sc.settlement.dto.*;
import com.yatang.sc.settlement.dubboservice.SimpleSettlementQueryDubboService;
import com.yatang.sc.settlement.service.SupplierSettledService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("simpleSettlementQueryDubboService")
public class SimpleSettlementQueryDubboServiceImpl implements SimpleSettlementQueryDubboService {
    @Autowired
    private SupplierSettledService settledService;

    @Override
    public Response<List<SupplierSettledDto>> listSupplierSettlementByMultParam(SupplierSettlementMultQueryDto record) {
        Response<List<SupplierSettledDto>> response = new Response();
        if (log.isInfoEnabled()) {
            log.info("---------- <<根据条件查询供应商结算列表>> listSupplierSettlementByMultParam(SupplierSettlementMultQueryDto dto): dto="
                    + JSON.toJSONString(record)  + "----------");
        }

        try {
            List<SupplierSettledPo> list = settledService.listSupplierSettlementByMultParam(BeanConvertUtils.convert(record, SupplierSettlementMultQueryPo.class));
            if (list != null) {
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
                response.setResultObject(BeanConvertUtils.convertList(list, SupplierSettledDto.class));
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setResultObject(null);
                response.setSuccess(true);
            }

        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
