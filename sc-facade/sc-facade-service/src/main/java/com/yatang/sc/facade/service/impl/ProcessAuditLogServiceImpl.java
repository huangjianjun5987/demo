package com.yatang.sc.facade.service.impl;

import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.dao.ProcessAuditLogDao;
import com.yatang.sc.facade.dao.ProcessDefinitionDao;
import com.yatang.sc.facade.domain.pm.ProcessAuditLogParamPo;
import com.yatang.sc.facade.domain.pm.ProcessAuditLogPo;
import com.yatang.sc.facade.domain.pm.ProcessDefinitionPo;
import com.yatang.sc.facade.service.ProcessAuditLogService;
import com.yatang.sc.facade.service.ProcessDefinitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessAuditLogServiceImpl implements ProcessAuditLogService {

    private final ProcessAuditLogDao processAuditLogDao;

    private final ProcessDefinitionDao processDefinitionDao;

    @Override
    public List<ProcessAuditLogPo> selectByBusinessId(String businessId) {
        return processAuditLogDao.selectByBusinessId(businessId);
    }



    @Override
    public List<ProcessAuditLogPo> queryByBusIdAndProcessId(ProcessAuditLogPo param) {
        return processAuditLogDao.queryByBusIdAndProcessId(param);
    }



    @Override
    public Long insertProcessLog(ProcessAuditLogParamPo processAuditLogParamPo) {
        //0:查询审批单
        ProcessDefinitionPo processDefinitionPo=processDefinitionDao.selectByPrimaryKey(Long.valueOf(processAuditLogParamPo.getProcessId()));
        if (processDefinitionPo==null){
            throw new RuntimeException("查询审批流程节点失败，操作终止");
        }
        ProcessAuditLogPo processAuditLogPo=BeanConvertUtils.convert(processAuditLogParamPo,ProcessAuditLogPo.class);
        processAuditLogPo.setProcessId(processDefinitionPo.getId());
        processAuditLogPo.setAuditTime(new Date());
        processAuditLogPo.setBusinessType(Integer.valueOf(processAuditLogParamPo.getType()+""));
        processAuditLogDao.insertSelective(processAuditLogPo);

        if (processAuditLogParamPo.getAuditResult()==1){
            //1:同意
            if (processDefinitionPo.getNextNodeId()!=null){
                return processDefinitionPo.getNextNodeId();
            }
        }
       return null;
    }
}
