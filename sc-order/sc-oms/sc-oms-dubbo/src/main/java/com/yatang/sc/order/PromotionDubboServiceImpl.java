package com.yatang.sc.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Objects;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.staticvalue.Constants;
import com.yatang.sc.dto.ParticiPateDto;
import com.yatang.sc.dto.ParticipateDataDto;
import com.yatang.sc.dto.PromoQueryConditionDto;
import com.yatang.sc.dto.PromotionDto;
import com.yatang.sc.dto.QueryParticipateDataDto;
import com.yatang.sc.order.domain.PromoCompaniesPo;
import com.yatang.sc.order.domain.PromoQueryConditionPo;
import com.yatang.sc.order.domain.PromotionPo;
import com.yatang.sc.order.dubboservice.PromotionDubboService;
import com.yatang.sc.order.service.PromotionService;
import com.yatang.sc.util.IdGeneratorUtil;
import com.yatang.sc.util.MyBeanUtils;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("promotionDubboService")
public class PromotionDubboServiceImpl implements PromotionDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private OrganizationService orgService;

    @Autowired
    private RedisAdapterServie<String, String> redisDubboAdapterServie; // 用于存放促销活动编号

    @Autowired
    private IdGeneratorUtil idGeneratorUtil;

    @Autowired
    private OrganizationSCService organizationSCService;

    @Override
    public Response<PageResult<PromotionDto>> listPromotions(PromoQueryConditionDto record) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<查询促销活动列表>> listPromotions(PromoQueryConditionDto record): record="
                    + JSON.toJSONString(record) + "----------");
        }

        Response<PageResult<PromotionDto>> response = new Response();

        try {
            PromoQueryConditionPo po = BeanConvertUtils.convert(record, PromoQueryConditionPo.class);
            //查询已结束状态的促销活动
            if (Objects.equal("ended",po.getStatus())){
                po.setEndDate(new Date());
                po.setStatus(null);
            }else if (!Objects.equal(null,po.getStatus())){
                po.setStartDate(new Date());
            }
            PageInfo<PromotionPo> list = promotionService.listPromotions(po);
            if (list!=null && list.getList()!=null && list.getList().size()>0){
                for (PromotionPo promotionPo:list.getList()){
                    Date date = new Date();
                    if (promotionPo.getEndDate()!=null && promotionPo.getEndDate().getTime()<date.getTime()){
                        promotionPo.setStatus("ended");
                    }
                }
            }
            List<PromotionDto> DtoList = BeanConvertUtils.convertList(list.getList(), PromotionDto.class);
            PageResult<PromotionDto> pageResult = new PageResult();
            pageResult.setPageNum(list.getPageNum());
            pageResult.setPageSize(list.getPageSize());
            pageResult.setTotal(list.getTotal());
            pageResult.setData(DtoList);
            response.setSuccess(true);
            response.setResultObject(pageResult);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());

        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<List<String>> listBranchCompany(String promoId) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<根据活动id查询促销范围>> listBranchCompany(String promoId): promoId="
                    + promoId + "----------");
        }

        Response<List<String>> response = new Response();
        List<String> list = new ArrayList<String>();
        try {
            // 根据活动ID查询出子公司id集合
            List<PromoCompaniesPo> poList = promotionService.listBranchCompany(promoId);
            if (null != poList && poList.size() > 0) {
                for (PromoCompaniesPo po : poList) {
                    //根据子公司id查其名称
                    list.add(orgService.querySimpleByBranchCompanyId(po.getCompanyId()).getResultObject().getName());
                }
                response.setResultObject(list);
            } else {
                response.setErrorMessage("全区域");
            }
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Boolean> insertPromotion(PromotionDto dto) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<新增促销活动>> insertPromotion(PromotionDto dto): dto="
                    + JSON.toJSONString(dto) + "----------");
        }
        Response<Boolean> response = new Response();
        try {
            String no=idGeneratorUtil.getIdGeneratorId("HD");
            if (StringUtils.isBlank(no)){
                log.error("---------- <<新增促销活动>> insertPromotion(PromotionDto dto): 主键生成错误");
                throw new  RuntimeException("---------- <<新增促销活动>> insertPromotion(PromotionDto dto): 主键生成错误");
            }
            dto.setId(no);
            dto.setDiscountType("percentage");
            dto.setPromotionType("order_promo");
            dto.setCreateDate(new Date());
            Boolean flag = promotionService.insertPromotion(BeanConvertUtils.convert(dto, PromotionPo.class));
            if (!flag) {
                response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
            }
            response.setSuccess(flag);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(null);
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Boolean> updatePromoStatus(PromotionDto dto) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<发布/关闭促销活动>> updatePromoStatus(PromotionDto dto): dto="
                    + JSON.toJSONString(dto) + "----------");
        }
        Response<Boolean> response = new Response();
        try {
            Boolean flag = promotionService.updatePromoStatus(BeanConvertUtils.convert(dto, PromotionPo.class));
            if (flag) {
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setCode(CommonsEnum.RESPONSE_400.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
            }
            response.setSuccess(flag);
            response.setResultObject(null);
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<PromotionDto> queryPromotionDetail(String promotionId) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<查询活动详情>> queryPromotionDetail(String promotionId): promotionId="
                    + promotionId + "----------");
        }
        Response<PromotionDto> response = new Response();
        try {
            response.setResultObject(BeanConvertUtils.convert(promotionService.queryPromotionDetail(promotionId), PromotionDto.class));
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 根据条件查询参与数据列表
     * @param queryParticipateDataDto
     * @return
     */
    @Override
    public Response<ParticiPateDto> queryParticipateDataByCondition(QueryParticipateDataDto queryParticipateDataDto) {

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.debug("method:queryParticipateDataByConditionparam{}",JSONObject.toJSONString(queryParticipateDataDto));
        Response<ParticiPateDto> response = new Response<ParticiPateDto>();
        ParticiPateDto particiPateDto = new ParticiPateDto();
        try {
            //查询活动名称
            String id =queryParticipateDataDto.getPromoId();
            PromotionPo promotionPo = promotionService.queryById(id);
            if (promotionPo != null){
                particiPateDto.setPromotionName(promotionPo.getPromotionName());
            }
            Map<String, Object> queryParticipateDataMap = MyBeanUtils.beanToMap(queryParticipateDataDto);
            //查询分页
            PageResult<ParticipateDataDto> pageResult = new PageResult<ParticipateDataDto>();
            queryParticipateDataDto.setPageNum(queryParticipateDataDto.getPageNum()==null?
                    Integer.valueOf(CommonsEnum.RESPONSE_PAGE_NUM.getCode()) :queryParticipateDataDto.getPageNum());
            queryParticipateDataDto.setPageSize(queryParticipateDataDto.getPageSize()==null?
                    Integer.valueOf(CommonsEnum.RESPONSE_PAGE_SIZE.getCode()) :queryParticipateDataDto.getPageSize());
            queryParticipateDataMap.put("start", (queryParticipateDataDto.getPageNum() - 1) * queryParticipateDataDto.getPageSize());
            queryParticipateDataMap.put("end", queryParticipateDataDto.getPageSize());
            log.info("queryParticipateDataMap{}",JSONObject.toJSONString(queryParticipateDataMap));
            Long total = promotionService.getParticipateDataPageListCount(queryParticipateDataMap);
            List<Map<String,String>> participateDataList = promotionService.getParticipateDataPageList(queryParticipateDataMap);
            List<ParticipateDataDto> participateDataDtos = new ArrayList<ParticipateDataDto>();
            ParticipateDataDto participateDataDto;
            //根据门店编号查询门店名称
            Map<String,Object> storeIdMap = new HashMap<String,Object>();
            for (Map<String, String> map : participateDataList) {
                storeIdMap.put("idOrName",map.get("franchiseeStoreId"));
                Response<List<StoreDto>> resStoreDtos = organizationSCService.queryPageSCStoreByIdOrName(1,1,storeIdMap);
                map.put("franchiseeStoreName",resStoreDtos.getResultObject().get(0).getName());
            }
            for (Map<String, String> map : participateDataList) {
                participateDataDto = BeanConvertUtils.convert(map, ParticipateDataDto.class);
                participateDataDtos.add(participateDataDto);
                fillParticipateData(participateDataDto);
            }
            pageResult.setPageSize(queryParticipateDataDto.getPageSize());
            pageResult.setPageNum(queryParticipateDataDto.getPageNum());
            pageResult.setTotal(total);
            pageResult.setData(participateDataDtos);
            particiPateDto.setParticipateDataDtoPageResult(pageResult);
            log.info("ParticipateDataPageResult{}",JSONObject.toJSONString(pageResult));
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(particiPateDto);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<List<PromotionDto>> listAvailablePromotions(String branchCompanyId, String promoType) {
        //若参数为空字符串则转换成null
        if (branchCompanyId != null && branchCompanyId.trim().length() == 0) { branchCompanyId = null; }
        if (promoType != null && promoType.trim().length() == 0) { promoType = null; }
        Response<List<PromotionDto>> response = new Response();
        try {
            List<PromotionPo> listPromotionPo = promotionService.queryAvailablePromotions(branchCompanyId, promoType);
            List<PromotionDto> listPromotionDto = BeanConvertUtils.convertList(listPromotionPo, PromotionDto.class);
            response.setSuccess(true);
            response.setResultObject(listPromotionDto);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Boolean> updateStoreId(PromotionDto dto) {
        if (log.isInfoEnabled()) {
            log.info("---------- <<查询活动详情页修改关联的门店Id>> updateStoreId(PromotionDto dto): dto="
                    + JSON.toJSONString(dto) + "----------");
        }
        Response<Boolean> response = new Response();
        try {
            Boolean flag = promotionService.updateStoreId(BeanConvertUtils.convert(dto, PromotionPo.class));
            if (flag) {
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setCode(CommonsEnum.RESPONSE_400.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
            }
            response.setSuccess(flag);
            response.setResultObject(null);
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 批量修改活动或者优惠券的状态
     *
     * @param couponPromotionIds
     * @return
     */
    @Override
    public Response<Boolean> batchUpdatePromoStatus(String[] couponPromotionIds, String status) {
        if (log.isInfoEnabled()) {
            log.info("<<批量修改活动或者优惠券的状态>> 需要修改的id值：{} , 修改成的状态：{}", JSONObject.toJSONString(couponPromotionIds), status);
        }
        Response<Boolean> response = new Response();
        Boolean flag;
        try {
            flag = promotionService.batchUpdatePromoStatus(couponPromotionIds, status);
            if (!flag) {
                response.setSuccess(false);
                response.setResultObject(flag);
                response.setCode(CommonsEnum.RESPONSE_400.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
                return response;
            }
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
            return response;
        }
        response.setSuccess(true);
        response.setResultObject(flag);
        response.setResultObject(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return response;
    }


    /**
     * 将订单转换成前端所需要的参与数据
     * @param
     * @return
     * @throws ParseException
     */
    public void fillParticipateData(ParticipateDataDto participateDataDto) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Response<BranchCompanyDto> companyRep = orgService.querySimpleByBranchCompanyId(participateDataDto.getBranchCompanyId());
        if(companyRep.isSuccess()&&companyRep.getResultObject()!=null){
            participateDataDto.setBranchCompanyName(companyRep.getResultObject().getName());
        }
        if(participateDataDto.getSubmitTime()!=null){
            participateDataDto.setSubmitTime(sf.parse(sf.format(participateDataDto.getSubmitTime())));
        }
    }



}
