package com.yatang.sc.facade.service.impl;


import com.google.common.collect.FluentIterable;
import com.yatang.sc.facade.dao.CarouselAdDao;
import com.yatang.sc.facade.dao.CarouselParamDao;
import com.yatang.sc.facade.dao.HomeAreaAdDao;
import com.yatang.sc.facade.dao.HomeItemAdDao;
import com.yatang.sc.facade.dao.QuickNavigationDao;
import com.yatang.sc.facade.domain.CarouselAdPo;
import com.yatang.sc.facade.domain.CarouselParamPo;
import com.yatang.sc.facade.domain.EditQuickNavigationStatusPo;
import com.yatang.sc.facade.domain.HomeAreaAdPo;
import com.yatang.sc.facade.domain.HomeItemAdPo;
import com.yatang.sc.facade.domain.QueryCarouselPo;
import com.yatang.sc.facade.domain.QueryRepeatSortingPo;
import com.yatang.sc.facade.domain.QuickNavigationPo;
import com.yatang.sc.facade.enums.AdType;
import com.yatang.sc.facade.enums.AreaType;
import com.yatang.sc.facade.service.HomeAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Transactional
public class HomeAdServiceImpl implements HomeAdService {

    private final HomeAreaAdDao homeAreaAdDao;
    private final HomeItemAdDao homeItemAdDao;
    private final CarouselAdDao carouselAdDao;
    private final CarouselParamDao carouselParamDao;
    private final QuickNavigationDao quickNavigationDao;
    private final static String EMPTY = "";

    @Override
    public void initAreas(String companyId) {

        initCarouselAreas(companyId);
        initQuickNavAreas(companyId);
        initBannerAreas(companyId);
        initFloorAreas(companyId);
        initHotAreas(companyId);
    }

    private void initHotAreas(String companyId) {
        for(int i=1; i<=1; i++){
            HomeAreaAdPo newPo = new HomeAreaAdPo();
            String areaId = companyId+"-hot";
            newPo.setId(areaId);
            newPo.setName("热门推荐");
            newPo.setSequence((double) homeAreaAdDao.count());
            newPo.setIsEnabled(false);
            newPo.setAreaType(AreaType.HOT.getValue());
            newPo.setCompanyId(companyId);
            newPo.setIsUsingNation(true);
            homeAreaAdDao.insertSelective(newPo);

            for(int j=1; j<=6; j++){
                HomeItemAdPo homeItemAdPo = new HomeItemAdPo();
                homeItemAdPo.setId(areaId+"-"+j);
                homeItemAdPo.setAreaId(areaId);
                homeItemAdPo.setName("热门推荐-"+j+"号位");
                homeItemAdPo.setAdType(AdType.FLOOR);
                homeItemAdDao.insertSelective(homeItemAdPo);
            }
        }
    }

    private void initQuickNavAreas(String companyId) {
        for(int i=1; i<=1; i++){
            HomeAreaAdPo newPo = new HomeAreaAdPo();
            String areaId = companyId+"-quick-nav";
            newPo.setId(areaId);
            newPo.setName("快捷功能区");
            newPo.setSequence((double) homeAreaAdDao.count());
            newPo.setIsEnabled(false);
            newPo.setAreaType(AreaType.QUICK_NAV.getValue());
            newPo.setCompanyId(companyId);
            newPo.setIsUsingNation(true);
            homeAreaAdDao.insertSelective(newPo);
        }
    }

    private void initCarouselAreas(String companyId) {
        for(int i=1; i<=1; i++){
            HomeAreaAdPo newPo = new HomeAreaAdPo();
            String id = companyId+"-carousel";
            newPo.setId(id);
            newPo.setName("轮播广告区");
            newPo.setSequence((double) homeAreaAdDao.count());
            newPo.setIsEnabled(false);
            newPo.setAreaType(AreaType.CAROUSEL.getValue());
            newPo.setCompanyId(companyId);
            newPo.setIsUsingNation(true);
            homeAreaAdDao.insertSelective(newPo);

            //在创建轮转area时同时创建一个控制轮播间隔时间的记录
            CarouselParamPo carouselParamPo = new CarouselParamPo();
            carouselParamPo.setCarouselInterval(5);
            carouselParamPo.setStatus(1);
            carouselParamPo.setAreaId(id);
            carouselParamDao.insert(carouselParamPo);
        }
    }

    private void initFloorAreas(String companyId) {
        for(int i=1; i<=3; i++){
            HomeAreaAdPo newPo = new HomeAreaAdPo();
            String areaId = companyId+"-floor-"+i;
            newPo.setId(areaId);
            newPo.setName("推荐管理楼层"+i);
            newPo.setSequence((double) homeAreaAdDao.count());
            newPo.setIsEnabled(false);
            newPo.setAreaType(AreaType.FLOOR.getValue());
            newPo.setCompanyId(companyId);
            newPo.setIsUsingNation(true);
            homeAreaAdDao.insertSelective(newPo);

            //初始化该area的7条item记录，包括标题记录
            HomeItemAdPo homeItemAdPoTitle = new HomeItemAdPo();
            homeItemAdPoTitle.setId(areaId+"-title");
            homeItemAdPoTitle.setAreaId(areaId);
            homeItemAdPoTitle.setName("推荐管理楼层"+i+"-"+"栏目标题");
            homeItemAdPoTitle.setAdType(AdType.COLUMN_TITLE);
            homeItemAdDao.insertSelective(homeItemAdPoTitle);
            for(int j=1; j<=6; j++){
                HomeItemAdPo homeItemAdPo = new HomeItemAdPo();
                homeItemAdPo.setId(areaId+"-"+j);
                homeItemAdPo.setAreaId(areaId);
                homeItemAdPo.setName("推荐管理楼层"+i+"-"+j+"号位");
                homeItemAdPo.setAdType(AdType.FLOOR);
                homeItemAdDao.insertSelective(homeItemAdPo);
            }
        }
    }

    private void initBannerAreas(String companyId) {
        for(int i=1; i<=3; i++){
            HomeAreaAdPo newPo = new HomeAreaAdPo();
            String areaId = companyId+"-banner-"+i;
            newPo.setId(areaId);
            newPo.setName("横幅广告"+i);
            newPo.setSequence((double) homeAreaAdDao.count());
            newPo.setIsEnabled(false);
            newPo.setAreaType(AreaType.BANNER.getValue());
            newPo.setCompanyId(companyId);
            newPo.setIsUsingNation(true);
            homeAreaAdDao.insertSelective(newPo);

            //初始化该area的1条item记录
            HomeItemAdPo homeItemAdPo = new HomeItemAdPo();
            homeItemAdPo.setId(areaId);
            homeItemAdPo.setAreaId(areaId);
            homeItemAdPo.setName("横幅广告"+i);
            homeItemAdPo.setAdType(AdType.BANNER);
            homeItemAdDao.insertSelective(homeItemAdPo);
        }
    }

    @Override
    public List<HomeAreaAdPo> areaList() {
      return homeAreaAdDao.listByParam();
    }

    @Override
    public List<HomeItemAdPo> adItemListBy() {
      return homeItemAdDao.listByParam();
    }

    @Override
    public boolean moveArea(String areaId, Boolean isUp) {
      HomeAreaAdPo areaAd = homeAreaAdDao.queryById(areaId);
      List<HomeAreaAdPo> areaAds = homeAreaAdDao.queryAreas(areaAd.getCompanyId());
      List<HomeAreaAdPo> areaAdsOfSort = FluentIterable.from(areaAds).toSortedList(new Comparator<HomeAreaAdPo>() {
        @Override
        public int compare(HomeAreaAdPo left, HomeAreaAdPo right) {
          return left.getSequence() == null || right.getSequence() == null ? 1 : left.getSequence().compareTo(right.getSequence());
        }
      });
      int currentIndex = areaAdsOfSort.indexOf(areaAd);
      if (isUp && currentIndex == 0) {
        throw new RuntimeException("不能上移");
      }
      if (!isUp && currentIndex == (areaAdsOfSort.size() - 1)) {
        throw new RuntimeException("不能下移");
      }

      HomeAreaAdPo targetFrom = areaAdsOfSort.get(isUp ?  currentIndex - 1 : currentIndex + 1);
      double fromSequence = targetFrom.getSequence();
      double toSequence;
      int toIndex;
      if (isUp) {
        toIndex = currentIndex - 2;
        toSequence = toIndex < 0 ? fromSequence - 1 : areaAdsOfSort.get(toIndex).getSequence();
      } else {
        toIndex = currentIndex + 2;
        toSequence = toIndex >= areaAdsOfSort.size() ? fromSequence + 1 : areaAdsOfSort.get(toIndex).getSequence();
      }

      areaAd.setSequence(fromSequence + ((toSequence - fromSequence) / 2));
      return homeAreaAdDao.updateByPrimaryKey(areaAd) >= 1;
    }

    @Override
    public Boolean setAreaEnable(String areaId, Boolean isEnabled) {
      HomeAreaAdPo areaAdPo = homeAreaAdDao.queryById(areaId);
      areaAdPo.setIsEnabled(isEnabled);
      return homeAreaAdDao.updateByPrimaryKey(areaAdPo) >= 1;
    }

    @Override
    public Boolean saveArea(HomeAreaAdPo po) {

      HomeAreaAdPo areaAdPo = homeAreaAdDao.queryById(po.getId());
      areaAdPo.setName(po.getName());
      return homeAreaAdDao.updateByPrimaryKey(areaAdPo) >= 1;
    }

    @Override
    public Boolean saveItemAd(HomeItemAdPo po) {
        if(po.getUrlType()!=null){
            switch (po.getUrlType()){
                case 1:
                    po.setUrl(EMPTY);
                    po.setLinkKeyword(EMPTY);
                    po.setLinkId(EMPTY);
                    break;
                case 2:
                    po.setProductNo(EMPTY);
                    po.setUrl(EMPTY);
                    po.setLinkKeyword(EMPTY);
                    break;
                case 3:
                    po.setProductNo(EMPTY);
                    po.setUrl(EMPTY);
                    if(po.getLinkId() != null && !"".equals(po.getLinkId())){
                        po.setLinkKeyword(EMPTY);
                    }
                    if(po.getLinkKeyword()!=null && !"".equals(po.getLinkKeyword())){
                        po.setLinkId(EMPTY);
                    }
                    break;
                case 4:
                    setItemParams2Null(po);
                    break;
                case 5:
                    setItemParams2Null(po);
                    break;
                case 6:
                    setItemParams2Null(po);
                    break;
                default:
                    throw new RuntimeException("链接类型参数非法！");
            }
        }
      return homeItemAdDao.updateByPrimaryKey(po) >= 1;
    }

    private void setItemParams2Null(HomeItemAdPo po) {
        po.setProductNo(EMPTY);
        po.setLinkKeyword(EMPTY);
        po.setLinkId(EMPTY);
    }

    @Override
    public Boolean deleteArea(String areaId) {
      return homeAreaAdDao.deleteByPrimaryKey(areaId) >= 1;
  }

    @Override
    public Boolean deleteItemAd(String itemAdId) {

        return homeItemAdDao.deleteByPrimaryKey(itemAdId) >= 1;
  }


    @Override
    public List<QuickNavigationPo> queryQuickNavigationList() {
        return quickNavigationDao.listByPosition();
    }

    @Override
    public QuickNavigationPo queryByQuickNavigationId(Integer id) {
        return quickNavigationDao.queryById(id);
    }

    @Override
    public Boolean updateQuickNavigation(QuickNavigationPo record) {
        return quickNavigationDao.updateByPrimaryKey(record) >= 1;
    }

    private void setQuickNavParams2Null(QuickNavigationPo record) {
        record.setGoodsId(EMPTY);
        record.setLinkKeyword(EMPTY);
        record.setLinkId(EMPTY);
    }

    @Override
    public Boolean batchUpdateQuickNavigation(EditQuickNavigationStatusPo record, String userId) {
      Boolean flag = false;
      for (Integer id : record.getIds()) {
          QuickNavigationPo po = new QuickNavigationPo();
          po.setId(id);
          po.setStatus(record.getStatus());
          po.setUpdatePerson(userId);
          flag = quickNavigationDao.updateByPrimaryKey(po) >= 1;
      }
      return flag;
    }

    @Override
    public CarouselParamPo queryFirstCarouselInterval() {
        return carouselParamDao.queryFirstCarouselInterval();
    }

    @Override
    public Boolean updateCarouselIntervalById(CarouselParamPo po) {
        return carouselParamDao.updateCarouselIntervalById(po) >= 1;
    }

    @Override
    public CarouselAdPo queryByCarouselAdId(Integer id) {
        return carouselAdDao.queryById(id);
    }

    @Override
    public Boolean queryByCarouselAdSorting(QueryRepeatSortingPo record) {
        return carouselAdDao.queryBySorting(record) == null;
    }

    @Override
    public List<CarouselAdPo> queryCarouselAdList() {
        return carouselAdDao.listBySorting();
    }

    @Override
    public Boolean deleteCarouselAd(Integer id) {
        checkHasOneCarouselAdInArea(id, "全国的轮播广告必须保留一条。");
        updateParamWhenNoCarouselAd(id);
        return carouselAdDao.deleteByPrimaryKey(id) >= 1;
    }

    /**
     * 当子公司的轮播广告删除完时，自动启用全国的轮播广告栏
     * @param currentCarouselId
     * @author liuxiaokun
     */
    private void updateParamWhenNoCarouselAd(Integer currentCarouselId) {
        CarouselAdPo carouselAdPo = carouselAdDao.queryById(currentCarouselId);
        if(carouselAdPo == null){
            throw new RuntimeException("该轮播ID没有对应的记录。");
        }
        String areaId = carouselAdPo.getAreaId();
        if(carouselAdDao.queryCarouselAdsByAreaId(areaId).size() < 2){
            HomeAreaAdPo homeAreaAdPo = new HomeAreaAdPo();
            homeAreaAdPo.setId(areaId);
            homeAreaAdPo.setIsUsingNation(true);
            homeAreaAdDao.updateByPrimaryKey(homeAreaAdPo);
        }
    }

    /**
     * 检查全国的轮播广告栏是否只剩一条，并且已经启用
     * @param currentCarouselId
     * @author liuxiaokun
     */
    private void checkHasOneCarouselAdInArea(Integer currentCarouselId, String errorMsg) {
        CarouselAdPo carouselAdPo = carouselAdDao.queryById(currentCarouselId);
        if(carouselAdPo == null){
            throw new RuntimeException("该轮播ID没有对应的记录。");
        }
        String areaId = carouselAdPo.getAreaId();
        String companyId = homeAreaAdDao.queryById(areaId).getCompanyId();
        Boolean isEnabled = carouselAdPo.getStatus()!=null && carouselAdPo.getStatus()==1;
        if(isEnabled && "headquarters".equalsIgnoreCase(companyId)){
            CarouselAdPo query = new CarouselAdPo();
            query.setAreaId(areaId);
            query.setStatus(carouselAdPo.getStatus());
            if(carouselAdDao.queryCarouselAdsByParam(query).size() < 2){
                throw new RuntimeException(errorMsg);
            }
        }
    }

    @Override
    public Boolean insertCarouselAd(CarouselAdPo po) {
        return carouselAdDao.insert(po) >= 1;
    }

    @Override
    public Boolean updateCarouselAd(CarouselAdPo po) {
        if(po.getStatus() != null && po.getStatus() == 0){
            checkHasOneCarouselAdInArea(po.getId(), "全国的轮播广告必须保留一条为启用状态。");
        }
        return carouselAdDao.updateByPrimaryKey(po) >= 1;
    }

    @Override
    public Boolean updateCertainCarouselAd(CarouselAdPo po) {
        if(po.getStatus() != null && po.getStatus() == 0){
            checkHasOneCarouselAdInArea(po.getId(), "全国的轮播广告必须保留一条为启用状态。");
        }
        if(po.getLinkType()!=null){
            switch (po.getLinkType()){
                case 1:
                    po.setLinkAddress(EMPTY);
                    po.setLinkKeyword(EMPTY);
                    po.setLinkId(EMPTY);
                    break;
                case 2:
                    po.setGoodsId(EMPTY);
                    po.setLinkAddress(EMPTY);
                    po.setLinkKeyword(EMPTY);
                    break;
                case 3:
                    po.setGoodsId(EMPTY);
                    po.setLinkAddress(EMPTY);
                    if(po.getLinkId() != null && !"".equals(po.getLinkId())){
                        po.setLinkKeyword(EMPTY);
                    }
                    if(po.getLinkKeyword()!=null && !"".equals(po.getLinkKeyword())){
                        po.setLinkId(EMPTY);
                    }
                    break;
                case 4:
                    setCarouselParams2Null(po);
                    break;
                case 5:
                    setCarouselParams2Null(po);
                    break;
                case 6:
                    setCarouselParams2Null(po);
                    break;
                default:
                    throw new RuntimeException("链接类型参数非法！");
            }
        }

        return carouselAdDao.updateCertainCarouselAd(po) >= 1;
    }

    private void setCarouselParams2Null(CarouselAdPo po) {
        po.setGoodsId(EMPTY);
        po.setLinkKeyword(EMPTY);
        po.setLinkId(EMPTY);
    }

    @Override
    public Boolean updateCertainQuickNavigation(QuickNavigationPo record) {
        if(record.getNavigationType()!=null){
            switch (record.getNavigationType()){
                case 1:
                    record.setLinkAddress(EMPTY);
                    record.setLinkKeyword(EMPTY);
                    record.setLinkId(EMPTY);
                    break;
                case 2:
                    record.setGoodsId(EMPTY);
                    record.setLinkAddress(EMPTY);
                    record.setLinkKeyword(EMPTY);
                    break;
                case 3:
                    record.setGoodsId(EMPTY);
                    record.setLinkAddress(EMPTY);
                    if(record.getLinkId() != null && !"".equals(record.getLinkId())){
                        record.setLinkKeyword(EMPTY);
                    }
                    if(record.getLinkKeyword()!=null && !"".equals(record.getLinkKeyword())){
                        record.setLinkId(EMPTY);
                    }
                    break;
                case 4:
                    setQuickNavParams2Null(record);
                    break;
                case 5:
                    setQuickNavParams2Null(record);
                    break;
                case 6:
                    setQuickNavParams2Null(record);
                    break;
                default:
                    throw new RuntimeException("链接类型参数非法！");
            }
        }
        return quickNavigationDao.updateCertainQuickNavigation(record) >= 1;
    }

    @Override
    public List<HomeItemAdPo> queryItemByAreaId(String areaId) {
        return homeItemAdDao.queryItemByAreaId(areaId);
    }

    @Override
    public List<CarouselAdPo> queryCarouselAdsByAreaId(String areaId) {
        return carouselAdDao.queryCarouselAdsByAreaId(areaId);
    }

    @Override
    public List<QuickNavigationPo> queryQuickNavigationsByAreaId(String areaId) {
        return quickNavigationDao.queryQuickNavigationsByAreaId(areaId);
    }

    @Override
    public List<HomeAreaAdPo> queryAreas(String companyId) {
        return homeAreaAdDao.queryAreas(companyId);
    }

    @Override
    public HomeAreaAdPo queryCarouselArea(QueryCarouselPo queryCarouselPo) {
        return homeAreaAdDao.queryCarouselArea(queryCarouselPo);
    }

    @Override
    public HomeAreaAdPo queryAreaById(String id) {
        return homeAreaAdDao.queryById(id);
    }

    @Override
    public CarouselParamPo queryCarouselInterval(String areaId) {
        return carouselParamDao.queryCarouselInterval(areaId);
    }

    @Override
    public Boolean switchOptWayOfHome(HomeAreaAdPo po) {
        if(po.getIsUsingNation()!=null && po.getIsUsingNation()==false){
            checkSwitchHomeCondition(po);
        }
        return homeAreaAdDao.switchOptWayOfHome(po)>=1;
    }

    /**
     * 检查所有坑位是否已经填满
     * @param po
     * @author liuxiaokun
     */
    private void checkSwitchHomeCondition(HomeAreaAdPo po) {
        List<HomeAreaAdPo> areas =  homeAreaAdDao.queryAreas(po.getCompanyId());
        String areaId;
        for(HomeAreaAdPo area : areas){
            if(area.getAreaType() == AreaType.BANNER.getValue()
                    || area.getAreaType() == AreaType.FLOOR.getValue()
                    || area.getAreaType() == AreaType.HOT.getValue()){
                areaId = area.getId();
                List<HomeItemAdPo> items = homeItemAdDao.queryItemByAreaId(areaId);
                for(HomeItemAdPo item : items){
                    if(item.getUrlType() == null){
                        throw new RuntimeException(item.getName()+"坑位未填满，不允许激活该运营方式。");
                    }
                }
            }else if(area.getAreaType() == AreaType.QUICK_NAV.getValue()){
                areaId = area.getId();
                List<QuickNavigationPo> items = quickNavigationDao.queryQuickNavigationsByAreaId(areaId);
                for(QuickNavigationPo item : items){
                    if(item.getNavigationType() == null){
                        throw new RuntimeException("快速导航"+item.getNavigationPosition()+"未填满，不允许激活该运营方式。");
                    }
                }
            }
        }
    }

    @Override
    public Boolean switchOptWayOfCarousel(HomeAreaAdPo po) {
        if(po.getIsUsingNation()!=null && po.getIsUsingNation()==false){
            checkSwitchCarouselCondition(po);
        }
        return homeAreaAdDao.switchOptWayOfCarousel(po)>=1;
    }

    /**
     * 检查轮播开启条件
     * @param po
     * @author liuxiaokun
     */
    private void checkSwitchCarouselCondition(HomeAreaAdPo po) {

        HomeAreaAdPo homeAreaAdPo = new HomeAreaAdPo();
        homeAreaAdPo.setCompanyId(po.getCompanyId());
        homeAreaAdPo.setAreaType(AreaType.CAROUSEL.getValue());
        List<HomeAreaAdPo> homeAreaAdPos = homeAreaAdDao.queryByParam(homeAreaAdPo);
        for(HomeAreaAdPo area : homeAreaAdPos){
            String areaId = area.getId();
            if(carouselAdDao.queryCarouselAdsByAreaId(areaId).size() < 1){
                throw new RuntimeException("没有轮播数据，不允许开启独立运营模式。");
            }
        }
    }
}
