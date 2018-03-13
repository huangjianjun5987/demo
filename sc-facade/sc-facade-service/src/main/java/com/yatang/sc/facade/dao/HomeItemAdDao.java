package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.HomeItemAdPo;

import java.util.List;

public interface HomeItemAdDao {

  int deleteByPrimaryKey(String id);

  int insert(HomeItemAdPo record);

  int insertSelective(HomeItemAdPo record);

  HomeItemAdPo queryById(String id);

  int updateByPrimaryKeySelective(HomeItemAdPo record);

  int updateByPrimaryKey(HomeItemAdPo record);

  List<HomeItemAdPo> listByParam();

  List<HomeItemAdPo> queryItemByAreaId(String areaId);

}