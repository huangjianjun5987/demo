package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.CarouselAdPo;
import com.yatang.sc.facade.domain.QueryRepeatSortingPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarouselAdDao {
    int deleteByPrimaryKey(Integer id);
    int insert(CarouselAdPo record);
    CarouselAdPo queryById(Integer id);
    CarouselAdPo queryBySorting(QueryRepeatSortingPo record);
    int updateByPrimaryKey(CarouselAdPo record);
    List<CarouselAdPo> listBySorting();
    int updateCertainCarouselAd(CarouselAdPo record);

    List<CarouselAdPo> queryCarouselAdsByAreaId(@Param("areaId") String areaId);

    List<CarouselAdPo> queryCarouselAdsByParam(CarouselAdPo carouselAdPo);
}
