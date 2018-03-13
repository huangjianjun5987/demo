package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.pm.ProcessAuditLogParamPo;
import com.yatang.sc.facade.domain.pm.ProcessDefinitionPo;
import com.yatang.sc.facade.dto.pm.ProcessAuditLogParamDto;
import com.yatang.sc.facade.dto.pm.ProcessDefinitionDto;
import com.yatang.sc.facade.dubboservice.ProcessDefinitionQueryDubboService;
import com.yatang.sc.facade.service.ProcessAuditLogService;
import com.yatang.sc.facade.service.ProcessDefinitionService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/10/25 15:51
 * @version: v1.0
 */
@Service("processDefinitionQueryDubboService")
public class ProcessDefinitionQueryDubboServiceImpl implements ProcessDefinitionQueryDubboService {

	@Autowired
	private ProcessDefinitionService processDefinitionService;
	@Autowired
	private ProcessAuditLogService   processAuditLogService;

	private static final Logger log = LoggerFactory.getLogger(ProcessDefinitionQueryDubboServiceImpl.class);



	@Override
	public Response<List<ProcessDefinitionDto>> queryProcessDefinitions(ProcessDefinitionDto processDefinitionDto) {
		log.info("-----------ProcessDefinitionQueryDubboServiceImpl-->queryProcessDefinitions():param{}" + JSONObject
				.toJSONString(processDefinitionDto) + "-----------");
		Response<List<ProcessDefinitionDto>> response = new Response<>();
		try {
			List<ProcessDefinitionPo> processDefinitionPos = processDefinitionService
					.queryBySelective(BeanConvertUtils.convert(processDefinitionDto, ProcessDefinitionPo.class));
			response.setResultObject(BeanConvertUtils.convertList(processDefinitionPos, ProcessDefinitionDto.class));
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
		} catch (Exception e) {
			log.info("-----------ProcessDefinitionQueryDubboServiceImpl-->queryProcessDefinitions():error{}"
					+ ExceptionUtils.getFullStackTrace(e) + "-----------");
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
		}
		return response;
	}



	@Override
	public Response<Long> getNextProcessNodeCode(ProcessAuditLogParamDto processAuditLogParamDto) {
		if (log.isInfoEnabled()) {
			log.info("----- 接口名>> 接口名(): param=" + JSONObject.toJSONString(processAuditLogParamDto) + "-----");
		}
		Response<Long> response = new Response<Long>();
		try {
			Long nextProcessNodeCode=processAuditLogService.insertProcessLog(BeanConvertUtils.convert(processAuditLogParamDto,
					ProcessAuditLogParamPo.class));
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(nextProcessNodeCode);
		} catch (Exception e) {
			log.error("-----接口名>> 接口名(),error=" + ExceptionUtils.getFullStackTrace(e) + "----------");
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;

	}
}
