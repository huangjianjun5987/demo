package com.yatang.sc.operation.web;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.AdPlanDto;
import com.yatang.sc.facade.dto.AdPlanModifyParamDto;
import com.yatang.sc.facade.dubboservice.AdPlanQueryDubboService;
import com.yatang.sc.facade.dubboservice.AdPlanWriteDubboService;
import com.yatang.sc.operation.util.ImageDomainJoinUrlUtil;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.vo.AdPlanModifyParamVo;
import com.yatang.sc.operation.vo.AdPlanVo;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @描述: 404广告配置Action
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/6/8 10:08
 * @版本: v1.0
 */
@Log4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/adPlan")
public class AdPlanManageAction extends BaseAction {

    private final AdPlanQueryDubboService adPlanQueryDubboService;
    private final AdPlanWriteDubboService adPlanWriteDubboService;


    private final ImageDomainJoinUrlUtil imageDomainJoinUrlUtil;//图片地址
    /**
     * 新增广告方案
     *
     * @param adPlanVo 广告方案的vo
     * @return
     * @author yangshuang
     */
    @RequestMapping(value = "/addAdPlan", method = RequestMethod.POST)
    public Response<Void> addAdPlan(@RequestBody @Validated({DefaultGroup.class}) AdPlanVo adPlanVo) {
        log.info("sc/adPlan/addAdPlan->adPlanVo:" + adPlanVo);
        Response<Void> response;
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response = new Response<>();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        adPlanVo.setCreateUserId(loginInfoVO.getUserId());
      /*  adPlanVo.setPicUrl1(imageDomainJoinUrlUtil.getRelativePath(adPlanVo.getPicUrl1()));
        adPlanVo.setPicUrl2(imageDomainJoinUrlUtil.getRelativePath(adPlanVo.getPicUrl2()));*/
//        adPlanVo.set
//        adPlanVo.setCreateUserId("create_user_id");
        //vo2Dto
        AdPlanDto adPlanDto = BeanConvertUtils.convert(adPlanVo, AdPlanDto.class);
        response = adPlanWriteDubboService.addAdPlan(adPlanDto);
        return response;
    }

    /**
     * 根据id查询广告方案
     *
     * @param id 广告方案的主键
     * @return
     * @author yangshuang
     */
    @RequestMapping(value = "/getAdPlanById", method = RequestMethod.GET)
    @ParamValid
    public Response<AdPlanVo> getAdPlanById(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) @Range(message = MessageConstantUtil.ID_RANGE, min = 1,max = Integer.MAX_VALUE) Integer id) {
        log.info("sc/adPlan/getAdPlanById/{id}->id:" + id);
        Response<AdPlanVo> response = new Response<>();
        Response<AdPlanDto> planDtoResponse = adPlanQueryDubboService.getAdPlanById(id);
        if (!planDtoResponse.isSuccess()) {
            return BeanConvertUtils.convert(planDtoResponse, Response.class);
        }
        AdPlanDto adPlanDto = planDtoResponse.getResultObject();
        //dto2Vo
        AdPlanVo adPlanVo = BeanConvertUtils.convert(adPlanDto, AdPlanVo.class);

        //拼接绝对路径
       /* adPlanVo.setPicUrl1(imageDomainJoinUrlUtil.getImageUrl(adPlanVo.getPicUrl1()));
        adPlanVo.setPicUrl2(imageDomainJoinUrlUtil.getImageUrl(adPlanVo.getPicUrl2()));*/
        response.setResultObject(adPlanVo);
        response.setSuccess(planDtoResponse.isSuccess());
        response.setErrorMessage(planDtoResponse.getErrorMessage());
        response.setCode(planDtoResponse.getCode());
        return response;
    }

    /**
     * 删除广告方案
     *
     * @param id 广告方案的主键
     * @return
     * @author yangshuang
     */
    @RequestMapping(value = "/deleteAdPlanById", method = RequestMethod.GET)
    @ParamValid
    public Response<Void> deleteAdPlanById(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) @Range(message = MessageConstantUtil.ID_RANGE, min = 0, max = Integer.MAX_VALUE) Integer id) {
        log.info("sc/adPlan/queryAllAdPlanList/{id}->id:" + id);
        Response<Void> response = adPlanWriteDubboService.deleteAdPlanById(id);
        return response;
    }

    /**
     * 查询所有的广告方案
     *
     * @return
     * @author yangshuang
     */
    @RequestMapping(value = "/queryAllAdPlanList", method = RequestMethod.GET)
    public Response<List<AdPlanVo>> queryAllAdPlanList() {
        log.info("sc/adPlan/queryAllAdPlanList");
        Response<List<AdPlanVo>> response = new Response<>();
        Response<List<AdPlanDto>> dtoResponse = adPlanQueryDubboService.queryAllAdPlanList();
        if (!dtoResponse.isSuccess()) {//失败的情况下
            return BeanConvertUtils.convert(dtoResponse, Response.class);
        }
        List<AdPlanDto> adPlanDtoList = dtoResponse.getResultObject();
        //dto2vo
        List<AdPlanVo> adPlanVoList = BeanConvertUtils.convertList(adPlanDtoList, AdPlanVo.class);

      /*  //拼接绝对路径
        for (AdPlanVo adPlanVo : adPlanVoList) {
            adPlanVo.setPicUrl1(imageDomainJoinUrlUtil.getImageUrl(adPlanVo.getPicUrl1()));
            adPlanVo.setPicUrl2(imageDomainJoinUrlUtil.getImageUrl(adPlanVo.getPicUrl2()));
        }*/
        response.setResultObject(adPlanVoList);
        response.setSuccess(dtoResponse.isSuccess());
        response.setErrorMessage(dtoResponse.getErrorMessage());
        return response;
    }

    /**
     * 根据 广告方案id和启用停用类型修改广告状态
     *
     * @param id     广告方案id
     * @param status 类型 0,停用,1,启用
     * @return
     * @author yangshuang
     */
    @RequestMapping(value = "/changeAdPlanState", method = RequestMethod.GET)
    @ParamValid
    public Response<Void> changeAdPlanState(@RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) Integer id, @RequestParam @NotNull(message = MessageConstantUtil.NOT_EMPTY) @Range(min = 0, max = 1, message = MessageConstantUtil.AD_PLAN_STATUS_RANGE) Integer status) {
        logger.info("sc/adPlan/changeAdPlanState/{id}/{type}->" + "id:" + id + ": type->" + status);
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            Response<Void>   response = new Response<>();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }

        AdPlanModifyParamVo adPlanModifyParamVo = new AdPlanModifyParamVo();

        adPlanModifyParamVo.setId(id);
        adPlanModifyParamVo.setModifyUserId(loginInfoVO.getUserId());
//        adPlanModifyParamVo.setModifyUserId("changeUserId");
        adPlanModifyParamVo.setStatus(status);
        AdPlanModifyParamDto modifyParamDto = BeanConvertUtils.convert(adPlanModifyParamVo, AdPlanModifyParamDto.class);
        return adPlanWriteDubboService.changeAdPlanState(modifyParamDto);
    }

    /**
     * 根据id更新广告方案
     *
     * @param adPlanVo
     * @return
     * @author yangshuang
     */
    @RequestMapping(value = "/updateAdPlan", method = RequestMethod.POST)
    public Response<Void> updateAdPlan(@RequestBody @Validated({GroupOne.class})  AdPlanVo adPlanVo) {
        log.info("sc/adPlan/updateAdPlan/adPlanVo->adPlanVo" + adPlanVo);
        //vo2Dto
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
         Response<Void>   response = new Response<>();
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
    /*    adPlanVo.setPicUrl1(imageDomainJoinUrlUtil.getRelativePath(adPlanVo.getPicUrl1()));
        adPlanVo.setPicUrl2(imageDomainJoinUrlUtil.getRelativePath(adPlanVo.getPicUrl2()));*/
        adPlanVo.setCreateUserId(loginInfoVO.getUserId());
//        adPlanVo.setModifyUserId("update_user_id");
        AdPlanDto adPlanDto = BeanConvertUtils.convert(adPlanVo, AdPlanDto.class);
        return adPlanWriteDubboService.updateAdPlan(adPlanDto);
    }


}
