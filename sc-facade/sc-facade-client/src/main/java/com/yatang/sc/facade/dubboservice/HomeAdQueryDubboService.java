package com.yatang.sc.facade.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.CarouselAdDto;
import com.yatang.sc.facade.dto.CarouselParamDto;
import com.yatang.sc.facade.dto.HomeAreaAdAppDto;
import com.yatang.sc.facade.dto.HomeAreaAdDto;
import com.yatang.sc.facade.dto.HomePageAdDto;
import com.yatang.sc.facade.dto.QueryHomePageAdDto;
import com.yatang.sc.facade.dto.QueryRepeatSortingDto;
import com.yatang.sc.facade.dto.QuickNavigationDto;

import java.util.List;

/**
 * @描述: 首页广告dubbo服务接口.
 * @作者: yipeng
 * @创建时间: 2017年06月09日11:01:00
 * @版本: 1.0 .
 */
public interface HomeAdQueryDubboService {

    /**
     * @Description: app端首页区域列表
     * @author yipeng
     * @date 2017年07月08日11:15:01
     */
    Response<List<HomeAreaAdAppDto>> appAreaList();

    /**
     * @Description: 首页区域列表
     * @author yipeng
     * @date 2017年06月09日16:45:20
     */
    Response<List<HomeAreaAdDto>> areaList();


    /**
     * @Description: 查询快捷导航列表
     * @author tankejia
     * @date 2017/6/9- 13:44
     */
    Response<List<QuickNavigationDto>> queryQuickNavigationList();

    /**
     * @param id
     * @Description: 根据id查询单个快捷导航
     * @author tankejia
     * @date 2017/6/9- 13:48
     */
    Response<QuickNavigationDto> queryByQuickNavigationId(Integer id);

    /**
     * @Description: 查询轮播间隔时间(二期)
     * @author tankejia
     * @date 2017/8/20- 15:00
     */
    Response<CarouselParamDto> queryFirstCarouselInterval();

    /**
     * @param id
     * @Description: 根据id查询单个轮播广告
     * @author tankejia
     * @date 2017/6/9- 13:59
     */
    Response<CarouselAdDto> queryCarouselAdById(Integer id);

    /**
     * @param record
     * @Description: 判断序号是否重复（根据sorting查询单个轮播广告）
     * @author tankejia
     * @date 2017/6/9- 14:02
     */
    Response<Boolean> queryByCarouselAdBySorting(QueryRepeatSortingDto record);

    /**
     * @Description: 查询轮播广告列表
     * @author tankejia
     * @date 2017/6/9- 14:06
     */
    Response<List<CarouselAdDto>> queryCarouselAdList();

    /*
     *@描述:根据公司Id获取主页
     *@作者:tangqi
     *@时间:2017/11/21 16:33
     */
    Response<List<HomeAreaAdDto>> queryAreas(QueryHomePageAdDto dto);

    /*
     *@描述:App端看到的页面
     *@作者:tangqi
     *@时间:2017/11/22 11:18
     */
    Response<List<HomeAreaAdAppDto>> queryAppAreas(String companyId);

    /*
     *@描述:获取轮播间隔时间
     *@作者:tangqi
     *@时间:2017/11/22 16:18
     */
    Response<CarouselParamDto> queryCarouselInterval(String areaId);

    /*
     *@描述:获取轮播图区域信息
     *@作者:tangqi
     *@时间:2017/11/23 9:42
     */
    Response<HomeAreaAdDto> queryCarouselArea(QueryHomePageAdDto queryHomePageAdDto);

    /*
     *@描述:通过区域ID获取轮播图列表
     *@作者:tangqi
     *@时间:2017/11/23 9:48
     */
    Response<List<CarouselAdDto>> queryCarouselList(String areaId);

}
