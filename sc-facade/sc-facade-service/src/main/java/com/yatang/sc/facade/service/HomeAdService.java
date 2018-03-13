package com.yatang.sc.facade.service;

import com.yatang.sc.facade.domain.CarouselAdPo;
import com.yatang.sc.facade.domain.CarouselParamPo;
import com.yatang.sc.facade.domain.EditQuickNavigationStatusPo;
import com.yatang.sc.facade.domain.HomeAreaAdPo;
import com.yatang.sc.facade.domain.HomeItemAdPo;
import com.yatang.sc.facade.domain.QueryCarouselPo;
import com.yatang.sc.facade.domain.QueryRepeatSortingPo;
import com.yatang.sc.facade.domain.QuickNavigationPo;

import java.util.List;

/**
 * @描述: 首页广告服务接口.
 * @作者: yiepng
 * @创建时间: 2017年06月09日10:59:39
 * @版本: 1.0 .
 */
public interface HomeAdService {

    /**
     * @Description: 初始化公司的所有首页区域
     * @author liuxiaokun
     * @date 2017年11月22日10：12
     */
    void initAreas(String companyId);

    /**
     * @Description: 首页区域列表
     * @author yipeng
     * @date 2017年06月09日16:45:20
     */
    List<HomeAreaAdPo> areaList();

    /**
     * @Description: 根据区域ID查询广告项列表
     * @author yipeng
     * @date 2017年06月09日16:41:55
     */
    List<HomeItemAdPo> adItemListBy();

    /**
     * @param areaId
     * @param isUp   true:上移,false:下移
     * @Description: 移动区域
     * @author yipeng
     * @date 2017年06月09日16:41:55
     */
    boolean moveArea(String areaId, Boolean isUp);

    /**
     * @param areaId
     * @param isEnabled true:启用,false:未启用
     * @Description: 设置区域是否可启用
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Boolean setAreaEnable(String areaId, Boolean isEnabled);

    /**
     * @param po
     * @Description: 设置首页区域
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Boolean saveArea(HomeAreaAdPo po);

    /**
     * @param po
     * @Description: 设置广告项
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Boolean saveItemAd(HomeItemAdPo po);

    /**
     * @param areaId
     * @Description: 删除首页区域
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Boolean deleteArea(String areaId);

    /**
     * @param itemAdId
     * @Description: 删除广告项
     * @author yipeng
     * @date 2017年06月09日16:41:57
     */
    Boolean deleteItemAd(String itemAdId);

    /**
     * @Description: 查询快捷导航列表
     * @author tankejia
     * @date 2017/6/9- 13:44
     */
    List<QuickNavigationPo> queryQuickNavigationList();

    /**
     * @Description: 根据id查询单个快捷导航
     * @author tankejia
     * @date 2017/6/9- 13:48
     * @param id
     */
    QuickNavigationPo queryByQuickNavigationId(Integer id);

    /**
     * @Description: 修改快捷导航列表
     * @author tankejia
     * @date 2017/6/9- 13:48
     * @param record
     */
    Boolean updateQuickNavigation(QuickNavigationPo record);

    /**
     * @Description: 批量冻结/启用快捷导航
     * @author tankejia
     * @date 2017/7/5- 10:00
     * @param record
     */
    Boolean batchUpdateQuickNavigation(EditQuickNavigationStatusPo record, String userId);

    /**
     * @Description: 查询轮播间隔时间(二期)
     * @author tankejia
     * @date 2017/8/20- 15:00
     */
    CarouselParamPo queryFirstCarouselInterval();

    /**
     * @Description: 修改轮播间隔时间(二期)
     * @author tankejia
     * @date 2017/8/20- 15:00
     * @param po
     */
    Boolean updateCarouselIntervalById(CarouselParamPo po);

    /**
     * @Description: 根据id查询单个轮播广告
     * @author tankejia
     * @date 2017/6/9- 13:59
     * @param id
     */
    CarouselAdPo queryByCarouselAdId(Integer id);

    /**
     * @Description: 判断序号是否重复（根据sorting查询单个轮播广告）
     * @author tankejia
     * @date 2017/6/9- 14:02
     * @param record
     */
    Boolean queryByCarouselAdSorting(QueryRepeatSortingPo record);

    /**
     * @Description: 查询轮播广告列表
     * @author tankejia
     * @date 2017/6/9- 14:06
     */
    List<CarouselAdPo> queryCarouselAdList();

    /**
     * @Description: 删除轮播广告
     * @author tankejia
     * @date 2017/6/9- 14:09
     * @param id
     */
    Boolean deleteCarouselAd(Integer id);

    /**
     * @Description: 新增轮播广告
     * @author tankejia
     * @date 2017/6/9- 14:11
     * @param po
     */
    Boolean insertCarouselAd(CarouselAdPo po);

    /**
     * @Description: 修改轮播广告
     * @author tankejia
     * @date 2017/6/9- 14:13
     * @param po
     */
    Boolean updateCarouselAd(CarouselAdPo po);

    /**
     * @Description: 修改轮播广告（特定参数）
     * @author tankejia
     * @date 2017/6/9- 14:13
     * @param po
     */
    Boolean updateCertainCarouselAd(CarouselAdPo po);

    /**
     * @Description: 修改快捷导航（特定参数）
     * @author tankejia
     * @date 2017/6/9- 13:48
     * @param record
     */
    Boolean updateCertainQuickNavigation(QuickNavigationPo record);

    /*
     *@描述:通过areaID获取区域里的广告
     *@作者:tangqi
     *@时间:2017/11/17 11:38
     */
    List<HomeItemAdPo> queryItemByAreaId(String areaId);

    /*
     *@描述:通过区域ID获取轮播
     *@作者:tangqi
     *@时间:2017/11/17 12:43
     */
    List<CarouselAdPo> queryCarouselAdsByAreaId(String areaId);

    /*
     *@描述:通过区域ID获取导航
     *@作者:tangqi
     *@时间:2017/11/17 14:06
     */
    List<QuickNavigationPo> queryQuickNavigationsByAreaId(String areaId);

    /*
     *@描述:获取页面
     *@作者:tangqi
     *@时间:2017/11/21 21:25
     */
    List<HomeAreaAdPo> queryAreas(String companyId);

    /*
     *@描述:通过公司ID和区域类型获取轮播区域
     *@作者:tangqi
     *@时间:2017/11/22 9:52
     */
    HomeAreaAdPo queryCarouselArea(QueryCarouselPo queryCarouselPo);

    /*
     *@描述:通过ID获取区域
     *@作者:tangqi
     *@时间:2017/11/22 11:28
     */
    HomeAreaAdPo queryAreaById(String id);

    /*
     *@描述:获取轮播间隔时间
     *@作者:tangqi
     *@时间:2017/11/22 16:21
     */
    CarouselParamPo queryCarouselInterval(String areaId);

    /*
     *@描述:切换首页运营方式
     *@作者:liuxiaokun
     *@时间:2017/11/22
     */
    Boolean switchOptWayOfHome(HomeAreaAdPo po);

    /*
     *@描述:切换轮播运营方式
     *@作者:liuxiaokun
     *@时间:2017/11/22
     */
    Boolean switchOptWayOfCarousel(HomeAreaAdPo po);
}
