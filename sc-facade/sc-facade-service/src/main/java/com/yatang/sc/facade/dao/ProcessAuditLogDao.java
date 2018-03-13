package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.pm.ProcessAuditLogPo;

import java.util.List;

public interface ProcessAuditLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(ProcessAuditLogPo record);

    int insertSelective(ProcessAuditLogPo record);

    ProcessAuditLogPo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProcessAuditLogPo record);

    int updateByPrimaryKey(ProcessAuditLogPo record);

    /**
     * @Description: 根据业务单号查询流程审批日志
     * @author tankejia
     * @date 2017/10/25- 13:53
     * @param businessId
     */
    List<ProcessAuditLogPo> selectByBusinessId(String businessId);

    /**
     * @Description: 根据审批流程节点id 和 业务单id查询审批日志
     * @author yinyuxin
     * @date 2017/11/2- 16:24
     * @param param
     */
    List<ProcessAuditLogPo> queryByBusIdAndProcessId(ProcessAuditLogPo param);
}