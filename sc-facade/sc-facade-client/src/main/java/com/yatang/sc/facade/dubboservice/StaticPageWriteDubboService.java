package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.StaticPageInfoDto;

/**
 * @描述:静态页面的编辑接口
 * @类名:StaticPageWriteDubboService
 * @作者: lvheping
 * @创建时间: 2017/6/8 17:47
 * @版本: v1.0
 */

public interface StaticPageWriteDubboService {
    /**
     * 新增静态页
     * @param staticPageInfoDto
     * @return
     */
    Response<Boolean> InsertStaticPage(StaticPageInfoDto staticPageInfoDto);

    /**
     * 修改静态页基本信息
     * @param convert
     * @return
     */
    Response<Boolean> updateStaticPageBase(StaticPageInfoDto convert);
}
