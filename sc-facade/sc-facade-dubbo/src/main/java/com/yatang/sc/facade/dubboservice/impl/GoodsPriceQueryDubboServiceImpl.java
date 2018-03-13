package com.yatang.sc.facade.dubboservice.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.yatang.xc.mbd.biz.system.dto.UserDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.UserDubboService;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.oc.biz.redis.dubboservice.RedisPlatform;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.GoodsSellPriceQueryParamPo;
import com.yatang.sc.facade.domain.RegionBasicPo;
import com.yatang.sc.facade.domain.SellPriceInfoPo;
import com.yatang.sc.facade.dto.RegionBasicDto;
import com.yatang.sc.facade.dto.prod.ProSellPriceBpmDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceQueryParamDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.facade.service.GoodPriceService;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;

import lombok.extern.log4j.Log4j;

/**
 * @描述:商品价格管理
 * @类名:GoodsPriceDubboServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/5/22 20:48
 * @版本: v1.0
 */
@Log4j
@Service("goodsPriceQueryDubboService")
public class GoodsPriceQueryDubboServiceImpl implements GoodsPriceQueryDubboService {
    public static  final String PRODUCT_SELL_PRICE_CACHE_KEY_PREX = "prod_sell_";
    @Autowired
    private GoodPriceService goodPriceService;
    @Autowired
    private UserDubboService userDubboService;

    @Autowired
    RedisAdapterServie<String, ProdSellPriceInfoDto> redisDubboAdapterServie;
    @Autowired
    private  ProductScIndexDubboService indexDubboService;//调用商品dubbo服务

    /**
     * 根据商品参数查询商品价格详情
     *
     * @param
     * @return
     */
    @Override
    public Response<List<ProdSellPriceInfoDto>> getGoodsSellPriceByCondition(ProdSellPriceQueryParamDto prodSellPriceQueryParamDto) {
      log.info("parameter"+ JSON.toJSONString(prodSellPriceQueryParamDto));
       Response<List<ProdSellPriceInfoDto>> response = new Response<>();
       try {

           GoodsSellPriceQueryParamPo convert = BeanConvertUtils.convert(prodSellPriceQueryParamDto,GoodsSellPriceQueryParamPo.class);
           List<SellPriceInfoPo> goodsSellPrice = goodPriceService.getGoodsSellPrice(convert);
           List<ProdSellPriceInfoDto> prodSellPriceInfoDtos = BeanConvertUtils.convertList(goodsSellPrice,ProdSellPriceInfoDto.class);
           response.setResultObject(prodSellPriceInfoDtos);
           response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
           response.setSuccess(true);
       }catch (Exception e){
           response.setSuccess(false);
           response.setCode(CommonsEnum.RESPONSE_500.getCode());
           response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
           log.error(ExceptionUtils.getFullStackTrace(e));
       }
        return response;
    }

    /**
     * 根据商品id和公司code查询并放入redis缓存
     * @param productId
     * @param companyCode
     * @return
     */
    @Override
    public Response<ProdSellPriceInfoDto> getGoodsSellPrice(String productId, String companyCode) {
        log.info("商品id"+productId+"子公司id"+companyCode);
        Response response = new Response();
        try {

            String cacheKey = PRODUCT_SELL_PRICE_CACHE_KEY_PREX + productId + "_" + companyCode;
           // redisDubboAdapterServie.del(cacheKey);
            ProdSellPriceInfoDto sellPriceInfo = redisDubboAdapterServie.get(RedisPlatform.common,cacheKey, ProdSellPriceInfoDto.class);
            log.info("Load ProdSellPriceInfoDto from redis:" + JSON.toJSONString(sellPriceInfo));
            if (sellPriceInfo == null) {
                GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo = new GoodsSellPriceQueryParamPo();
                goodsSellPriceQueryParamPo.setProductId(productId);
                goodsSellPriceQueryParamPo.setBranchCompanyId(companyCode);
                //主数据和app端查询商品售价时，只查当前生效，不查审核中的那一套价格 yinyuxin
                goodsSellPriceQueryParamPo.setFirstCreated(0);

                List<SellPriceInfoPo> goodsSellPrice = goodPriceService.getGoodsSellPrice(goodsSellPriceQueryParamPo);

                if(goodsSellPrice != null && goodsSellPrice.size() > 0){
                    sellPriceInfo = BeanConvertUtils.convert(goodsSellPrice.get(0),ProdSellPriceInfoDto.class);
                    log.info("PUT ProdSellPriceInfoDto TO redis:" + cacheKey);
                   redisDubboAdapterServie.setex(RedisPlatform.common,cacheKey, sellPriceInfo,600);
                }

            }
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(true);
            response.setResultObject(sellPriceInfo);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }



    @Override
    public Response<ProdSellPriceInfoDto> queryPriceDetailById(Long id) {
        Response<ProdSellPriceInfoDto> response=new Response<>();
        try {
            SellPriceInfoPo sellPriceInfoPo = goodPriceService.queryPriceDetailById(id);
            ProdSellPriceInfoDto prodSellPriceInfoDto=BeanConvertUtils.convert(sellPriceInfoPo,ProdSellPriceInfoDto.class);
            if (prodSellPriceInfoDto !=null && !Strings.isNullOrEmpty(prodSellPriceInfoDto.getAuditUserId())){
                //渲染审批人姓名 yinyuxin
                UserDTO userDTO = userDubboService
                        .findUserById(Integer.valueOf(prodSellPriceInfoDto.getAuditUserId())).getResultObject();
                prodSellPriceInfoDto.setAuditUserName(userDTO==null?null:userDTO.getEmployeeName());
            }
            if (prodSellPriceInfoDto !=null && !Strings.isNullOrEmpty(prodSellPriceInfoDto.getCreateUserId())){
                //渲染创建人姓名 yinyuxin
                UserDTO userDTO = userDubboService
                        .findUserById(Integer.valueOf(prodSellPriceInfoDto.getCreateUserId())).getResultObject();
                prodSellPriceInfoDto.setCreateUserName(userDTO==null?null:userDTO.getEmployeeName());
            }
            if (prodSellPriceInfoDto !=null && !Strings.isNullOrEmpty(prodSellPriceInfoDto.getModifyUserId())){
                //渲染修改人姓名 yinyuxin
                UserDTO userDTO = userDubboService
                        .findUserById(Integer.valueOf(prodSellPriceInfoDto.getModifyUserId())).getResultObject();
                prodSellPriceInfoDto.setModifyUserName(userDTO==null?null:userDTO.getEmployeeName());
            }


            //封装 按箱销售数据 （审批页面显示需要） yinyuxin
            Response<ProductIndexDto> productIndexDtoResponse = indexDubboService
                    .queryByProductId(prodSellPriceInfoDto.getProductId());
            if (!productIndexDtoResponse.isSuccess()||null==productIndexDtoResponse.getResultObject()){
                log.error("goodsPriceQueryDubboService--queryPriceDetailById()--error:"+productIndexDtoResponse.getErrorMessage());
               throw new RuntimeException("商品基础信息查询失败");
            }
            prodSellPriceInfoDto.setSellFullCase(productIndexDtoResponse.getResultObject().getSellFullCase());
            prodSellPriceInfoDto.setFullCaseUnit(productIndexDtoResponse.getResultObject().getFullCaseUnit());

            //因为前端页面 售价详情默认有 当前使用和审批两套（即详情页有左右两套，只有右边的可以供用户修改）
            // 后台中 审批完成的售价会删掉审批信息，为了统一，当审批记录为空时，将当前记录显示为审批记录（右边显示） yinyuxin
            if (null==prodSellPriceInfoDto.getSellPricesInReview()){
                ProdSellPriceInfoDto prodSellPriceInfoDtoInReView=BeanConvertUtils.convert(prodSellPriceInfoDto,ProdSellPriceInfoDto.class);
                prodSellPriceInfoDtoInReView.setId(null);
                prodSellPriceInfoDto.setSellPricesInReview(prodSellPriceInfoDtoInReView);
            }
            response.setResultObject(prodSellPriceInfoDto);
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            return  response;
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            return response;
        }

    }



    /**
     * 分页查询查询商品价格详情（用于后台显示用，所以当前生效和审核中 两套售价都会查出  yinyuxin）
     *
     * @param prodSellPriceQueryParamDto@return
     */
    @Override
    public Response<PageResult<ProdSellPriceInfoDto>> pageQuerySellPrice(ProdSellPriceQueryParamDto prodSellPriceQueryParamDto) {
        log.info("分页查询价格详情参数"+JSON.toJSONString(prodSellPriceQueryParamDto));
        Response<PageResult<ProdSellPriceInfoDto>> response = new Response<>();
        try {

            GoodsSellPriceQueryParamPo convert = BeanConvertUtils.convert(prodSellPriceQueryParamDto,GoodsSellPriceQueryParamPo.class);
            PageInfo<SellPriceInfoPo> sellPriceInfoPoPageInfo = goodPriceService.pageQuerySellPrice(convert);
            List<ProdSellPriceInfoDto> prodSellPriceInfoDtos = BeanConvertUtils.convertList(sellPriceInfoPoPageInfo.getList(),ProdSellPriceInfoDto.class);
            PageResult p = new PageResult();
            p.setData(prodSellPriceInfoDtos);
            p.setTotal(sellPriceInfoPoPageInfo.getTotal());
            p.setPageSize(sellPriceInfoPoPageInfo.getPageSize());
            p.setPageNum(sellPriceInfoPoPageInfo.getPageNum());
            response.setResultObject(p);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    /**
     * 根据供应商id查询查询下面的市
     *
     * @param supplierId
     * @return
     * @author yangshuang
     */
    @Override
    public Response<List<RegionBasicDto>> queryRegionListBySupplierId(String supplierId) {
        Response<List<RegionBasicDto>> response = new Response<>();
        try {
            List<RegionBasicPo> regionBasicPoList = goodPriceService.queryRegionListBySupplierId(supplierId);
            //po2dto
            List<RegionBasicDto> regionBasicDtoList = BeanConvertUtils.convertList(regionBasicPoList, RegionBasicDto.class);
            response.setResultObject(regionBasicDtoList);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    /**
     * 根据多个商品id批量查询价格详情(只会查询当前生效的销售价格区间)
     *
     * @param list
     * @return
     */
    @Override
    public Response<Map<String, List<ProdSellPriceInfoDto>>> findSellPriceInfo(List<String> list) {
        log.info("商品id"+JSON.toJSONString(list));
        Response<Map<String, List<ProdSellPriceInfoDto>>> response = new Response<>();
        try {
            Map<String,List<ProdSellPriceInfoDto>> map = new HashMap<>();
            for (String id:list){
                List<ProdSellPriceInfoDto> prodSellPriceInfoDtos=BeanConvertUtils.convertList(goodPriceService.getGoodsSellPriceByProductId(id),ProdSellPriceInfoDto.class);
                map.put(id,prodSellPriceInfoDtos);
            }
            response.setResultObject(map);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 根据传入信息查询商品在该子公司是否已经配置
     *
     * @param prodSellPriceQueryParamDto
     * @return
     */
    @Override
    public Response<Integer> checkSellPriceInfo(ProdSellPriceQueryParamDto prodSellPriceQueryParamDto) {
        log.info("传入参数"+JSON.toJSONString(prodSellPriceQueryParamDto));
        Response<Integer> response = new Response();
       try {
           GoodsSellPriceQueryParamPo convert = BeanConvertUtils.convert(prodSellPriceQueryParamDto, GoodsSellPriceQueryParamPo.class);
           int i = goodPriceService.checkSellPriceInfo(convert);
           response.setResultObject(i);
           response.setSuccess(true);
           response.setCode(CommonsEnum.RESPONSE_200.getCode());
           response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
       }catch (Exception e){
           response.setSuccess(false);
           response.setCode(CommonsEnum.RESPONSE_500.getCode());
           response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
           log.error(ExceptionUtils.getFullStackTrace(e));
       }
        return response;
    }



    /**
     * 查询查询商品价格详情（用于app使用，所以只查询当前生效一套售价  yinyuxin）
     * @param branchCompanyId
     * @param productIds
     * @return
     */
    @Override
    public Response<List<ProdSellPriceInfoDto>> getGoodsSellPriceByProductIdsAndCompanyId(String branchCompanyId, List<String> productIds) {
        log.info("传入参数:" + branchCompanyId + "->" + JSON.toJSONString(productIds));
        Response<List<ProdSellPriceInfoDto>> response = new Response();
        try {
            List<SellPriceInfoPo> sellPriceInfoPos = goodPriceService.getGoodsSellPriceByProductIdsAndCompanyId(branchCompanyId, productIds);
            List<ProdSellPriceInfoDto> convert = BeanConvertUtils.convertList(sellPriceInfoPos, ProdSellPriceInfoDto.class);
            response.setResultObject(convert);
            response.setSuccess(true);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 根据售价id查询商品售价详情（提供给流程管理使用）
     *
     * @param id
     * @return
     */
    @Override
    public Response<ProSellPriceBpmDto> getProdSellPriceInfo(Long id) {
        Response response = new Response();
        try {
            SellPriceInfoPo sellPriceInfoPo = goodPriceService.queryPriceDetailById(id);
            Response<ProductIndexDto> productIndexDtoResponse = indexDubboService.queryByProductId(sellPriceInfoPo.getProductId());
            if (null==productIndexDtoResponse||null==productIndexDtoResponse.getResultObject()){
                 throw new RuntimeException("查询商品数据异常--商品id"+sellPriceInfoPo.getProductId());
            }
            ProductIndexDto resultObject = productIndexDtoResponse.getResultObject();
            ProSellPriceBpmDto sellPriceBpmDto = BeanConvertUtils.convert(sellPriceInfoPo, ProSellPriceBpmDto.class);
            sellPriceBpmDto.setModifyTime(sellPriceInfoPo.getModifyTime()==null?sellPriceInfoPo.getCreateTime():sellPriceInfoPo.getModifyTime());
            sellPriceBpmDto.setProductCode(resultObject.getProductCode());//设置商品code
            sellPriceBpmDto.setProductName(resultObject.getName());//设置商品名称
            log.info("走审批流程封装参数信息"+JSON.toJSONString(sellPriceBpmDto));
            response.setResultObject(sellPriceBpmDto);
            response.setSuccess(true);
        }catch (Exception e){
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }

        return response;
    }



    @Override
    public Response<Map<String, Integer>> querySaleInnerNumberForApp(List<String> productIds,String branchCompanyId) {
        log.info("goodsPriceQueryDubboService-->querySaleInnerNumberForApp(),param:"+ JSONObject.toJSONString(productIds)+",branchCompanyId="+branchCompanyId);
        Response<Map<String,Integer>> response=new Response<>();
        try {
            Map<String, Integer> map = goodPriceService.querySaleInnerNumberForApp(productIds, branchCompanyId);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setSuccess(true);
            response.setResultObject(map);
            return response;
        } catch (Exception e) {
            log.error("goodsPriceQueryDubboService-->querySaleInnerNumberForApp(),error:"+ExceptionUtils.getFullStackTrace(e));
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            return response;
        }
    }

}
