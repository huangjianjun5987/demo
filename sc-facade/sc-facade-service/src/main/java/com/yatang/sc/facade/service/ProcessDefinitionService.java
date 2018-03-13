package com.yatang.sc.facade.service;

import com.yatang.sc.facade.domain.pm.ProcessDefinitionPo;

import java.util.List;

/**
 * @description: 采购退货单审批流程service
 * @author: yinyuxin
 * @date: 2017/10/25 14:56
 * @version: v1.0
 */
public interface ProcessDefinitionService {

	/**
	 * 全条件查询审批流程记录
	 * @param processDefinitionPo
	 * @return
	 */
	List<ProcessDefinitionPo> queryBySelective(ProcessDefinitionPo processDefinitionPo);
}
