package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.StaticPageInfoDto;
import com.yatang.sc.facade.dto.StaticPageQueyDto;

/**
 * @描述:查询静态页面列表服务接口
 * @类名:StaticPageQueryDubboService
 * @作者: lvheping
 * @创建时间: 2017/6/8 13:41
 * @版本: v1.0
 */

public interface StaticPageQueryDubboService {
    /**
     *跟据传入的条件查询静态页列表
     * @param staticPageQueyDto
     * @return
     */
    Response<PageResult<StaticPageInfoDto>> findStaticPageList(StaticPageQueyDto staticPageQueyDto);

    /**
     * 根据传入的ID查询信息回显
     * @param id
     * @return
     */
    Response<StaticPageInfoDto> getStaticPageById(Integer id);
}
