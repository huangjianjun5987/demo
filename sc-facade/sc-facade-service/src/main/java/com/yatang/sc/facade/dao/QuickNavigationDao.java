package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.QuickNavigationPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuickNavigationDao {
    int updateByPrimaryKey(QuickNavigationPo record);
    List<QuickNavigationPo> listByPosition();
    QuickNavigationPo queryById(Integer id);
    int updateCertainQuickNavigation(QuickNavigationPo record);

    List<QuickNavigationPo> queryQuickNavigationsByAreaId(@Param("areaId") String areaId);

    int insert(QuickNavigationPo quickNavigationPo);
}
