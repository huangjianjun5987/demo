package com.yatang.sc.facade.dubboservice.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.AdPlanPo;
import com.yatang.sc.facade.dto.AdPlanDto;
import com.yatang.sc.facade.dubboservice.AdPlanQueryDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.AdPlanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述: 404广告方案QuerydubboService
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 10:12
 * @版本: v1.0
 */
@Log4j
@Service("adPlanQueryDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdPlanQueryDubboServiceImpl implements AdPlanQueryDubboService {

    private final AdPlanService adPlanService;
    @Override
    public Response<List<AdPlanDto>> queryAllAdPlanList() {
        Response<List<AdPlanDto>> response = new Response<>();
        try {
            List<AdPlanPo> poList=adPlanService.queryAllAdPlanList();
            //po2Dto
            List<AdPlanDto> dtoList = BeanConvertUtils.convertList(poList, AdPlanDto.class);
            response.setResultObject(dtoList);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<AdPlanDto> getAdPlanById(Integer id) {
        Response<AdPlanDto> response = new Response<>();
        try {
            AdPlanPo adPlanPo=adPlanService.getAdPlanById(id);
            if (null==adPlanPo) {
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                return response;
            }
            //po2dto
            AdPlanDto adPlanDto = BeanConvertUtils.convert(adPlanPo, AdPlanDto.class);
            response.setResultObject(adPlanDto);
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
