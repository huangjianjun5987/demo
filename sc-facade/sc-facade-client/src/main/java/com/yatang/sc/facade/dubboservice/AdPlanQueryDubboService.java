package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.AdPlanDto;

import java.util.List;

/**
 * @描述: 404广告配置QuerydubboService
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 10:10
 * @版本: v1.0
 */
public interface AdPlanQueryDubboService {
    /**
     * 查询所有的广告方案
     * @author yangshuang
     * @return
     */
    Response<List<AdPlanDto>> queryAllAdPlanList();
    /**
     * 根据id查询广告方案
     * @param id 广告方案的主键
     * @author yangshuang
     * @return
     */
    Response<AdPlanDto> getAdPlanById(Integer id);
}
