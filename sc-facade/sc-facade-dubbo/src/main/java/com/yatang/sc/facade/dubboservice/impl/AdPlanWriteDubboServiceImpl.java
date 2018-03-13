package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.AdPlanModifyParamPo;
import com.yatang.sc.facade.domain.AdPlanPo;
import com.yatang.sc.facade.dto.AdPlanDto;
import com.yatang.sc.facade.dto.AdPlanModifyParamDto;
import com.yatang.sc.facade.dubboservice.AdPlanWriteDubboService;
import com.yatang.sc.facade.service.AdPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 404广告配置WritedubboService
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 10:13
 * @版本: v1.0
 */
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service("adPlanWriteDubboService")
public class AdPlanWriteDubboServiceImpl implements AdPlanWriteDubboService {

    private final AdPlanService adPlanService;

    @Override
    public Response<Void> addAdPlan(AdPlanDto adPlanDto) {
        Response<Void> response = new Response<>();
        try {
            AdPlanPo adPlanPo = BeanConvertUtils.convert(adPlanDto, AdPlanPo.class);
            response.setSuccess(adPlanService.addAdPlan(adPlanPo));
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));

        }
        return response;
    }

    @Override
    public Response<Void> deleteAdPlanById(Integer id) {
        Response<Void> response = new Response<>();
        try {
            response.setSuccess(adPlanService.deleteAdPlanById(id));
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Void> changeAdPlanState(AdPlanModifyParamDto modifyParamDto) {
        Response<Void> response = new Response<>();
        try {
            //dto2po
            AdPlanModifyParamPo modifyParamPo = BeanConvertUtils.convert(modifyParamDto, AdPlanModifyParamPo.class);
            response.setSuccess(adPlanService.changeAdPlanState(modifyParamPo));
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     *
     * @param adPlanDto
     * @return
     */
    @Override
    public Response<Void> updateAdPlan(AdPlanDto adPlanDto) {
        Response<Void> response = new Response<>();
        try {
            //dto2PO
            AdPlanPo adPlanPo = BeanConvertUtils.convert(adPlanDto, AdPlanPo.class);
            //判断是否被更改
            int count=adPlanService.checkModifyAdPlan(adPlanPo);
            if (count==1){//未被修改
                response.setCode(CommonsEnum.RESPONSE_10020.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10020.getName());
                return response;
            }
            boolean update=adPlanService.updateAdPlan(adPlanPo);
            if (!update) {
                response.setCode(CommonsEnum.RESPONSE_10021.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10021.getName());
                return response;
            }
            response.setSuccess(update);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
