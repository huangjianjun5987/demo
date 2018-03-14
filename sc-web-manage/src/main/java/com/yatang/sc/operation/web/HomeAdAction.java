package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.dto.CarouselAdDto;
import com.yatang.sc.facade.dto.CarouselParamDto;
import com.yatang.sc.facade.dto.EditQuickNavigationStatusDto;
import com.yatang.sc.facade.dto.HomeAreaAdDto;
import com.yatang.sc.facade.dto.HomeItemAdDto;
import com.yatang.sc.facade.dto.QueryHomePageAdDto;
import com.yatang.sc.facade.dto.QueryRepeatSortingDto;
import com.yatang.sc.facade.dto.QuickNavigationDto;
import com.yatang.sc.facade.dubboservice.HomeAdQueryDubboService;
import com.yatang.sc.facade.dubboservice.HomeAdWriteDubboService;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.BaseCompanyInfo;
import com.yatang.sc.operation.vo.CarouselAdVo;
import com.yatang.sc.operation.vo.CarouselParamVo;
import com.yatang.sc.operation.vo.EditQuickNavigationStatusVo;
import com.yatang.sc.operation.vo.HomeAreaAdVo;
import com.yatang.sc.operation.vo.HomeItemAdVo;
import com.yatang.sc.operation.vo.QueryHomePageAdVo;
import com.yatang.sc.operation.vo.QueryRepeatSortingVo;
import com.yatang.sc.operation.vo.QuickNavigationVo;
import com.yatang.sc.operation.vo.prod.BranchCompanyInfoVo;
import com.yatang.sc.payment.common.ResponseUtils;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.system.dto.UserTypeCompanyDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.CompanyDubboService;
import com.yatang.xc.mbd.biz.system.dubboservice.UserDubboService;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @描述: 首页广告 HTTP API
 * @作者: yipeng
 * @创建时间: 2017年06月09日11:05:01
 * @版本: 1.0 .
 */
@Log4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping(value = "/sc/homeAd")
public class HomeAdAction extends BaseAction {
    private final HomeAdWriteDubboService homeAdWriteDubboService;
    private final HomeAdQueryDubboService homeAdQueryDubboService;
    private final OrganizationService organizationService;
    private final UserDubboService userDubboService;
    private final CompanyDubboService companyDubboService;
    private final OrganizationSCService organizationSCService;

    /**
     * @Description: 首页区域列表，包含广告
     * @author yipeng
     * @date 2017年06月12日13:59:13
     */
    @RequestMapping(value = "areaList", method = RequestMethod.GET)
    public Response<List<HomeAreaAdVo>> areaList() {
        Response<List<HomeAreaAdDto>> resultResponse = homeAdQueryDubboService.areaList();
        if (!Objects.equals(resultResponse.getCode(), "200")) {
            return BeanConvertUtils.convert(resultResponse, Response.class);
        }

        Response<List<HomeAreaAdVo>> voResponse = new Response<>();
        voResponse.setResultObject(BeanConvertUtils.convertList(resultResponse.getResultObject(), HomeAreaAdVo.class));
        voResponse.setSuccess(true);
        return voResponse;
    }

    /**
     * @Description: 移动区域
     * @author yipeng
     * @date 2017年06月12日13:59:13
     */
    @RequestMapping(value = "moveArea", method = RequestMethod.POST)
    public Response<Boolean> moveArea(@RequestBody Map<String, Object> dto) {
        String areaId = MapUtils.getString(dto, "areaId");
        Boolean isUp = MapUtils.getBoolean(dto, "isUp");
        return homeAdWriteDubboService.moveArea(areaId, isUp);
    }

    /**
     * @Description: 设置区域是否可启用
     * @author yipeng
     * @date 2017年06月12日13:59:13
     */
    @RequestMapping(value = "setAreaEnable", method = RequestMethod.POST)
    public Response<Boolean> setAreaEnable(@RequestBody Map<String, Object> dto) {
        String areaId = MapUtils.getString(dto, "areaId");
        Boolean isEnabled = MapUtils.getBoolean(dto, "isEnabled");
        return homeAdWriteDubboService.setAreaEnable(areaId, isEnabled);
    }

    /**
     * @Description: 设置首页区域
     * @author yipeng
     * @date 2017年06月12日13:59:13
     */
    @RequestMapping(value = "saveArea", method = RequestMethod.POST)
    public Response<Boolean> saveArea(@Validated({GroupOne.class}) @RequestBody HomeAreaAdVo vo) {
        HomeAreaAdDto homeAreaAdDto = BeanConvertUtils.convert(vo, HomeAreaAdDto.class);
        return homeAdWriteDubboService.saveArea(homeAreaAdDto);
    }

    /**
     * @Description: 设置广告项
     * @author yipeng
     * @date 2017年06月12日13:59:13
     */
    @RequestMapping(value = "saveItemAd", method = RequestMethod.POST)
    public Response<Boolean> saveItemAd(@Validated({GroupOne.class}) @RequestBody HomeItemAdVo vo) {
        log.info("vo:" + vo);
        return homeAdWriteDubboService.saveItemAd(BeanConvertUtils.convert(vo, HomeItemAdDto.class));
    }

    /**
     * @Description: 删除首页区域
     * @author yipeng
     * @date 2017年06月12日13:59:13
     */
    @RequestMapping(value = "deleteArea", method = RequestMethod.POST)
    public Response<Boolean> deleteArea(@RequestBody Map<String, Object> dto) {
        String areaId = MapUtils.getString(dto, "areaId");
        return homeAdWriteDubboService.deleteArea(areaId);
    }

    /**
     * @Description: 删除广告项
     * @author yipeng
     * @date 2017年06月12日13:59:13
     */
    @RequestMapping(value = "deleteItemAd", method = RequestMethod.POST)
    public Response<Boolean> deleteItemAd(@RequestBody Map<String, Object> dto) {
        String itemAdId = MapUtils.getString(dto, "itemAdId");
        return homeAdWriteDubboService.deleteItemAd(itemAdId);
    }

    /**
     * @Description: 查询快捷导航列表
     * @author tankejia
     * @date 2017/6/9- 16:25
     */
    @RequestMapping(value = "queryQuickNavigationList", method = RequestMethod.GET)
    public Response<List<QuickNavigationVo>> queryQuickNavigationList() {
        Response<List<QuickNavigationVo>> response = new Response<>();
        Response<List<QuickNavigationDto>> listResponse = homeAdQueryDubboService.queryQuickNavigationList();
        response.setSuccess(listResponse.isSuccess());
        response.setResultObject(BeanConvertUtils.convertList(listResponse.getResultObject(), QuickNavigationVo.class));
        response.setCode(listResponse.getCode());
        response.setErrorMessage(listResponse.getErrorMessage());
        return response;
    }

    /**
     * @param quickNavigationId
     * @Description: 根据id查询单个快捷导航
     * @author tankejia
     * @date 2017/6/9- 16:48
     */
    @RequestMapping(value = "queryQuickNavigation", method = RequestMethod.GET)
    public Response<QuickNavigationVo> queryQuickNavigation(@RequestParam Integer quickNavigationId) {
        Response<QuickNavigationVo> response = new Response<>();
        Response<QuickNavigationDto> dtoResponse = homeAdQueryDubboService.queryByQuickNavigationId(quickNavigationId);
        response.setSuccess(dtoResponse.isSuccess());
        response.setResultObject(BeanConvertUtils.convert(dtoResponse.getResultObject(), QuickNavigationVo.class));
        response.setCode(dtoResponse.getCode());
        response.setErrorMessage(dtoResponse.getErrorMessage());
        return response;
    }

    /**
     * @param quickNavigationVo
     * @Description: 修改快捷导航
     * @author tankejia
     * @date 2017/6/9- 16:59
     */
    @RequestMapping(value = "updateQuickNavigation", method = RequestMethod.POST)
    public Response<Boolean> updateQuickNavigation(@Valid @RequestBody QuickNavigationVo quickNavigationVo) {
        // 获取当前用户信息 TODO
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        Response response = SessionUtil.logOut(currentUser);
        if (response != null) {
            return response;
        }

        response = checkRightToModifyQuickNav(currentUser.getUserId());
        if(!response.isSuccess()){
            return response;
        }

        quickNavigationVo.setUpdatePerson(currentUser.getLoginName());
        return homeAdWriteDubboService.updateCertainQuickNavigation(BeanConvertUtils.convert(quickNavigationVo, QuickNavigationDto.class));
    }

    private Response checkRightToModifyQuickNav(String userId) {
        if(!isGlobalUser(userId)){
            Response response = getFailResponse();
            response.setErrorMessage("没有权限做此操作");
            return response;
        }

        return getSuccessResponse();
    }

    /**
     * @param
     * @Description: 批量冻结/启用快捷导航
     * @author tankejia
     * @date 2017/7/5- 10:00
     */
    @RequestMapping(value = "batchUpdateQuickNavigation", method = RequestMethod.POST)
    public Response<Boolean> batchUpdateQuickNavigation(@Validated({GroupOne.class}) @RequestBody EditQuickNavigationStatusVo record) {
        // 获取当前用户信息 TODO
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        Response response = SessionUtil.logOut(currentUser);
        if (response != null) {
            return response;
        }

        response = checkRightToModifyQuickNav(currentUser.getUserId());
        if(!response.isSuccess()){
            return response;
        }
        
        EditQuickNavigationStatusDto dto = BeanConvertUtils.convert(record, EditQuickNavigationStatusDto.class);
        return homeAdWriteDubboService.batchUpdateQuickNavigation(dto, currentUser.getLoginName());
    }

    /**
     * @Description: 查询轮播间隔时间(二期)（废弃）
     * @author tankejia
     * @date 2017/8/20- 11:25
     */
    @RequestMapping(value = "queryFirstCarouselInterval", method = RequestMethod.GET)
    public Response<CarouselParamVo> queryFirstCarouselInterval() {
        Response<CarouselParamVo> response = new Response<>();
        Response<CarouselParamDto> dtoResponse = homeAdQueryDubboService.queryFirstCarouselInterval();
        response.setCode(dtoResponse.getCode());
        response.setErrorMessage(dtoResponse.getErrorMessage());
        response.setResultObject(BeanConvertUtils.convert(dtoResponse.getResultObject(), CarouselParamVo.class));
        response.setSuccess(dtoResponse.isSuccess());
        return response;
    }

    /**
     * @param vo
     * @Description: 修改轮播间隔时间(二期)
     * @author tankejia
     * @date 2017/8/20- 11:25
     */
    @RequestMapping(value = "updateCarouselIntervalById", method = RequestMethod.POST)
    public Response<Boolean> updateCarouselIntervalById(@Validated({GroupOne.class}) @RequestBody CarouselParamVo vo) {
        return homeAdWriteDubboService.updateCarouselIntervalById(BeanConvertUtils.convert(vo, CarouselParamDto.class));
    }

    /**
     * @Description: 查询所有轮播广告（废弃）
     * @author tankejia
     * @date 2017/6/9- 17:22
     */
    /*@RequestMapping(value = "queryCarouselAdList", method = RequestMethod.GET)
    public Response<List<CarouselAdVo>> queryCarouselAdList() {
        Response<List<CarouselAdVo>> response = new Response<>();
        Response<List<CarouselAdDto>> listResponse = homeAdQueryDubboService.queryCarouselAdList();
        response.setSuccess(listResponse.isSuccess());
        response.setResultObject(BeanConvertUtils.convertList(listResponse.getResultObject(), CarouselAdVo.class));
        response.setCode(listResponse.getCode());
        response.setErrorMessage(listResponse.getErrorMessage());
        return response;
    }*/

    /**
     * @param carouselAdId
     * @Description: 根据id查询单个轮播广告
     * @author tankejia
     * @date 2017/6/9- 17:22
     */
    @RequestMapping(value = "queryCarouselAdListById", method = RequestMethod.GET)
    public Response<CarouselAdVo> queryCarouselAdById(@RequestParam Integer carouselAdId) {
        Response<CarouselAdVo> response = new Response<>();
        Response<CarouselAdDto> dtoResponse = homeAdQueryDubboService.queryCarouselAdById(carouselAdId);
        response.setSuccess(dtoResponse.isSuccess());
        response.setResultObject(BeanConvertUtils.convert(dtoResponse.getResultObject(), CarouselAdVo.class));
        response.setCode(dtoResponse.getCode());
        response.setErrorMessage(dtoResponse.getErrorMessage());
        return response;
    }

    /**
     * @param queryRepeatSortingVo
     * @Description: 判断序号是否重复（根据sorting查询单个轮播广告）
     * @author tankejia
     * @date 2017/6/9- 17:22
     */
    @RequestMapping(value = "queryCarouselAdBySorting", method = RequestMethod.GET)
    public Response<Boolean> queryCarouselAdBySorting(@Validated({GroupOne.class}) QueryRepeatSortingVo queryRepeatSortingVo) {
        return homeAdQueryDubboService.queryByCarouselAdBySorting(BeanConvertUtils.convert(queryRepeatSortingVo, QueryRepeatSortingDto.class));
    }

    /**
     * @param carouselAdVo
     * @Description: 修改单个轮播广告
     * @author tankejia
     * @date 2017/6/9- 17:19
     */
    @RequestMapping(value = "updateCarouselAd", method = RequestMethod.POST)
    public Response<Boolean> updateCarouselAd(@Validated({GroupOne.class}) @RequestBody CarouselAdVo carouselAdVo) {
        // 获取当前用户信息 TODO
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        Response response = SessionUtil.logOut(currentUser);
        if (response != null) {
            return response;
        }
        carouselAdVo.setUpdatePerson(currentUser.getLoginName());
        CarouselAdDto dto = BeanConvertUtils.convert(carouselAdVo, CarouselAdDto.class);
        return homeAdWriteDubboService.updateCertainCarouselAd(dto);
    }

    /**
     * @param carouselAdVo
     * @Description: 停用启用轮播广告
     * @author tankejia
     * @date 2017/7/8- 15:09
     */
    @RequestMapping(value = "updateCarouselAdStatus", method = RequestMethod.POST)
    public Response<Boolean> updateCarouselAdStatus(@Validated({DefaultGroup.class}) @RequestBody CarouselAdVo carouselAdVo) {
        // 获取当前用户信息 TODO
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        Response response = SessionUtil.logOut(currentUser);
        if (response != null) {
            return response;
        }
        carouselAdVo.setUpdatePerson(currentUser.getLoginName());
        CarouselAdDto dto = BeanConvertUtils.convert(carouselAdVo, CarouselAdDto.class);
        return homeAdWriteDubboService.updateCarouselAd(dto);
    }

    /**
     * @param carouselAdVo
     * @Description: 新增轮播广告
     * @author tankejia
     * @date 2017/6/9- 17:19
     */
    @RequestMapping(value = "insertCarouselAd", method = RequestMethod.POST)
    public Response<Boolean> insertCarouselAd(@Validated({GroupTwo.class}) @RequestBody CarouselAdVo carouselAdVo) {
        // 获取当前用户信息 TODO
        LoginInfoVO currentUser = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        Response response = SessionUtil.logOut(currentUser);
        if (response != null) {
            return response;
        }
        carouselAdVo.setCreatePerson(currentUser.getLoginName());
        CarouselAdDto dto = BeanConvertUtils.convert(carouselAdVo, CarouselAdDto.class);
        return homeAdWriteDubboService.insertCarouselAd(dto);
    }

    /**
     * @param map(carouselAdId)
     * @Description: 删除单个轮播广告
     * @author tankejia
     * @date 2017/6/9- 17:19
     */
    @RequestMapping(value = "deleteCarouselAd", method = RequestMethod.POST)
    public Response<Boolean> deleteCarouselAd(@RequestBody Map<String, Object> map) {
        Integer carouselAdId = MapUtils.getInteger(map, "carouselAdId");
        return homeAdWriteDubboService.deleteCarouselAd(carouselAdId);
    }

    /**
     * @Description: 切换首页运营方式
     * @author liuxiaokun
     * @date 2017/11/22
     */
    @RequestMapping(value = "switchOptWayOfHome", method = RequestMethod.POST)
    public Response<Boolean> switchOptWayOfHome(@Validated({GroupTwo.class}) @RequestBody HomeAreaAdVo homeAreaAdVo) {
        HomeAreaAdDto dto = BeanConvertUtils.convert(homeAreaAdVo, HomeAreaAdDto.class);
        return homeAdWriteDubboService.switchOptWayOfHome(dto);
    }

    /**
     * @Description: 切换轮转运营方式
     * @author liuxiaokun
     * @date 2017/11/22
     */
    @RequestMapping(value = "switchOptWayOfCarousel", method = RequestMethod.POST)
    public Response<Boolean> switchOptWayOfCarousel(@Validated({GroupTwo.class}) @RequestBody HomeAreaAdVo homeAreaAdVo) {
        HomeAreaAdDto dto = BeanConvertUtils.convert(homeAreaAdVo, HomeAreaAdDto.class);
        return homeAdWriteDubboService.switchOptWayOfCarousel(dto);
    }

    /*
     *@描述:根据公司ID和运营类型获取首页区域列表
     *@作者:tangqi
     *@时间:2017/11/21 9:52
     */
    @RequestMapping(value = "queryAreas", method = RequestMethod.GET)
    public Response<List<HomeAreaAdVo>> queryAreas(@Validated QueryHomePageAdVo queryHomePageAdVo) {
        Response<List<HomeAreaAdVo>> returnResponse = ResponseUtils.RESPONSE_200();
        Response<List<HomeAreaAdDto>> response = homeAdQueryDubboService.
                queryAreas(BeanConvertUtils.convert(queryHomePageAdVo, QueryHomePageAdDto.class));
        returnResponse.setResultObject(BeanConvertUtils.convertList(response.getResultObject(), HomeAreaAdVo.class));
        return returnResponse;
    }

    /*
     *@描述:获取轮播图区域信息
     *@作者:tangqi
     *@时间:2017/11/22 10:18
     */
    @RequestMapping(value = "queryCarouselArea", method = RequestMethod.GET)
    public Response<HomeAreaAdVo> queryCarouselAd(@Validated QueryHomePageAdVo queryHomePageAdVo){
        Response<HomeAreaAdVo> response = new Response<>();
        Response<HomeAreaAdDto> areaAdDtoResponse = homeAdQueryDubboService.queryCarouselArea(BeanConvertUtils.convert(queryHomePageAdVo, QueryHomePageAdDto.class));
        HomeAreaAdVo homeAreaAdVo = BeanConvertUtils.convert(areaAdDtoResponse.getResultObject(), HomeAreaAdVo.class);
        response.setResultObject(homeAreaAdVo);
        response.setErrorMessage(areaAdDtoResponse.getErrorMessage());
        response.setCode(areaAdDtoResponse.getCode());
        response.setSuccess(areaAdDtoResponse.isSuccess());
        return response;
    }

    @RequestMapping(value = "queryCarouselAdList", method = RequestMethod.GET)
    public Response<List<CarouselAdVo>> queryCarouselAdList(String areaId){
        Response<List<CarouselAdVo>> response = new Response<>();
        Response<List<CarouselAdDto>> responseDto = homeAdQueryDubboService.queryCarouselList(areaId);
        response.setResultObject(BeanConvertUtils.convertList(responseDto.getResultObject(), CarouselAdVo.class));
        response.setSuccess(responseDto.isSuccess());
        response.setCode(responseDto.getCode());
        response.setErrorMessage(responseDto.getErrorMessage());
        return response;
    }
    /*
     *@描述:获取子公司信息
     *@作者:tangqi
     *@时间:2017/11/21 9:52
     */
    @RequestMapping(value = "queryBranchCompanyInfo", method = RequestMethod.GET)
    public Response<List<BaseCompanyInfo>> findCompanyBaseInfo(HttpServletRequest req) {
        LoginInfoVO currentUser = (LoginInfoVO) req.getSession().getAttribute(CURRENT_USER);
        if (null==currentUser){
            log.error("session中用户信息为空");
            return this.getFailResponse();
        }
        Response<List<BaseCompanyInfo>> response = new Response<>();
        List<BaseCompanyInfo> companyList = new ArrayList<>();
        List<BranchCompanyInfoVo> branchCompanyInfoVos=this.queryUserCompanyInfosByParam(currentUser.getUserId(),null,null);
        if (branchCompanyInfoVos ==null || branchCompanyInfoVos.size()<=0){
            return response;
        }

        for (BranchCompanyInfoVo branchCompanyInfoVo : branchCompanyInfoVos){
            BaseCompanyInfo companyInfo = new BaseCompanyInfo();
            companyInfo.setId(branchCompanyInfoVo.getId());
            companyInfo.setName(branchCompanyInfoVo.getName());
            companyList.add(companyInfo);
        }
        if(isGlobalUser(currentUser.getUserId())){
            BaseCompanyInfo companyInfo = new BaseCompanyInfo();
            companyInfo.setId("headquarters");
            companyInfo.setName("全国");
            companyList.add(companyInfo);
        }
        response.setCode("200");
        response.setSuccess(true);
        response.setResultObject(companyList);
        return response;
    }

    private boolean isGlobalUser(String userId){
        boolean isGlobalUser = false;
        Response<UserTypeCompanyDTO> byUserId = companyDubboService.findUserTypeCompanyByUserId(new Integer(userId));
        if(byUserId.getResultObject() == null){
            return isGlobalUser;
        }
        UserTypeCompanyDTO companyDTO = byUserId.getResultObject();
        if(companyDTO == null){
            return isGlobalUser;
        }
        return companyDTO.isHeadQuarters();
    }

    @RequestMapping(value = "queryCarouselInterval", method = RequestMethod.GET)
    public Response<CarouselParamVo> queryCarouselInterval(String areaId) {
        Response<CarouselParamVo> response = ResponseUtils.RESPONSE_200();
        Response<CarouselParamDto> resResult = homeAdQueryDubboService.queryCarouselInterval(areaId);
        response.setResultObject(BeanConvertUtils.convert(resResult.getResultObject(), CarouselParamVo.class));
        response.setCode(resResult.getCode());
        response.setSuccess(resResult.isSuccess());
        response.setErrorMessage(resResult.getErrorMessage());
        return response;
    }
}
