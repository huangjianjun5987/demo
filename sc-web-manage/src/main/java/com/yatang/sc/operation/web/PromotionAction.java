package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.dto.ParticiPateDto;
import com.yatang.sc.dto.PromoQueryConditionDto;
import com.yatang.sc.dto.PromotionDto;
import com.yatang.sc.dto.QueryParticipateDataDto;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.util.excel.ExcelUtil2;
import com.yatang.sc.operation.vo.PromoQueryConditionVo;
import com.yatang.sc.operation.vo.PromotionVo;
import com.yatang.sc.operation.vo.QueryParticipateDataVo;
import com.yatang.sc.order.dubboservice.PromotionDubboService;
import com.yatang.sc.sorder.res.ActionResponse;
import com.yatang.sc.sorder.vo.SimpleProductQueryVo;
import com.yatang.sc.sorder.vo.SimpleProductVo;
import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupThree;
import com.yatang.sc.web.paramvalid.MessageConstantUtil;
import com.yatang.sc.web.paramvalid.ParamValid;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import com.yatang.xc.pi.solr.dto.PrdRecordDto;
import com.yatang.xc.pi.solr.dto.ScBackendPrdAddQueryDto;
import com.yatang.xc.pi.solr.dto.ScBackendResultDto;
import com.yatang.xc.pi.solr.dubboservice.ScBackendSearchDubboService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

/**
 * @描述: 促销控制层
 * @作者: tankejia
 * @创建时间: 2017/9/4-15:55 .
 * @版本: 1.0 .
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "/sc/promotion")
public class PromotionAction extends BaseAction {

    private final PromotionDubboService promotionDubboService;

    @Autowired
    ScBackendSearchDubboService scBackendSearchDubboService;

    /**
     * @Description: 多条件查询活动列表分页显示
     * @author tankejia
     * @date 2017/9/4- 14:10
     * @param param
     */
    @RequestMapping(value = "/queryPromotionList", method = RequestMethod.GET)
    public Response<PageResult<PromotionDto>> queryPromotionList(PromoQueryConditionVo param) {
        return promotionDubboService.listPromotions(BeanConvertUtils.convert(param,PromoQueryConditionDto.class));
    }

    /**
     * @Description: 根据活动id查询促销范围
     * @author tankejia
     * @date 2017/9/4- 15:17
     * @param promoId
     */
    @RequestMapping(value = "/queryBranchCompanyList", method = RequestMethod.GET)
    public Response<List<String>> queryBranchCompanyList(String promoId) {
        return promotionDubboService.listBranchCompany(promoId);
    }

    /**
     * @Description: 新增促销活动
     * @author tankejia
     * @date 2017/9/4- 15:26
     * @param vo
     */
    @RequestMapping(value = "/insertPromotion", method = RequestMethod.POST)
    public Response<Boolean> insertPromotion(@Validated({ GroupOne.class }) @RequestBody PromotionVo vo,HttpSession session){
        LoginInfoVO currentUser = (LoginInfoVO)
         session.getAttribute(CURRENT_USER);
         if (null == currentUser || null == currentUser.getUserId()) {
         log.error("获取用户信息出错，vo={},session={}",
         JSON.toJSONString(vo),
         JSON.toJSONString(session));
         return getFailResponse();
         }
        vo.setType(0);

        PromotionDto promotionDto=BeanConvertUtils.convert(vo,PromotionDto.class);
        promotionDto.setCreateUserId(currentUser.getUserId());
        return promotionDubboService.insertPromotion(promotionDto);
    }

    /**
     * @Description: 发布/关闭促销活动
     * @author tankejia
     * @date 2017/9/6- 11:07
     * @param vo
     */
    @RequestMapping(value = "/updatePromoStatus", method = RequestMethod.POST)
    public Response<Boolean> updatePromoStatus(@Validated({ DefaultGroup.class }) @RequestBody PromotionVo vo,HttpSession session){
        LoginInfoVO currentUser = (LoginInfoVO)
                session.getAttribute(CURRENT_USER);
        if (null == currentUser || null == currentUser.getUserId()) {
            log.error("获取用户信息出错，vo={},session={}",
                    JSON.toJSONString(vo),
                    JSON.toJSONString(session));
            return getFailResponse();
        }
        PromotionDto promotionDto=BeanConvertUtils.convert(vo,PromotionDto.class);
        promotionDto.setPublishDate(new Date());
        promotionDto.setCreateUserId(currentUser.getUserId());
        return promotionDubboService.updatePromoStatus(promotionDto);
    }

    /**
     * @Description: 查询活动详情
     * @author tankejia
     * @date 2017/9/6- 11:07
     * @param promotionId
     */
    @RequestMapping(value = "/queryPromotionDetail", method = RequestMethod.GET)
    public Response<PromotionVo> queryPromotionDetail(@RequestParam @NotBlank(message = MessageConstantUtil.NOT_EMPTY) String promotionId){
        Response<PromotionVo> response = new Response<>();
        Response<PromotionDto> dtoResponse = promotionDubboService.queryPromotionDetail(promotionId);
        response.setCode(dtoResponse.getCode());
        response.setErrorMessage(dtoResponse.getErrorMessage());
        response.setResultObject(BeanConvertUtils.convert(dtoResponse.getResultObject(), PromotionVo.class));
        response.setSuccess(dtoResponse.isSuccess());
        return response;
    }


    /**
     * 查询参与数据列表
     * @param queryParticipateDataVo
     * @return
     */
    @RequestMapping(value = "/queryParticipateData",method = RequestMethod.GET)
    public Response<ParticiPateDto> queryParticipateDataByCondition(QueryParticipateDataVo queryParticipateDataVo) throws ParseException {
        logger.info("查询参与数据列表的参数转换成dto之前{}",JSONObject.toJSONString(queryParticipateDataVo));
        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sb1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sb2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        QueryParticipateDataDto queryParticipateDataDto = BeanConvertUtils.convert(queryParticipateDataVo,QueryParticipateDataDto.class);
        if(queryParticipateDataDto.getStartTime()!=null ){
            queryParticipateDataDto.setStartTime(sb.parse(sb1.format(queryParticipateDataDto.getStartTime())));
        }
        if(queryParticipateDataDto.getEndTime() != null){
            queryParticipateDataDto.setEndTime(sb.parse(sb2.format(queryParticipateDataDto.getEndTime())));
        }
        logger.info("查询参与数据列表的参数转换成dto之后{}",JSONObject.toJSONString(queryParticipateDataDto));
        Response<ParticiPateDto> particiPateDtoResponse = promotionDubboService.queryParticipateDataByCondition(queryParticipateDataDto);
        logger.info("查询参与数据列表的结果{}",JSONObject.toJSONString(particiPateDtoResponse));
        return ActionResponse.wrap(particiPateDtoResponse);
    }

    /**
     * 导出excel
     * @param queryParticipateDataVo
     * @param response
     */
    @RequestMapping(value="/toExcel",method= RequestMethod.GET)
    public void toExcel(QueryParticipateDataVo queryParticipateDataVo,HttpServletResponse response) throws ParseException {
        SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sb1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sb2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        QueryParticipateDataDto queryParticipateDataDto = BeanConvertUtils.convert(queryParticipateDataVo,QueryParticipateDataDto.class);
        if(queryParticipateDataDto.getStartTime()!=null ){
            queryParticipateDataDto.setStartTime(sb.parse(sb1.format(queryParticipateDataDto.getStartTime())));
        }
        if(queryParticipateDataDto.getEndTime() != null){
            queryParticipateDataDto.setEndTime(sb.parse(sb2.format(queryParticipateDataDto.getEndTime())));
        }
        logger.info("查询参与数据列表的参数转换成dto之后{}",JSONObject.toJSONString(queryParticipateDataDto));
        Response<ParticiPateDto> particiPateDto = promotionDubboService.queryParticipateDataByCondition(queryParticipateDataDto);
        logger.debug("/toExcel:查询出来的数据", JSONObject.toJSONString(particiPateDto));
        if(particiPateDto.getResultObject().getParticipateDataDtoPageResult() != null){
            try{
                ExcelUtil2.listToExcel(particiPateDto.getResultObject().getParticipateDataDtoPageResult().getData(), response);
            }catch (Exception e){
                logger.error("导出异常", e);
            }
        }

    }

    /**
     * 获取所有的商品id和商品名字
     * @return
     */
    @RequestMapping(value = "/getPromotionItemsInfo" , method = RequestMethod.GET)
    public Response<PageResult<SimpleProductVo>> getAllItem(SimpleProductQueryVo simpleProductQueryVo ){
        logger.info("获取所有的商品id和商品名字条件是:{}",JSONObject.toJSONString(simpleProductQueryVo));
        // simpleProductQueryVo.setSalesInfo(simpleProductQueryVo.getSalesInfo()+"-0");
        simpleProductQueryVo.setSalesInfo(null);
        Response<PageResult<SimpleProductVo>> simpleProductVoResponse = new Response<>();
        ScBackendPrdAddQueryDto scPrdAddQueryDto = BeanConvertUtils.convert(simpleProductQueryVo,ScBackendPrdAddQueryDto.class);
        Response<ScBackendResultDto> scBackendResultDtoResponse = scBackendSearchDubboService.prdAddSearch(scPrdAddQueryDto);
        logger.info("获取所有的商品id和商品名字结果是:{}",JSONObject.toJSONString(scBackendResultDtoResponse));
        if (!scBackendResultDtoResponse.isSuccess() || scBackendResultDtoResponse.getResultObject() == null){
            logger.error("查询所有商品失败");
            simpleProductVoResponse.setSuccess(false);
            simpleProductVoResponse.setCode(CommonsEnum.RESPONSE_500.getCode());
            simpleProductVoResponse.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            simpleProductVoResponse.setResultObject(null);
            return simpleProductVoResponse;
        }
        PageResult<SimpleProductVo> simpleProductPageVo = convertScBackToSimpleProductPageVo(scBackendResultDtoResponse.getResultObject());
        simpleProductPageVo.setPageSize(simpleProductQueryVo.getPageSize());
        simpleProductVoResponse.setSuccess(true);
        simpleProductVoResponse.setCode(CommonsEnum.RESPONSE_200.getCode());
        simpleProductVoResponse.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        simpleProductVoResponse.setResultObject(simpleProductPageVo);
        return  simpleProductVoResponse;
    }

    public PageResult<SimpleProductVo> convertScBackToSimpleProductPageVo(ScBackendResultDto scBackendResultDto){
        PageResult<SimpleProductVo> simpleProductVoPageResult = new PageResult<SimpleProductVo>();
        simpleProductVoPageResult.setPageNum(scBackendResultDto.getPageNum());
        simpleProductVoPageResult.setTotal((long)scBackendResultDto.getTotalCount());
        List<SimpleProductVo>  simpleProductVos = new ArrayList<SimpleProductVo>();
        SimpleProductVo simpleProductVo;
        for(PrdRecordDto prdRecordDto:scBackendResultDto.getRecords()){
            simpleProductVo = new SimpleProductVo();
            if (StringUtils.isEmpty(prdRecordDto.getCouponId())){
                simpleProductVo.setProductCode(prdRecordDto.getProductCode());
                simpleProductVo.setProductId(prdRecordDto.getProductId());
                simpleProductVo.setProductName(prdRecordDto.getName());
                simpleProductVos.add(simpleProductVo);
            }
        }
        simpleProductVoPageResult.setData(simpleProductVos);
        return  simpleProductVoPageResult;
    }

    /**
     * 查询活动详情页修改关联的门店Id
     *
     * @param vo
     * @param session
     * @return
     */
    @RequestMapping(value = "/updateStoreId", method = RequestMethod.POST)
    public Response<Boolean> updateStoreId(@Validated({GroupThree.class}) @RequestBody PromotionVo vo, HttpSession session) {
        LoginInfoVO currentUser = (LoginInfoVO)
                session.getAttribute(CURRENT_USER);
        if (null == currentUser || null == currentUser.getUserId()) {
            log.error("获取用户信息出错，vo={},session={}",
                    JSON.toJSONString(vo),
                    JSON.toJSONString(session));
            return getFailResponse();
        }
        PromotionDto promotionDto = BeanConvertUtils.convert(vo, PromotionDto.class);
        return promotionDubboService.updateStoreId(promotionDto);
    }

    /**
     * @param
     * @Description: 批量发布/关闭促销活动或者优惠券
     * @author zhudanning
     * @date 2017/12/29- 15:22
     */
    @ParamValid
    @RequestMapping(value = "/batchUpdatePromoStatus", method = RequestMethod.POST)
    public Response<Boolean> batchUpdatePromoStatus(@NotBlank(message = MessageConstantUtil.NOT_EMPTY) String couponPromotionIds, @NotBlank String status) {
        // 获取当前用户信息
        Response<Boolean> response = new Response<>();
        LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
        if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
            response.setCode(com.yatang.sc.common.staticvalue.CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(com.yatang.sc.common.staticvalue.CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        if (!("released".equals(status) || "closed".equals(status))) {
            response.setSuccess(false);
            response.setResultObject(false);
            response.setCode(CommonsEnum.RESPONSE_400.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
            return response;
        }
        return promotionDubboService.batchUpdatePromoStatus(couponPromotionIds.split(","), status);
    }
}
