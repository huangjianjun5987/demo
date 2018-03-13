package com.yatang.sc.facade.dubboservice.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.StaticPageInfoPo;
import com.yatang.sc.facade.domain.StaticPageQueyPo;
import com.yatang.sc.facade.dto.StaticPageInfoDto;
import com.yatang.sc.facade.dto.StaticPageQueyDto;
import com.yatang.sc.facade.dubboservice.StaticPageQueryDubboService;
import com.yatang.sc.facade.service.StaticPageService;

import lombok.extern.log4j.Log4j;

/**
 * @描述:查询静态页面列表dubbo实现
 * @类名:StaticPageQueryDubboServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/6/8 14:18
 * @版本: v1.0
 */
@Log4j
@Service(value = "staticPageQueryDubboService")
public class StaticPageQueryDubboServiceImpl implements StaticPageQueryDubboService {
    @Autowired
    private StaticPageService staticPageServiceImpl;

    /**
     * 跟据传入的条件查询静态页列表
     *
     * @param staticPageQueyDto
     * @return
     */
    @Override
    public Response<PageResult<StaticPageInfoDto>> findStaticPageList(StaticPageQueyDto staticPageQueyDto) {
        log.info("查询参数"+staticPageQueyDto.toString());
        Response<PageResult<StaticPageInfoDto>> response = new Response<PageResult<StaticPageInfoDto>>();
        StaticPageQueyPo staticPageQueyPo = BeanConvertUtils.convert(staticPageQueyDto,StaticPageQueyPo.class);
        try {
            PageInfo<StaticPageInfoPo> staticPageList = staticPageServiceImpl.findStaticPageList(staticPageQueyPo);
            List<StaticPageInfoDto> staticPageInfoDtos = BeanConvertUtils.convertList(staticPageList.getList(), StaticPageInfoDto.class);
            PageResult pageResult = new PageResult();
            pageResult.setData(staticPageInfoDtos);
            pageResult.setPageNum(staticPageList.getPageNum());
            pageResult.setPageSize(staticPageList.getPageSize());
            pageResult.setTotal(staticPageList.getTotal());
            response.setResultObject(pageResult);
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("查询失败");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }

        return response;
    }

    /**
     * 根据传入的ID查询信息回显
     *
     * @param id
     * @return
     */
    @Override
    public Response<StaticPageInfoDto> getStaticPageById(Integer id) {
        log.info("静态页id"+id);
        Response<StaticPageInfoDto> response = new Response<StaticPageInfoDto>();
        try {

            StaticPageInfoPo staticPageInfoPo = staticPageServiceImpl.getStaticPageById(id);
            StaticPageInfoDto staticPageInfoDto = BeanConvertUtils.convert(staticPageInfoPo, StaticPageInfoDto.class);
            response.setResultObject(staticPageInfoDto);
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("根据ID查询失败");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}
