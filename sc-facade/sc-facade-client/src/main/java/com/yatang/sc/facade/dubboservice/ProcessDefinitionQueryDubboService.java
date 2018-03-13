package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.pm.ProcessAuditLogParamDto;
import com.yatang.sc.facade.dto.pm.ProcessDefinitionDto;

import java.util.List;

/**
 * @description: 审批流程查询相关接口service
 * @author: yinyuxin
 * @date: 2017/10/25 15:48
 * @version: v1.0
 */
public interface ProcessDefinitionQueryDubboService {

	/**
	 * 查询审批流程
	 * @param processDefinitionDto
	 * @return
	 */
	Response<List<ProcessDefinitionDto>> queryProcessDefinitions(ProcessDefinitionDto processDefinitionDto);

	/**
	 * 获取下一审批流程节点编码（审批操作）
	 * @param processAuditLogParamDto
	 * @return 下一节点id  null表示审批结束
	 */
	Response<Long> getNextProcessNodeCode(ProcessAuditLogParamDto processAuditLogParamDto);

}
