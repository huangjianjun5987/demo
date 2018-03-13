package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.AdPlanModifyParamPo;
import com.yatang.sc.facade.domain.AdPlanPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdPlanDao {
    int deleteByPrimaryKey(Integer id);

    int insert(AdPlanPo record);
    /**
     * 根据id查询广告方案
     * @param id 广告方案的主键
     * @author yangshuang
     * @return
     */
    AdPlanPo selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(AdPlanPo record);

    List<AdPlanPo> queryAllAdPlanList();
/*    *//**
     * 改变指定id的广告状态
     * @param id
     * @param status
     * @author yangshuang
     * @return
     *//*
    int changeAdPlanState(@Param(value = "id") Integer id, @Param(value = "status")int status);*/

    /**
     * 停用所有的广告
     * @author yangshuang
     * @return
     * @param modifyParamPo
     */
    int stopAllAdPlanState(AdPlanModifyParamPo modifyParamPo);
    /**
     * 查看广告是否被修改
     * @param adPlanPo
     * @author yangshuang
     * @return
     */
    int checkModifyAdPlan(AdPlanPo adPlanPo);
    /**
     * 改变指定id的广告状态
     * @param id
     * @param status
     * @author yangshuang
     * @return
     */
    int changeAdPlanState(AdPlanModifyParamPo modifyParamPo);
}