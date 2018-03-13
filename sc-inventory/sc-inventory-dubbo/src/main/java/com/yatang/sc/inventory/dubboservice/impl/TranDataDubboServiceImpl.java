package com.yatang.sc.inventory.dubboservice.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.common.CommonsEnum;
import com.yatang.sc.inventory.dto.TranDataDto;
import com.yatang.sc.inventory.dubboservice.TranDataDubboService;
import com.yatang.sc.inventory.dubboservice.flow.TranDataDubboFlow;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
@Service("tranDataDubboService")
public class TranDataDubboServiceImpl implements TranDataDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TranDataDubboFlow tranDataDubboFlow;


    @Override
    public Response<Boolean> addTranData(TranDataDto tranDataDto) {
        Response<Boolean> response = new Response<Boolean>();
        try{
            tranDataDubboFlow.addTranData(tranDataDto);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(true);
        }catch (Exception e){
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
            throw  new RuntimeException(e.getMessage());
        }
        return response;
    }


	@Override
	public Response<Boolean> addTranDataList(List<TranDataDto> tranDataDtoList) {
		Response<Boolean> response = new Response<Boolean>();
        try{
            tranDataDubboFlow.addTranDataList(tranDataDtoList);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(true);
        }catch (Exception e){
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
            throw  new RuntimeException(e.getMessage());
        }
        return response;
	}


}
