package com.yatang.sc.inventory.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.inventory.domain.CfAreaWarehouse;
import com.yatang.sc.inventory.dto.AreawarehouseDto;
import com.yatang.sc.inventory.dubboservice.AreaWarehouseQueryDubboService;
import com.yatang.sc.inventory.service.AreaWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("areaWarehouseQueryDubboService")
public class AreaWarehouseQueryDubboServiceImpl implements AreaWarehouseQueryDubboService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    AreaWarehouseService areaWarehouseService;

    @Override
    public Response<AreawarehouseDto> queryAreawarehouseByProvince(String province) {
        log.info("queryAreawarehouseByProvince,province:{}",province);
        Response<AreawarehouseDto> reponse = new Response<>();
        try{
            CfAreaWarehouse areaWarehouse = areaWarehouseService.queryAreawarehouseByProvince(province);
            if(areaWarehouse == null){
                log.info("queryAreawarehouseByProvince,result is null");
                reponse.setSuccess(false);
                reponse.setCode(CommonsEnum.RESPONSE_10006.getCode());
                reponse.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return reponse;
            }
            AreawarehouseDto areawarehouseDto = BeanConvertUtils.convert(areaWarehouse,AreawarehouseDto.class);
            reponse.setSuccess(true);
            reponse.setCode(CommonsEnum.RESPONSE_200.getCode());
            reponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            reponse.setResultObject(areawarehouseDto);
        }catch (Exception e){
            log.error("queryAreawarehouseByProvince:{}",e.getMessage(),e);
            reponse.setSuccess(false);
            reponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            reponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return reponse;
    }

}
