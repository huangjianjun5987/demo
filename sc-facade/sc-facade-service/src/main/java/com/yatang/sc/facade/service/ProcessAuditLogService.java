package com.yatang.sc.facade.service;

import com.yatang.sc.facade.domain.pm.ProcessAuditLogParamPo;
import com.yatang.sc.facade.domain.pm.ProcessAuditLogPo;

import java.util.List;

/**
 * @描述: 流程审批日志service
 * @作者: tankejia
 * @创建时间: 2017/10/25-14:06 .
 * @版本: 1.0 .
 */
public interface ProcessAuditLogService {

    /**
     * @Description: 根据业务单号查询详情
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

    /**
     * 获取下一审批流程节点编码（审批操作）
     * @param processAuditLogParamPo
     * @return 下一节点id  null表示审批结束
     */
    Long insertProcessLog(ProcessAuditLogParamPo processAuditLogParamPo);
}
