package com.yatang.sc.facade.dao;

import com.yatang.sc.facade.domain.CarouselParamPo;
import org.apache.ibatis.annotations.Param;

public interface CarouselParamDao {
    CarouselParamPo queryFirstCarouselInterval();
    int updateCarouselIntervalById(CarouselParamPo po);
    int insert(CarouselParamPo po);
    CarouselParamPo queryCarouselInterval(@Param("areaId") String areaId);
}
