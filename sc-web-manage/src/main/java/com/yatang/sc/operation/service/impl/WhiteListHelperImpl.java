package com.yatang.sc.operation.service.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.operation.service.WhiteListHelper;
import com.yatang.sc.operation.vo.ParamScPurchaseVo;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.sc.dto.ParamScPurchaseDto;
import com.yatang.xc.mbd.biz.org.sc.dto.QueryStoreScPurchaseDto;
import com.yatang.xc.mbd.biz.org.sc.dto.StorePurcaseResultDto;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WhiteListHelperImpl implements WhiteListHelper {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    OrganizationSCService organizationSCService;

    @Autowired
    WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    @Override
    public Response<List<StorePurcaseResultDto>> whiteListBatchImport(List<ParamScPurchaseVo> paramScPurchaseVoList) {
        Response<List<StorePurcaseResultDto>> rep = new Response<>();
        List<QueryStoreScPurchaseDto> queryStoreScPurchaseDtoList = new ArrayList<>();
        for (ParamScPurchaseVo paramScPurchaseVo : paramScPurchaseVoList) {
            QueryStoreScPurchaseDto queryStoreScPurchaseDto = new QueryStoreScPurchaseDto();
            queryStoreScPurchaseDto.setStoreId(paramScPurchaseVo.getStoreId());
            queryStoreScPurchaseDtoList.add(queryStoreScPurchaseDto);
            Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(paramScPurchaseVo.getDeliveryWarehouseCode());
            if (logicWarehouseDtoResponse == null || !logicWarehouseDtoResponse.isSuccess() || logicWarehouseDtoResponse.getResultObject() == null) {
                rep.setSuccess(false);
                rep.setCode(CommonsEnum.RESPONSE_20007.getCode());
                rep.setErrorMessage(CommonsEnum.RESPONSE_20007.getName()+",仓库编码:"+paramScPurchaseVo.getDeliveryWarehouseCode());
                return rep;
            }
        }
        List<ParamScPurchaseDto> paramScPurchaseDtoList = BeanConvertUtils.convertList(paramScPurchaseVoList, ParamScPurchaseDto.class);
        Response<List<StorePurcaseResultDto>> response = organizationSCService.onOfflineStorePurchaseImport(paramScPurchaseDtoList);
        if (!response.isSuccess() || CollectionUtils.isNotEmpty(response.getResultObject())) {
            rep.setSuccess(false);
            rep.setCode(CommonsEnum.RESPONSE_10024.getCode());
            rep.setResultObject(response.getResultObject());
        } else {
            rep.setSuccess(response.isSuccess());
        }
        return rep;
    }
}
