package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.StaticPageInfoPo;
import com.yatang.sc.facade.domain.StaticPageQueyPo;

import java.util.List;

public interface StaticPageInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(StaticPageInfoPo record);

    int insertSelective(StaticPageInfoPo record);

    StaticPageInfoPo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StaticPageInfoPo record);

    int updateByPrimaryKeyWithBLOBs(StaticPageInfoPo record);

    int updateByPrimaryKey(StaticPageInfoPo record);

    /**
     * 根据传入参数查询静态页列表
     * @param staticPageQueyPo
     */
   List<StaticPageInfoPo> findStaticPageList(StaticPageQueyPo staticPageQueyPo);
}