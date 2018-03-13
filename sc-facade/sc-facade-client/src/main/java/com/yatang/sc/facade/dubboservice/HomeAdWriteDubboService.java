package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.CarouselAdDto;
import com.yatang.sc.facade.dto.CarouselParamDto;
import com.yatang.sc.facade.dto.EditQuickNavigationStatusDto;
import com.yatang.sc.facade.dto.HomeAreaAdDto;
import com.yatang.sc.facade.dto.HomeItemAdDto;
import com.yatang.sc.facade.dto.QuickNavigationDto;

/**
 * @描述: 首页广告dubbo服务接口.
 * @作者: yipeng
 * @创建时间: 2017年06月09日11:01:07
 * @版本: 1.0 .
 */
public interface HomeAdWriteDubboService {

    /**
     * @param areaId
     * @param isUp   true:上移,false:下移
     * @Description: 移动区域
     * @author yipeng
     * @date 2017年06月09日16:41:55
     */
    Response<Boolean> moveArea(String areaId, Boolean isUp);

    /**
     * @param areaId
     * @param isEnabled true:启用,false:未启用
     * @Description: 设置区域是否可启用
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Response<Boolean> setAreaEnable(String areaId, Boolean isEnabled);

    /**
     * @param dto
     * @Description: 设置首页区域
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Response<Boolean> saveArea(HomeAreaAdDto dto);

    /**
     * @param dto
     * @Description: 设置广告项
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Response<Boolean> saveItemAd(HomeItemAdDto dto);

    /**
     * @param areaId
     * @Description: 删除首页区域
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Response<Boolean> deleteArea(String areaId);

    /**
     * @param itemAdId
     * @Description: 删除广告项
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Response<Boolean> deleteItemAd(String itemAdId);

    /**
     * @Description: 修改快捷导航(特定参数)
     * @author tankejia
     * @date 2017/6/9- 14:39
     * @param record
     */
    Response<Boolean> updateCertainQuickNavigation(QuickNavigationDto record);

    /**
     * @Description: 批量冻结/启用快捷导航
     * @author tankejia
     * @date 2017/7/5- 10:00
     * @param record
     */
    Response<Boolean> batchUpdateQuickNavigation(EditQuickNavigationStatusDto record, String userId);

    /**
     * @Description: 修改轮播间隔时间（二期）
     * @author tankejia
     * @date 2017/8/20- 15:18
     * @param paramDto
     */
    Response<Boolean> updateCarouselIntervalById(CarouselParamDto  paramDto);

    /**
     * @Description: 删除轮播广告
     * @author tankejia
     * @date 2017/6/9- 14:09
     * @param id
     */
    Response<Boolean> deleteCarouselAd(Integer id);

    /**
     * @Description: 新增轮播广告
     * @author tankejia
     * @date 2017/6/9- 14:11
     * @param record
     */
    Response<Boolean> insertCarouselAd(CarouselAdDto record);

    /**
     * @Description: 修改轮播广告
     * @author tankejia
     * @date 2017/6/9- 14:13
     * @param record
     */
    Response<Boolean> updateCarouselAd(CarouselAdDto record);

    /**
     * @Description: 修改轮播广告(特定参数)
     * @author tankejia
     * @date 2017/6/9- 14:13
     * @param record
     */
    Response<Boolean> updateCertainCarouselAd(CarouselAdDto record);

    /**
     * @Description: 切换首页运营方式
     * @author liuxiaokun
     * @date 2017/11/22
     * @param dto
     */
    Response<Boolean> switchOptWayOfHome(HomeAreaAdDto dto);

    /**
     * @Description: 切换轮播运营方式
     * @author liuxiaokun
     * @date 2017/11/22
     * @param dto
     */
    Response<Boolean> switchOptWayOfCarousel(HomeAreaAdDto dto);
}
