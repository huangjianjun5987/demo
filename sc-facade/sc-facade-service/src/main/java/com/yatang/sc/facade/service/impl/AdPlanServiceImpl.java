package com.yatang.sc.facade.service.impl;

import com.yatang.sc.facade.dao.AdPlanDao;
import com.yatang.sc.facade.domain.AdPlanModifyParamPo;
import com.yatang.sc.facade.domain.AdPlanPo;
import com.yatang.sc.facade.service.AdPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @描述:04广告方案sevice接口实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 11:46
 * @版本: v1.0
 */
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdPlanServiceImpl implements AdPlanService {
    private final AdPlanDao adPlanDao;

    @Override
    public boolean addAdPlan(AdPlanPo adPlanPo) {
        adPlanPo.setCreateTime(new Date());
        adPlanPo.setStatus(0);// 默认停用
        return adPlanDao.insert(adPlanPo) >= 1;
    }


    @Override
    public boolean deleteAdPlanById(Integer id) {
        return adPlanDao.deleteByPrimaryKey(id) >= 1;
    }


    @Override
    public List<AdPlanPo> queryAllAdPlanList() {
        return adPlanDao.queryAllAdPlanList();
    }


    @Override
    public AdPlanPo getAdPlanById(Integer id) {
        return adPlanDao.selectByPrimaryKey(id);
    }


    /**
     * 根据 广告方案id和启用停用类型修改广告状态
     *
     * @param id   广告方案id
     * @param status 类型 0,停用,1,启用
     * @author yangshuang
     * @return

     */
    public boolean changeAdPlanState(AdPlanModifyParamPo modifyParamPo) {
        Integer status = modifyParamPo.getStatus();
        modifyParamPo.setModifyTime(new Date());
        // 1.停用
        if (0 == status) {
            return adPlanDao.changeAdPlanState(modifyParamPo) >= 1;
        }
        // 停用其他广告方案并启用当前id的广告方案
        else if (1 == status) {
            adPlanDao.stopAllAdPlanState(modifyParamPo);
            return adPlanDao.changeAdPlanState(modifyParamPo) >= 1;
        }
        return false;
    }


    @Override
    public boolean updateAdPlan(AdPlanPo adPlanPo) {
        adPlanPo.setModifyTime(new Date());
        return adPlanDao.updateByPrimaryKeySelective(adPlanPo) >= 1;
    }

    @Override
    public int checkModifyAdPlan(AdPlanPo adPlanPo) {
        return adPlanDao.checkModifyAdPlan(adPlanPo);
    }
}
