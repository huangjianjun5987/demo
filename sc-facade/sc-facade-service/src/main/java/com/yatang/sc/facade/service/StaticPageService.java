package com.yatang.sc.facade.service;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.StaticPageInfoPo;
import com.yatang.sc.facade.domain.StaticPageQueyPo;

import java.util.List;
import java.util.Map;

/**
 * @描述:静态页管理service层接口
 * @类名:StaticPageService
 * @作者: lvheping
 * @创建时间: 2017/6/8 14:21
 * @版本: v1.0
 */

public interface StaticPageService {
    /**
     * 根据传入的参数查询静态列表页
     * @param staticPageQueyPo
     * @return
     */
    PageInfo<StaticPageInfoPo> findStaticPageList(StaticPageQueyPo staticPageQueyPo);

    /**
     * 添加静态页
     * @param convert
     * @return
     */
    Boolean InsertStaticPage(StaticPageInfoPo convert);

    /**
     * 修改静态页基本信息
     * @param staticPageInfoPo
     * @return
     */
    Boolean updateStaticPageBase(StaticPageInfoPo staticPageInfoPo);

    /**
     * 根据传入ID查询静态页信息
     * @param id
     * @return
     */
    StaticPageInfoPo getStaticPageById(Integer id);
}
