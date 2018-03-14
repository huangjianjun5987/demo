package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.dto.pm.ProcessAuditLogDto;
import com.yatang.sc.facade.dubboservice.ProcessAuditLogQueryDubboService;
import com.yatang.sc.operation.vo.pm.ProcessAuditLogVo;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @描述: 流程审批日志控制层
 * @作者: tankejia
 * @创建时间: 2017/10/25-15:32 .
 * @版本: 1.0 .
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/processAuditLog")
public class ProcessAuditLogAction extends BaseAction {

    private final ProcessAuditLogQueryDubboService logQueryDubboService;

    /**
     * @Description: 查看退货单审批意见
     * @author tankejia
     * @date 2017/10/25- 15:47
     * @param businessId
     */
    @ParamValid
    @RequestMapping(value = "/queryApprovalInfo", method = RequestMethod.GET)
    public Response<List<ProcessAuditLogVo>> queryApprovalInfo(
            @NotEmpty(message = MessageConstantUtil.NOT_EMPTY) String businessId) {
        Response<List<ProcessAuditLogVo>> response = new Response<>();
        Response<List<ProcessAuditLogDto>> resp = logQueryDubboService.queryApprovalInfo(businessId);
        response.setResultObject(BeanConvertUtils.convertList(resp.getResultObject(), ProcessAuditLogVo.class));
        response.setSuccess(resp.isSuccess());
        response.setErrorMessage(resp.getErrorMessage());
        response.setCode(resp.getCode());
        return response;

    }

}
