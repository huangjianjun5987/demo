package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.pm.ProcessAuditLogPo;
import com.yatang.sc.facade.dto.pm.ProcessAuditLogDto;
import com.yatang.sc.facade.dubboservice.ProcessAuditLogQueryDubboService;
import com.yatang.sc.facade.service.ProcessAuditLogService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("processAuditLogQueryDubboService")
public class ProcessAuditLogQueryDubboServiceImpl implements ProcessAuditLogQueryDubboService {

    @Autowired
    private ProcessAuditLogService processAuditLogService;

    protected final Log log	= LogFactory.getLog(this.getClass());

    @Override
    public Response<List<ProcessAuditLogDto>> queryApprovalInfo(String businessId) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<查看退货单审批意见>> queryApprovalInfo(Long businessId): businessId=" + businessId + "----------");
        }
        Response<List<ProcessAuditLogDto>> response = new Response();
        try {
            List<ProcessAuditLogPo> po = processAuditLogService.selectByBusinessId(businessId);
            if (po != null) {
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setResultObject(BeanConvertUtils.convertList(po, ProcessAuditLogDto.class));
                response.setSuccess(true);
            } else {
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setResultObject(null);
                response.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("---------查看退货单审批意见>>queryApprovalInfo(),error="+ ExceptionUtils.getFullStackTrace(e)+"----------");
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }
}
