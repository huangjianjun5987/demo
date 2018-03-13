package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.AdPlanDto;
import com.yatang.sc.facade.dto.AdPlanModifyParamDto;

/**
 * @描述: 404广告配置WritedubboService
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 10:10
 * @版本: v1.0
 */
public interface AdPlanWriteDubboService {
    /**
     * 新增广告方案
     * @param adPlanDto 广告方案的dto
     * @author yangshuang
     * @return
     */
    Response<Void> addAdPlan(AdPlanDto adPlanDto);

    /**
     * 删除广告方案
     * @param id 广告方案的主键
     * @author yangshuang
     * @return
     */
    Response<Void> deleteAdPlanById(Integer id);
    /**
     * 根据 广告方案id和启用停用类型修改广告状态
     *
     * @param modifyParamDto
     * @return
     */
    Response<Void> changeAdPlanState(AdPlanModifyParamDto modifyParamDto);
    /**
     * 根据id更新广告方案
     * @param adPlanDto
     * @author yangshuang
     * @return
     */
    Response<Void> updateAdPlan(AdPlanDto adPlanDto);
}
