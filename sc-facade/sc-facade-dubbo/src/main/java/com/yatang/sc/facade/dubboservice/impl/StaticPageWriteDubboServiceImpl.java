package com.yatang.sc.facade.dubboservice.impl;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.StaticPageInfoPo;
import com.yatang.sc.facade.dto.StaticPageInfoDto;
import com.yatang.sc.facade.dubboservice.StaticPageWriteDubboService;
import com.yatang.sc.facade.service.StaticPageService;

import lombok.extern.log4j.Log4j;

/**
 * @描述:
 * @类名:
 * @作者: lvheping
 * @创建时间: 2017/6/8 19:32
 * @版本: v1.0
 */
@Log4j
@Service(value = "staticPageWriteDubboService")
public class StaticPageWriteDubboServiceImpl implements StaticPageWriteDubboService {
    @Autowired
    private StaticPageService staticPageService;
    /**
     * 新增静态页
     *
     * @param staticPageInfoDto
     * @return
     */
    @Override
    public Response<Boolean> InsertStaticPage(StaticPageInfoDto staticPageInfoDto) {
        log.info("新增参数"+staticPageInfoDto);
        Response<Boolean> response = new Response<>();
        StaticPageInfoPo convert = BeanConvertUtils.convert(staticPageInfoDto, StaticPageInfoPo.class);
        try {

            Boolean b = staticPageService.InsertStaticPage(convert);
            response.setSuccess(b);
            response.setErrorMessage("添加成功");
        }catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("添加失败");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 修改静态页基本信息
     *
     * @param convert
     * @return
     */
    @Override
    public Response<Boolean> updateStaticPageBase(StaticPageInfoDto convert) {
        log.info("修改参数"+convert);
        Response<Boolean> response = new Response<>();
        StaticPageInfoPo staticPageInfoPo = BeanConvertUtils.convert(convert, StaticPageInfoPo.class);
        try {

            Boolean b = staticPageService.updateStaticPageBase(staticPageInfoPo);
            response.setSuccess(b);
            response.setErrorMessage("修改成功");
        }catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("修改失败");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
