package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.pm.ProcessAuditLogDto;

import java.util.List;


/**
 * @描述: 流程审批日志dubbo
 * @作者: tankejia
 * @创建时间: 2017/10/25-14:41 .
 * @版本: 1.0 .
 */
public interface ProcessAuditLogQueryDubboService {

    /**
     * @Description: 根据业务单号查询详情
     * @author tankejia
     * @date 2017/10/25- 13:53
     * @param businessId
     */
    Response<List<ProcessAuditLogDto>> queryApprovalInfo(String businessId);
}
