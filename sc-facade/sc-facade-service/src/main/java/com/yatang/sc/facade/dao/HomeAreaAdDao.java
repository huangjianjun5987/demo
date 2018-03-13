package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.HomeAreaAdPo;
import com.yatang.sc.facade.domain.QueryCarouselPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeAreaAdDao {

  int deleteByPrimaryKey(String id);

  int insert(HomeAreaAdPo record);

  int insertSelective(HomeAreaAdPo record);

  HomeAreaAdPo queryById(String id);

  int updateByPrimaryKeySelective(HomeAreaAdPo record);

  int updateByPrimaryKey(HomeAreaAdPo record);

  List<HomeAreaAdPo> listByParam();

  List<HomeAreaAdPo> queryByParam(HomeAreaAdPo record);

  int count();

  List<HomeAreaAdPo> queryAreas(@Param("companyId") String companyId);

  HomeAreaAdPo queryCarouselArea(QueryCarouselPo queryCarouselPo);

  int switchOptWayOfHome(HomeAreaAdPo po);

  int switchOptWayOfCarousel(HomeAreaAdPo po);
}