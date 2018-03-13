package com.yatang.sc.facade.service.impl;

import com.yatang.sc.facade.dao.ProcessDefinitionDao;
import com.yatang.sc.facade.domain.pm.ProcessAuditLogPo;
import com.yatang.sc.facade.domain.pm.ProcessDefinitionPo;
import com.yatang.sc.facade.service.ProcessAuditLogService;
import com.yatang.sc.facade.service.ProcessDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/10/25 15:02
 * @version: v1.0
 */
@Transactional
@Service("processDefinitionService")
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService{

	@Autowired
	private ProcessDefinitionDao processDefinitionDao;
	@Autowired
	private ProcessAuditLogService processAuditLogService;

	@Override
	public List<ProcessDefinitionPo> queryBySelective(ProcessDefinitionPo processDefinitionPo) {
		List<ProcessDefinitionPo> processDefinitionPos=new ArrayList<>();
		processDefinitionPo.setIsFirstNode(0);
		List<ProcessDefinitionPo> firstProcess = processDefinitionDao.queryBySelective(processDefinitionPo);
		if (firstProcess!=null && firstProcess.size()>0){
			ProcessDefinitionPo processDefinitionPo1=firstProcess.get(0);
			//查询当前节点的审批日志
			ProcessAuditLogPo processAuditLogPo=new ProcessAuditLogPo();
			processAuditLogPo.setProcessId(processDefinitionPo1.getId());
			processAuditLogPo.setBusinessId(processDefinitionPo.getBusinessId());
			List<ProcessAuditLogPo> processAuditLogPos = processAuditLogService
					.queryByBusIdAndProcessId(processAuditLogPo);
			if (processAuditLogPos!=null && processAuditLogPos.size()>0){
				processDefinitionPo1.setProcessAuditLog(processAuditLogPos.get(0));
			}

			processDefinitionPos.add(processDefinitionPo1);
			//查下一节点审批流程
			while (null!=processDefinitionPo1.getNextNodeId()){
				processDefinitionPo1=processDefinitionDao.selectByPrimaryKey(processDefinitionPo1.getNextNodeId());

				//查询当前节点的审批日志
				processAuditLogPo.setProcessId(processDefinitionPo1.getId());
				processAuditLogPos = processAuditLogService.queryByBusIdAndProcessId(processAuditLogPo);
				if (processAuditLogPos!=null && processAuditLogPos.size()>0){
					processDefinitionPo1.setProcessAuditLog(processAuditLogPos.get(0));
				}
				processDefinitionPos.add(processDefinitionPo1);
			}
		}
		return processDefinitionPos;
	}


}
