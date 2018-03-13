package com.yatang.sc.facade.service;

import com.yatang.sc.facade.domain.AdPlanModifyParamPo;
import com.yatang.sc.facade.domain.AdPlanPo;

import java.util.List;

/**
 * @描述: 404广告方案sevice接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 11:45
 * @版本: v1.0
 */
public interface AdPlanService {
    /**
     * 新增广告方案
     * @param adPlanPo 广告方案的po
     * @author yangshuang
     * @return
     */
    boolean addAdPlan(AdPlanPo adPlanPo);
    /**
     * 删除广告方案
     * @param id 广告方案的主键
     * @author yangshuang
     * @return
     */
    boolean deleteAdPlanById(Integer id);

    /**
     ** 查询所有的广告方案
     * @author yangshuang
     * @return
     */
    List<AdPlanPo> queryAllAdPlanList();
    /**
     * 根据id查询广告方案
     * @param id 广告方案的主键
     * @author yangshuang
     * @return
     */
    AdPlanPo getAdPlanById(Integer id);
    /**
     * 根据 广告方案id和启用停用类型修改广告状态
     *
     * @param id   广告方案id
     * @param status 类型 0,停用,1,启用
     * @return
     * @author yangshuang
     */
    boolean changeAdPlanState(AdPlanModifyParamPo modifyParamPo);
    /**
     * 根据id更新广告方案
     * @param adPlanPo
     * @author yangshuang
     * @return
     */
    boolean updateAdPlan(AdPlanPo adPlanPo);

    /**
     * 查看广告是否被修改
     * @param adPlanPo
     * @author yangshuang
     * @return
     */
    int checkModifyAdPlan(AdPlanPo adPlanPo);
}
