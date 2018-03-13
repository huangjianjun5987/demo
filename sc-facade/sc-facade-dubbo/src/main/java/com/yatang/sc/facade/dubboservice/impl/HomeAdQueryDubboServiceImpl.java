package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.CarouselAdPo;
import com.yatang.sc.facade.domain.CarouselParamPo;
import com.yatang.sc.facade.domain.HomeAreaAdPo;
import com.yatang.sc.facade.domain.HomeItemAdPo;
import com.yatang.sc.facade.domain.QueryCarouselPo;
import com.yatang.sc.facade.domain.QueryRepeatSortingPo;
import com.yatang.sc.facade.domain.QuickNavigationPo;
import com.yatang.sc.facade.dto.CarouselAdDto;
import com.yatang.sc.facade.dto.CarouselParamDto;
import com.yatang.sc.facade.dto.HomeAreaAdAppDto;
import com.yatang.sc.facade.dto.HomeAreaAdDto;
import com.yatang.sc.facade.dto.HomeItemAdDto;
import com.yatang.sc.facade.dto.QueryCarouselDto;
import com.yatang.sc.facade.dto.QueryHomePageAdDto;
import com.yatang.sc.facade.dto.QueryRepeatSortingDto;
import com.yatang.sc.facade.dto.QuickNavigationDto;
import com.yatang.sc.facade.dubboservice.HomeAdQueryDubboService;
import com.yatang.sc.facade.enums.AdType;
import com.yatang.sc.facade.enums.AreaType;
import com.yatang.sc.facade.service.HomeAdService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("homeAdQueryDubboService")
public class HomeAdQueryDubboServiceImpl implements HomeAdQueryDubboService {

    protected final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private HomeAdService service;

    @Autowired
    private RedisDistributedLockFactory mRedisDistributedLockFactory;

    @Override
    public Response<List<HomeAreaAdAppDto>> appAreaList() {
        Response<List<HomeAreaAdAppDto>> response = new Response<>();

        // 获取所有首页广告项转换到区域列表中
        List<HomeItemAdPo> itemAdPos = service.adItemListBy();

        // 转换轮播图广告到所有首页广告项中
        itemAdPos.addAll(carouselAds());
        // 转换快捷导航广告到所有首页广告项中
        itemAdPos.addAll(quickNavigations(true));

        List<HomeAreaAdPo> list = service.areaList();
        Map<String, HomeAreaAdDto> areaAdDtoMap = FluentIterable.from(list)
                .transform(new Function<HomeAreaAdPo, HomeAreaAdDto>() {
                    @Override
                    public HomeAreaAdDto apply(HomeAreaAdPo po) {
                        HomeAreaAdDto dto = BeanConvertUtils.convert(po, HomeAreaAdDto.class);
                        dto.setItemAds(Lists.<HomeItemAdDto>newArrayList());
                        return dto;
                    }
                })
                .uniqueIndex(new Function<HomeAreaAdDto, String>() {
                    @Override
                    public String apply(HomeAreaAdDto po) {
                        return po.getId();
                    }
                });
        for (HomeItemAdPo itemAdPo : itemAdPos) {
            String areaId = itemAdPo.getAreaId();
            // 如果广告项不存在区域，则进行过滤
            HomeAreaAdDto areaAdDto = areaAdDtoMap.get(areaId);
            if (areaAdDto == null) {
                continue;
            }

            areaAdDto.getItemAds().add(BeanConvertUtils.convert(itemAdPo, HomeItemAdDto.class));
        }


        // 过滤未启用区域
        List<HomeAreaAdAppDto> enabledAreaAds = Lists.newArrayList();
        for (HomeAreaAdDto areaAd : areaAdDtoMap.values()) {
            if (BooleanUtils.isNotTrue(areaAd.getIsEnabled())) {
                continue;
            }

            HomeAreaAdAppDto appDto = BeanConvertUtils.convert(areaAd, HomeAreaAdAppDto.class);

            if (Objects.equals(HomeAreaAdPo.CAROUSEL, appDto.getId())) {
                CarouselParamPo param = service.queryFirstCarouselInterval();
                appDto.setCarouselInterval(param.getCarouselInterval());
            }

            enabledAreaAds.add(appDto);
        }

        response.setSuccess(true);
        response.setResultObject(enabledAreaAds);

        return response;
    }

    private List<HomeItemAdPo> carouselAds() {
        List<HomeItemAdPo> newItemAds = Lists.newArrayList();
        List<CarouselAdPo> carouselAds = service.queryCarouselAdList();
        for (CarouselAdPo carouselAd : carouselAds) {
            if (carouselAd.getStatus() != 1) {
                continue;
            }

            HomeItemAdPo itemAd = new HomeItemAdPo();
            itemAd.setId(String.valueOf(carouselAd.getId()));
            itemAd.setAreaId(HomeAreaAdPo.CAROUSEL);
            itemAd.setProductNo(String.valueOf(carouselAd.getGoodsId()));
            itemAd.setUrlType(carouselAd.getLinkType());
            itemAd.setUrl(carouselAd.getLinkAddress());
            itemAd.setIcon(carouselAd.getPicAddress());
            itemAd.setAdType(AdType.CAROUSEL);
            itemAd.setLinkId(carouselAd.getLinkId());
            itemAd.setLinkKeyword(carouselAd.getLinkKeyword());
            newItemAds.add(itemAd);
        }
        return newItemAds;
    }

    private List<HomeItemAdPo> quickNavigations(boolean isAll) {
        List<HomeItemAdPo> newItemAds = Lists.newArrayList();
        List<QuickNavigationPo> quickNavigations = service.queryQuickNavigationList();
        for (QuickNavigationPo quickNavigation : quickNavigations) {
            if(!isAll){
                if (quickNavigation.getStatus() != 1) {
                    continue;
                }
            }
            HomeItemAdPo itemAd = new HomeItemAdPo();
            itemAd.setId(String.valueOf(quickNavigation.getId()));
            itemAd.setAreaId(HomeAreaAdPo.QUICK_NAV);
            itemAd.setProductNo(String.valueOf(quickNavigation.getGoodsId()));
            itemAd.setUrlType(quickNavigation.getNavigationType());
            itemAd.setName(quickNavigation.getNavigationPosition());
            itemAd.setTitle(quickNavigation.getNavigationName());
            itemAd.setUrl(quickNavigation.getLinkAddress());
            itemAd.setIcon(quickNavigation.getPicAddress());
            itemAd.setAdType(AdType.QUICK_NAV);
            itemAd.setLinkId(quickNavigation.getLinkId());
            itemAd.setLinkKeyword(quickNavigation.getLinkKeyword());
            newItemAds.add(itemAd);
        }
        return newItemAds;
    }

    @Override
    public Response<List<HomeAreaAdDto>> areaList() {
        Response<List<HomeAreaAdDto>> response = new Response<>();

        List<HomeAreaAdPo> list = service.areaList();
        Map<String, HomeAreaAdDto> areaAdDtoMap = FluentIterable.from(list)
                .transform(new Function<HomeAreaAdPo, HomeAreaAdDto>() {
                    @Override
                    public HomeAreaAdDto apply(HomeAreaAdPo po) {
                        HomeAreaAdDto dto = BeanConvertUtils.convert(po, HomeAreaAdDto.class);
                        dto.setItemAds(Lists.<HomeItemAdDto>newArrayList());
                        return dto;
                    }
                })
                .uniqueIndex(new Function<HomeAreaAdDto, String>() {
                    @Override
                    public String apply(HomeAreaAdDto po) {
                        return po.getId();
                    }
                });

        // 获取所有首页广告项转换到区域列表中
        List<HomeItemAdPo> itemAdPos = service.adItemListBy();

        // 转换轮播图广告到所有首页广告项中
        itemAdPos.addAll(carouselAds());
        // 转换快捷导航广告到所有首页广告项中
        itemAdPos.addAll(quickNavigations(true));

        for (HomeItemAdPo itemAdPo : itemAdPos) {
            String areaId = itemAdPo.getAreaId();
            HomeAreaAdDto areaAdDto = areaAdDtoMap.get(areaId);
            if (areaAdDto == null) {
                continue;
            }

            areaAdDto.getItemAds().add(BeanConvertUtils.convert(itemAdPo, HomeItemAdDto.class));
        }

        response.setSuccess(true);
        response.setResultObject(Lists.newArrayList(areaAdDtoMap.values()));

        return response;
    }

    @Override
    public Response<List<QuickNavigationDto>> queryQuickNavigationList() {
        if (log.isInfoEnabled()) {
            log.info("---------- <<查询快捷导航列表>> queryQuickNavigationList() ----------");
        }
        Response<List<QuickNavigationDto>> response = new Response();

        try {
            List<QuickNavigationPo> list = service.queryQuickNavigationList();
            if (null != list && !list.isEmpty()) {
                List<QuickNavigationDto> dtos = BeanConvertUtils.convertList(list, QuickNavigationDto.class);
                response.setSuccess(true);
                response.setResultObject(dtos);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setSuccess(true);
                response.setResultObject(null);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
            }

        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }

        return response;
    }

    @Override
    public Response<QuickNavigationDto> queryByQuickNavigationId(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<根据id精确查询快捷导航>> queryByQuickNavigationId(Integer id): id=" + id + "----------");
        }
        Response<QuickNavigationDto> response = new Response();
        try {
            QuickNavigationPo po = service.queryByQuickNavigationId(id);
            if (null != po) {
                QuickNavigationDto dto = BeanConvertUtils.convert(po, QuickNavigationDto.class);
                response.setSuccess(true);
                response.setResultObject(dto);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setSuccess(true);
                response.setResultObject(null);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    @Override
    public Response<CarouselParamDto> queryFirstCarouselInterval() {
        if (log.isInfoEnabled()) {
            log.info("---------- <<查询轮播间隔时间>> queryFirstCarouselInterval() ----------");
        }
        Response<CarouselParamDto> response = new Response();
        try {
            CarouselParamPo po = service.queryFirstCarouselInterval();
            response.setSuccess(true);
            response.setResultObject(BeanConvertUtils.convert(po, CarouselParamDto.class));
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }

        return response;
    }

    @Override
    public Response<CarouselAdDto> queryCarouselAdById(Integer id) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<根据id精确查询轮播广告>> queryByCarouselAdById(Integer id): id=" + id + "----------");
        }
        Response<CarouselAdDto> response = new Response();
        try {
            CarouselAdPo po = service.queryByCarouselAdId(id);
            if (null != po) {
                CarouselAdDto dto = BeanConvertUtils.convert(po, CarouselAdDto.class);
                response.setSuccess(true);
                response.setResultObject(dto);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setSuccess(true);
                response.setResultObject(null);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    @Override
    public Response<Boolean> queryByCarouselAdBySorting(QueryRepeatSortingDto record) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<判断序号是否重复>> queryByCarouselAdBySorting(QueryRepeatSortingDto record): record="
                    + JSON.toJSONString(record) + "----------");
        }
        Response<Boolean> response = new Response();
        QueryRepeatSortingPo po = BeanConvertUtils.convert(record, QueryRepeatSortingPo.class);
        try {
            Boolean flag = service.queryByCarouselAdSorting(po);
            if (flag) {
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setCode(CommonsEnum.RESPONSE_10001.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_10001.getName());
            }
            response.setResultObject(null);
            response.setSuccess(true);
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    @Override
    public Response<List<CarouselAdDto>> queryCarouselAdList() {
        if (log.isInfoEnabled()) {
            log.info("---------- <<查询所有轮播广告>> queryCarouselAdList() ----------");
        }
        Response<List<CarouselAdDto>> response = new Response();
        try {
            List<CarouselAdPo> list = service.queryCarouselAdList();
            if (null != list && !list.isEmpty()) {
                List<CarouselAdDto> dtos = BeanConvertUtils.convertList(list, CarouselAdDto.class);
                response.setSuccess(true);
                response.setResultObject(dtos);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setSuccess(true);
                response.setResultObject(null);
                response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
                response.setCode(CommonsEnum.RESPONSE_10006.getCode());
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return response;
    }

    private List<HomeItemAdPo> queryCarouselAdsByAreaId(HomeAreaAdDto homeAreaAdDto, boolean isAll) {
        List<CarouselAdPo> carouselAds = service.queryCarouselAdsByAreaId(homeAreaAdDto.getId());
        if(org.apache.commons.collections.CollectionUtils.isEmpty(carouselAds) || homeAreaAdDto.getIsUsingNation()){
            List<HomeAreaAdPo> headquarters = service.queryAreas("headquarters");
            for (HomeAreaAdPo homeAreaAdPo : headquarters){
                if(homeAreaAdPo.getAreaType() == AreaType.CAROUSEL.getValue()){
                    carouselAds = service.queryCarouselAdsByAreaId(homeAreaAdPo.getId());
                }
            }
        }
        if(carouselAds == null){
            carouselAds = new ArrayList<>();
        }
        Iterator<CarouselAdPo> iterator = carouselAds.iterator();
        while (iterator.hasNext()){
            CarouselAdPo next = iterator.next();
            if(next.getStatus() == 0){
                iterator.remove();
            }
        }
        ImmutableList<CarouselAdPo> areaAdPos = FluentIterable.from(carouselAds).toSortedList(new Comparator<CarouselAdPo>() {
            @Override
            public int compare(CarouselAdPo o1, CarouselAdPo o2) {
                if(o1.getSorting() < o2.getSorting()){
                    return -1;
                }else {
                    return 1;
                }
            }
        });
        return convertCarousel2Item(areaAdPos);
    }

    private List<HomeItemAdPo> queryQuickNavigationsByAreaId(String areaId, boolean isAll) {
        List<HomeItemAdPo> newItemAds = Lists.newArrayList();
        List<QuickNavigationPo> quickNavigations = service.queryQuickNavigationsByAreaId(areaId);
        for (QuickNavigationPo quickNavigation : quickNavigations) {
            if (quickNavigation.getStatus() != 1) {
                continue;
            }

            HomeItemAdPo itemAd = new HomeItemAdPo();
            itemAd.setId(String.valueOf(quickNavigation.getId()));
            itemAd.setAreaId(quickNavigation.getAreaId());
            itemAd.setProductNo(String.valueOf(quickNavigation.getGoodsId()));
            itemAd.setUrlType(quickNavigation.getNavigationType());
            itemAd.setName(quickNavigation.getNavigationPosition());
            itemAd.setTitle(quickNavigation.getNavigationName());
            itemAd.setUrl(quickNavigation.getLinkAddress());
            itemAd.setIcon(quickNavigation.getPicAddress());
            itemAd.setAdType(AdType.QUICK_NAV);
            newItemAds.add(itemAd);
        }
        return newItemAds;
    }

    @Override
    public Response<List<HomeAreaAdDto>> queryAreas(QueryHomePageAdDto dto) {
        log.info("HomeAdQueryDubboServiceImpl-->queryAreas参数：{"+dto.toString()+"}");
        String companyId = dto.getCompanyId();
        if (dto.getHomePageType() == 1){
            companyId = "headquarters";
        }
        Response<List<HomeAreaAdDto>> response = new Response<>();
        List<HomeAreaAdDto> areaAdDtos = new ArrayList<>();
        try{
            List<HomeAreaAdPo> homeAreaAdPos = service.queryAreas(companyId);
            //如果为空就创建，如果不为空就填充数据
            if(CollectionUtils.isEmpty(homeAreaAdPos)){
                areaAdDtos = insertPage(companyId);
            }else{
                areaAdDtos = assembleAreas(homeAreaAdPos, true);
            }
            //查询子公司页面运营模式
            if(!companyId.equals(dto.getCompanyId())){
                List<HomeAreaAdPo> homeAreaAdPos1 = service.queryAreas(dto.getCompanyId());
                for(HomeAreaAdPo po : homeAreaAdPos1){
                    for(HomeAreaAdDto areaDto : areaAdDtos){
                        if(po.getAreaType() == AreaType.CAROUSEL.getValue() && areaDto.getAreaType() == AreaType.CAROUSEL.getValue()){
                            areaDto.setIsUsingNation(po.getIsUsingNation());
                        }else{
                            if(po.getAreaType() == AreaType.CAROUSEL.getValue() || areaDto.getAreaType() == AreaType.CAROUSEL.getValue()){
                                continue;
                            }
                            areaDto.setIsUsingNation(po.getIsUsingNation());
                        }
                    }
                }
            }
            response.setResultObject(areaAdDtos);
            response.setSuccess(true);
            response.setCode("200");
        }catch (Exception e){
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public Response<List<HomeAreaAdAppDto>> queryAppAreas(String companyId) {
        log.info("homeAdQueryDubboServiceImpl->queryAppAreas参数：{"+companyId+"}");
        Response<List<HomeAreaAdAppDto>> showPageRes = new Response<>();
        try{
            //从数据库中查询
            List<HomeAreaAdPo> homeAreaAdPos = service.queryAreas(companyId);
            log.info("pre queryAppAreas:" + JSON.toJSONString(homeAreaAdPos));
            //根据查询结果，获取到最佳显示页面
            List<HomeAreaAdPo> homeAreaAdPoReturn = getPerfectAreas(homeAreaAdPos);
            log.info("post queryAppAreas:" + JSON.toJSONString(homeAreaAdPoReturn));
            //填充area里面的数据
            List<HomeAreaAdDto> areaAdDtos = assembleAreas(homeAreaAdPoReturn, false);
            log.info("post assembleAreas:" + JSON.toJSONString(areaAdDtos));
            List<HomeAreaAdAppDto> addAreas = BeanConvertUtils.convertList(areaAdDtos, HomeAreaAdAppDto.class);
            for(HomeAreaAdAppDto homeAreaAdAppDto : addAreas){
                if(homeAreaAdAppDto.getAreaType() == AreaType.CAROUSEL.getValue()){
                    CarouselParamPo carouselParamPo = service.queryCarouselInterval(homeAreaAdAppDto.getId());
                    homeAreaAdAppDto.setCarouselInterval(carouselParamPo.getCarouselInterval());
                }
            }
            showPageRes.setSuccess(true);
            showPageRes.setCode("200");
            showPageRes.setResultObject(addAreas);
        }catch (Exception e){
            showPageRes.setCode("500");
            showPageRes.setErrorMessage(e.getMessage());
            showPageRes.setSuccess(false);
        }

        return showPageRes;
    }

    private HomeAreaAdDto queryCarouselAd(HomeAreaAdPo carouselArea) {
        List<CarouselAdPo> carouselAdPoList = new ArrayList<>();

        QueryCarouselPo queryCarousel = new QueryCarouselPo();
        queryCarousel.setCompanyId("headquarters");
        queryCarousel.setAreaType(AreaType.CAROUSEL.getValue());
        HomeAreaAdPo headAreaAd = service.queryCarouselArea(queryCarousel);

        List<CarouselAdPo> carouselAdPos = service.queryCarouselAdsByAreaId(headAreaAd.getId());
        //过滤禁用的
        for(CarouselAdPo carouselAdPo : carouselAdPos){
            if(carouselAdPo.getStatus() == 1){
                carouselAdPoList.add(carouselAdPo);
            }
        }
        List<HomeItemAdDto> homeItemAdDtos = BeanConvertUtils.convertList(convertCarousel2Item(carouselAdPoList), HomeItemAdDto.class);

        HomeAreaAdDto areaAdDto = BeanConvertUtils.convert(carouselArea, HomeAreaAdDto.class);
        areaAdDto.setItemAds(homeItemAdDtos);
        return areaAdDto;
    }

    /*
     *@描述:获取完成的页面
     *@作者:tangqi
     *@时间:2017/11/22 15:03
     */
    private List<HomeAreaAdPo> getPerfectAreas(List<HomeAreaAdPo> homeAreaAdPos) {

        List<HomeAreaAdPo> homeAreaAdPoReturn = new ArrayList<>();
        List<HomeAreaAdPo> homeAreaAdPoBody = new ArrayList<>();
        //检查坑位
        boolean useSelf = true;
        HomeAreaAdDto carouselAreaAd = null;

        if(CollectionUtils.isEmpty(homeAreaAdPos)){
            homeAreaAdPos = service.queryAreas("headquarters");
        }else{
            for(HomeAreaAdPo homeAreaAdPo : homeAreaAdPos){
                if(homeAreaAdPo == null){
                    homeAreaAdPos = service.queryAreas("headquarters");
                    break;
                }else if(homeAreaAdPo.getAreaType() == AreaType.CAROUSEL.getValue() && homeAreaAdPo.getIsUsingNation() == true){
                    carouselAreaAd = queryCarouselAd(homeAreaAdPo);
                }else if(homeAreaAdPo.getAreaType() == AreaType.BANNER.getValue() || homeAreaAdPo.getAreaType() == AreaType.FLOOR.getValue()){
                    if(homeAreaAdPo.getIsUsingNation() == true){
                        useSelf = false;
                        continue;
                    }
                    List<HomeItemAdPo> homeItemAdPos = service.queryItemByAreaId(homeAreaAdPo.getId());
                    for(HomeItemAdPo itemPo : homeItemAdPos){
                        if(itemPo.getUrlType() == null){
                            useSelf = false;
                            continue;
                        }
                    }
                }

            }
        }
        log.info("carouselAdRes-----------------------:" + JSON.toJSONString(carouselAreaAd));
        if(!useSelf){
            homeAreaAdPoBody =  service.queryAreas("headquarters");
        }
        log.info("homeAreaAdPoBody------------------------:" + JSON.toJSONString(homeAreaAdPoBody));
        if(carouselAreaAd == null && CollectionUtils.isEmpty(homeAreaAdPoBody)){
            homeAreaAdPoReturn.addAll(homeAreaAdPos);
        }else if(carouselAreaAd == null && CollectionUtils.isNotEmpty(homeAreaAdPoBody)){
            for(HomeAreaAdPo HomeAreaAdPo : homeAreaAdPos){
                if (HomeAreaAdPo.getAreaType() == AreaType.CAROUSEL.getValue()){
                    homeAreaAdPoReturn.add(HomeAreaAdPo);
                }
            }
            for(HomeAreaAdPo HomeAreaAdPo : homeAreaAdPoBody){
                if (HomeAreaAdPo.getAreaType() != AreaType.CAROUSEL.getValue()){
                    homeAreaAdPoReturn.add(HomeAreaAdPo);
                }
            }
        }else if(carouselAreaAd != null && CollectionUtils.isNotEmpty(homeAreaAdPoBody)){
            homeAreaAdPoReturn.addAll(homeAreaAdPoBody);
        }else if(carouselAreaAd != null && CollectionUtils.isEmpty(homeAreaAdPoBody)){
            homeAreaAdPoReturn.add(BeanConvertUtils.convert(carouselAreaAd, HomeAreaAdPo.class));
            for(HomeAreaAdPo HomeAreaAdPo : homeAreaAdPos){
                if (HomeAreaAdPo.getAreaType() != AreaType.CAROUSEL.getValue()){
                    homeAreaAdPoReturn.add(HomeAreaAdPo);
                }
            }
        }
        log.info("去掉禁用的部分之前：homeAreaAdPoReturn------------------------:" + JSON.toJSONString(homeAreaAdPoReturn));
        //去掉禁用的部分
        Iterator<HomeAreaAdPo> iterator = homeAreaAdPoReturn.iterator();
        while (iterator.hasNext()){
            HomeAreaAdPo next = iterator.next();
            if(next.getAreaType() == AreaType.QUICK_NAV.getValue()){
                continue;
            }
            if(!(next.getIsEnabled())){
                iterator.remove();
            }
        }
        log.info("去掉禁用的部分之后：homeAreaAdPoReturn------------------------:" + JSON.toJSONString(homeAreaAdPoReturn));
        ImmutableList<HomeAreaAdPo> areaAdPos = FluentIterable.from(homeAreaAdPoReturn).toSortedList(new Comparator<HomeAreaAdPo>() {
            @Override
            public int compare(HomeAreaAdPo o1, HomeAreaAdPo o2) {
                if (o1.getSequence() < o2.getSequence()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        return areaAdPos;
    }

    /*
     *@描述:组装Area
     *@作者:tangqi
     *@时间:2017/11/22 12:51
     */
    private List<HomeAreaAdDto> assembleAreas(List<HomeAreaAdPo> areaAdPos, boolean isAll){
        List<HomeAreaAdDto> homeAreaAdAppDtos = new ArrayList<>();
        Map<String, HomeAreaAdDto> areaAdDtoMap = FluentIterable.from(areaAdPos)
                .transform(new Function<HomeAreaAdPo, HomeAreaAdDto>() {
                    @Override
                    public HomeAreaAdDto apply(HomeAreaAdPo po) {
                        HomeAreaAdDto dto = BeanConvertUtils.convert(po, HomeAreaAdDto.class);
                        dto.setItemAds(Lists.<HomeItemAdDto>newArrayList());
                        return dto;
                    }
                })
                .uniqueIndex(new Function<HomeAreaAdDto, String>() {
                    @Override
                    public String apply(HomeAreaAdDto po) {
                        return po.getId();
                    }
                });
        // 获取所有首页广告项转换到区域列表中
        for (Map.Entry<String, HomeAreaAdDto> entry : areaAdDtoMap.entrySet()) {
            HomeAreaAdDto homeAreaAd = entry.getValue();
            List<HomeItemAdPo> itemAdPos = new ArrayList<>();
            itemAdPos = service.queryItemByAreaId(entry.getKey());

            if (Objects.equals(homeAreaAd.getAreaType(), AreaType.CAROUSEL.getValue())) {
                // 转换轮播图广告到所有首页广告项中
                itemAdPos.addAll(queryCarouselAdsByAreaId(homeAreaAd, isAll));
            }

            // 转换快捷导航广告到所有首页广告项中
            List<HomeItemAdPo> homeItemAdPos = new ArrayList<>();
            if(homeAreaAd.getAreaType() == AreaType.QUICK_NAV.getValue()){
                homeItemAdPos = quickNavigations(isAll);
            }
            itemAdPos.addAll(homeItemAdPos);
            homeAreaAd.getItemAds().addAll(BeanConvertUtils.convertList(itemAdPos, HomeItemAdDto.class));
        }
        homeAreaAdAppDtos.addAll(areaAdDtoMap.values());
        return homeAreaAdAppDtos;
    }

    /*
     *@描述:检查page是否已经存在，不存在就生成一个新的
     *@作者:tangqi
     *@时间:2017/11/22 12:51
     */
    private List<HomeAreaAdDto> insertPage(String companyId) {
        String lockPath = "/initialize_company_areas_lock_" + companyId;
        RLock rLock = mRedisDistributedLockFactory.getLock(lockPath);
        List<HomeAreaAdPo> homeAreaAdPos = null;
        try {
            rLock.lock();
            homeAreaAdPos = service.queryAreas(companyId);
            //双锁检测，提升并发时的性能
            if (CollectionUtils.isEmpty(homeAreaAdPos)) {
                service.initAreas(companyId);
                homeAreaAdPos = service.queryAreas(companyId);
            }
        } catch (Exception e) {
            log.error("---初始化area数据失败---", e);
            throw e;
        } finally {
            rLock.unlock();
        }

        List<HomeAreaAdDto> homeAreaAdDtos = assembleAreas(homeAreaAdPos, true);
        return homeAreaAdDtos;
    }

    /*
     *@描述:把轮播图转化为常规的广告位
     *@作者:tangqi
     *@时间:2017/11/22 15:42
     */
    private List<HomeItemAdPo> convertCarousel2Item(List<CarouselAdPo> carouselAdPos){
        List<HomeItemAdPo> homeItemAdPos = new ArrayList<>();
        if (CollectionUtils.isEmpty(carouselAdPos)){
            return homeItemAdPos;
        }
        for (CarouselAdPo carouselAdPo : carouselAdPos){
            if (carouselAdPo == null){
                continue;
            }
            HomeItemAdPo itemAd = new HomeItemAdPo();
            itemAd.setId(String.valueOf(carouselAdPo.getId()));
            itemAd.setAreaId(carouselAdPo.getAreaId());
            itemAd.setProductNo(String.valueOf(carouselAdPo.getGoodsId()));
            itemAd.setUrlType(carouselAdPo.getLinkType());
            itemAd.setUrl(carouselAdPo.getLinkAddress());
            itemAd.setIcon(carouselAdPo.getPicAddress());
            itemAd.setAdType(AdType.CAROUSEL);
            homeItemAdPos.add(itemAd);

        }
        return homeItemAdPos;
    }

    @Override
    public Response<CarouselParamDto> queryCarouselInterval(String areaId) {
        Response<CarouselParamDto> response = new Response();
        try{
            CarouselParamPo carouselParamPo = service.queryCarouselInterval(areaId);
            if(carouselParamPo == null){
                throw new RuntimeException("初始化轮播间隔时间失败，请手动设置");
            }
            response.setResultObject(BeanConvertUtils.convert(carouselParamPo, CarouselParamDto.class));
            response.setSuccess(true);
            response.setCode("200");
        }catch(Exception e){
            response.setCode("500");
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response<HomeAreaAdDto> queryCarouselArea(QueryHomePageAdDto queryHomePageAdDto) {
        Response<HomeAreaAdDto> response = new Response<>();
        String companyId = queryHomePageAdDto.getCompanyId();
        if (queryHomePageAdDto.getHomePageType() == 1){
            companyId = "headquarters";
        }
        try{

            QueryCarouselDto queryCarouselDto = new QueryCarouselDto();
            queryCarouselDto.setCompanyId(companyId);
            queryCarouselDto.setAreaType(AreaType.CAROUSEL.getValue());
            HomeAreaAdDto areaAdDto = BeanConvertUtils.convert(service.queryCarouselArea(BeanConvertUtils.convert(queryCarouselDto, QueryCarouselPo.class)), HomeAreaAdDto.class);
            //分公司查询轮播，如果查询总公司，但是还是要查询自己的运营模式
            if (!companyId.equals(queryHomePageAdDto.getCompanyId())){
                queryCarouselDto.setCompanyId(queryHomePageAdDto.getCompanyId());
                HomeAreaAdPo homeAreaAdPo = service.queryCarouselArea(BeanConvertUtils.convert(queryCarouselDto, QueryCarouselPo.class));
                areaAdDto.setIsUsingNation(homeAreaAdPo.getIsUsingNation());
            }
            response.setSuccess(true);
            response.setCode("200");
            response.setResultObject(areaAdDto);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode("500");
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public Response<List<CarouselAdDto>> queryCarouselList(String areaId) {
        Response<List<CarouselAdDto>> response = new Response<>();
        try{
            List<CarouselAdPo> carouselAdPoList = service.queryCarouselAdsByAreaId(areaId);
            List<CarouselAdDto> carouselAdDtos = BeanConvertUtils.convertList(carouselAdPoList, CarouselAdDto.class);
            response.setResultObject(carouselAdDtos);
            response.setCode("200");
            response.setSuccess(true);
        }catch (Exception e){
            response.setErrorMessage(e.getMessage());
            response.setCode("500");
            response.setSuccess(false);
        }

        return response;
    }
}
