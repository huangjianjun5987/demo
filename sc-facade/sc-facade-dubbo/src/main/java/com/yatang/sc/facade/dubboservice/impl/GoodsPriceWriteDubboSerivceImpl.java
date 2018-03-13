package com.yatang.sc.facade.dubboservice.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.yatang.sc.facade.enums.ProdAuditStatusType;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import com.yatang.xc.oc.biz.redis.dubboservice.RedisPlatform;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.message.ProductIndexMessage;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.bpm.dubboservice.WorkFlowDubboService;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.GoodsSellPriceQueryParamPo;
import com.yatang.sc.facade.domain.ProdBatchParameterPo;
import com.yatang.sc.facade.domain.ProdSellInfoLogPo;
import com.yatang.sc.facade.domain.SellPriceInfoPo;
import com.yatang.sc.facade.domain.SellSectionPricePo;
import com.yatang.sc.facade.dto.prod.ProdBatchParameterDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.ProdSellSectionPriceDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceWriteDubboService;
import com.yatang.sc.facade.service.GoodPriceService;
import com.yatang.sc.facade.service.ProdSellInfoLogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/5/26 20:35
 * @版本: v1.0
 */
@Slf4j
@Service("goodsPriceWriteDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GoodsPriceWriteDubboSerivceImpl implements GoodsPriceWriteDubboService {

    public static  final String PRODUCT_SELL_PRICE_CACHE_KEY_PREX = "prod_sell_";

    private final GoodPriceService goodPriceService;

    private final RedisAdapterServie redisDubboAdapterServie;

    private final ProdSellInfoLogService prodSellInfoLogService;
    @Resource(name = "productIndexMQProducer")
    private  SimpleMQProducer simpleMQProducer;//MQ消息发送

    private final WorkFlowDubboService workFlowDubboService;



    /**
     * 添加销售价格
     *
     * @param prodSellPriceInfoDto
     * @return
     */
    @Override
    public Response<Boolean> insertSellPrice(ProdSellPriceInfoDto prodSellPriceInfoDto) {
        log.info("新增价格参数：{}",JSON.toJSONString(prodSellPriceInfoDto));
        Response<Boolean> response = new Response<>();
        try {
            SellPriceInfoPo sellPriceInfoPo = BeanConvertUtils.convert(prodSellPriceInfoDto,SellPriceInfoPo.class);
            sellPriceInfoPo.setSellSectionPrices(BeanConvertUtils.convertList(prodSellPriceInfoDto.getSellSectionPrices(), SellSectionPricePo.class));
            //获取最低销售价
            BigDecimal min = getPrice(sellPriceInfoPo.getSellSectionPrices());
            sellPriceInfoPo.setLowestPrice(min);//设置最低销售区间价
            Long priceId = goodPriceService.insertSellPrice(sellPriceInfoPo);
            if (priceId==null){
                response.setCode(CommonsEnum.RESPONSE_402.getCode());
                response.setSuccess(false);
                response.setErrorMessage("添加失败该销售关系已存在");
                return response;
            }
            //发起审批流
            workFlowDubboService.saveStartProcess(priceId, 3, Integer.parseInt(sellPriceInfoPo.getBranchCompanyId()), sellPriceInfoPo.getCreateUserId());
            //记录操作日志
            ProdSellInfoLogPo prodSellInfoLogPo=new ProdSellInfoLogPo();
            prodSellInfoLogPo.setBranchCompanyId(sellPriceInfoPo.getBranchCompanyId());
            prodSellInfoLogPo.setOperateUserId(sellPriceInfoPo.getCreateUserId());
            prodSellInfoLogPo.setOperate("新增销售关系进入审批流程");
            prodSellInfoLogPo.setOperateDate(new Date());
            prodSellInfoLogPo.setLowestPrice(min);
            prodSellInfoLogPo.setProductId(sellPriceInfoPo.getProductId());
            prodSellInfoLogService.insertLog(prodSellInfoLogPo);
            response.setSuccess(true);
            response.setErrorMessage("添加成功");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("添加失败");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 删除销售价格(伪删除)
     * @param id 区域价格管理主表的id
     * @return
     */
    @Override
    public Response<Boolean> deleteSellPriceById(Long id,String productId,String userId) {
        log.info("价格id"+id+"商品id"+productId+"userId"+userId);
        Response<Boolean> response = new Response<>();
        try {
            SellPriceInfoPo goodsSellPrice = goodPriceService.getGoodsSellPriceById(id);
            if (goodsSellPrice==null){
                response.setSuccess(false);
                return response;
            }
            if (1==goodsSellPrice.getAuditStatus()){//已提交状态不能删除
                response.setCode(CommonsEnum.RESPONSE_402.getCode());
                response.setSuccess(false);
                response.setErrorMessage("改销售关系为已提交状态不能删除");
                return response;
            }
            boolean deleteSuccess = goodPriceService.deleteSellPriceById(id, userId);
            String branchCompanyId = goodsSellPrice.getBranchCompanyId();
                String key = PRODUCT_SELL_PRICE_CACHE_KEY_PREX+productId+"_"+branchCompanyId;
                redisDubboAdapterServie.del(RedisPlatform.common,key);
            // todo mq
            if (deleteSuccess) {
                 ProductIndexMessage productIndexMessage = new ProductIndexMessage();
                 List<String> productIds = new ArrayList<>();
                 productIds.add(productId);
                 productIndexMessage.setProductIds(productIds);
                 simpleMQProducer.sendMsg(productIndexMessage);//sendMQ

                //记录销售价格修改日志
                ProdSellInfoLogPo prodSellInfoLogPo=new ProdSellInfoLogPo();
                prodSellInfoLogPo.setBranchCompanyId(branchCompanyId);
                prodSellInfoLogPo.setProductId(productId);
                prodSellInfoLogPo.setLowestPrice(goodsSellPrice.getLowestPrice());
                prodSellInfoLogPo.setOperateDate(new Date());
                prodSellInfoLogPo.setOperate("删除销售价格");
                prodSellInfoLogPo.setOperateUserId(userId);
                prodSellInfoLogService.insertLog(prodSellInfoLogPo);
             }

            response.setSuccess(true);
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
     * 根据传入信息修改销售区间价状态
     *
     * @param convert
     * @return
     */
    @Override
    public Response<Boolean> updateSellPriceStatus(ProdSellPriceInfoDto convert) {
        log.info("修改商品价格参数convert {}",JSON.toJSONString(convert));
        Response<Boolean> response = new Response<>();
        try {
            SellPriceInfoPo sellPriceInfoPo1 = BeanConvertUtils.convert(convert, SellPriceInfoPo.class);
            GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo = new GoodsSellPriceQueryParamPo();
            goodsSellPriceQueryParamPo.setId(convert.getId());
            List<SellPriceInfoPo> goodsSellPrice = goodPriceService.getGoodsSellPrice(goodsSellPriceQueryParamPo);
            SellPriceInfoPo sellPriceInfoPo = goodsSellPrice.get(0);
            Boolean updateSuccess = goodPriceService.updateSellPriceStatus(sellPriceInfoPo1);
            String key = PRODUCT_SELL_PRICE_CACHE_KEY_PREX + sellPriceInfoPo.getProductId() + "_" + sellPriceInfoPo.getBranchCompanyId();
            if (updateSuccess) {
                String productId = sellPriceInfoPo.getProductId();
                ProductIndexMessage productIndexMessage = new ProductIndexMessage();
                List<String> productIds = new ArrayList<>();
                productIds.add(productId);
                productIndexMessage.setProductIds(productIds);
                log.info("修改价格mq消息"+JSON.toJSONString(productIndexMessage)+"删除redis"+key);
                simpleMQProducer.sendMsg(productIndexMessage);//sendMQ
                redisDubboAdapterServie.del(RedisPlatform.common,key);

                //记录销售价格修改日志
                ProdSellInfoLogPo prodSellInfoLogPo=new ProdSellInfoLogPo();
                prodSellInfoLogPo.setBranchCompanyId(sellPriceInfoPo.getBranchCompanyId());
                prodSellInfoLogPo.setProductId(productId);
                prodSellInfoLogPo.setLowestPrice(sellPriceInfoPo.getLowestPrice());
                prodSellInfoLogPo.setOperateDate(new Date());
                prodSellInfoLogPo.setOperate("修改销售区间价状态-->0为失效状态1为正常使用状态:"+convert.getStatus());
                prodSellInfoLogPo.setOperateUserId(convert.getModifyUserId());
                prodSellInfoLogService.insertLog(prodSellInfoLogPo);
            }
            response.setSuccess(updateSuccess);
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
     * 根据传入的信息批量修改商品在该区间的状态为不启用
     *
     * @param convert
     * @return
     */
    @Override
    public Response<Boolean> prodBatchUpdate(ProdBatchParameterDto convert) {
        log.info("批量修改状态不启用状态参数convert{}",JSON.toJSONString(convert));
        Response<Boolean> response = new Response<>();
        try {
            ProdBatchParameterPo convert1 = BeanConvertUtils.convert(convert, ProdBatchParameterPo.class);
            Boolean b = goodPriceService.prodBatchUpdate(convert1);
            if (b){
                ProductIndexMessage productIndexMessage = new ProductIndexMessage();
                List<String> productIds = convert.getProductIds();
                productIndexMessage.setProductIds(productIds);
                simpleMQProducer.sendMsg(productIndexMessage);//sendMq
                log.info("发送的mq信息productIndexMessage{}",JSON.toJSONString(productIndexMessage));
                GoodsSellPriceQueryParamPo goodsSellPriceQueryParamPo = new GoodsSellPriceQueryParamPo();
                goodsSellPriceQueryParamPo.setBranchCompanyId(convert1.getBranchCompanyId());
                for (String p:productIds){
                    goodsSellPriceQueryParamPo.setProductId(p);
                    List<SellPriceInfoPo> goodsSellPrice = goodPriceService.getGoodsSellPrice(goodsSellPriceQueryParamPo);
                    String branchCompanyId =null;
                    if (goodsSellPrice!=null&&goodsSellPrice.get(0)!=null){
                        branchCompanyId = goodsSellPrice.get(0).getBranchCompanyId();
                        String key = PRODUCT_SELL_PRICE_CACHE_KEY_PREX + p + "_" + branchCompanyId;
                        redisDubboAdapterServie.del(RedisPlatform.common,key);
                        log.info("删除redis信息key"+key);

                        //记录销售价格修改日志
                        ProdSellInfoLogPo prodSellInfoLogPo=new ProdSellInfoLogPo();
                        prodSellInfoLogPo.setBranchCompanyId(branchCompanyId);
                        prodSellInfoLogPo.setProductId(p);
                        prodSellInfoLogPo.setLowestPrice(goodsSellPrice.get(0).getLowestPrice());
                        prodSellInfoLogPo.setOperateDate(new Date());
                        prodSellInfoLogPo.setOperate("批量区域下架销售关系");
                        prodSellInfoLogPo.setOperateUserId(convert.getUserId());
                        prodSellInfoLogService.insertLog(prodSellInfoLogPo);
                    }
                }
                response.setSuccess(b);
            }
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
     * 根据传入的信息批量的添加商品与子公司的销售关系
     *
     * @param prodBatchParameterDto
     * @param addList
     * @return
     */
    @Override
    public Response<Boolean> prodBatchUpdateInfo(ProdBatchParameterDto prodBatchParameterDto, List<ProdSellPriceInfoDto> addList) {
       log.info("批量修改参数",JSON.toJSONString(prodBatchParameterDto)+"批量新增参数"+JSON.toJSONString(addList));
        Response<Boolean> response = new Response<>();
        try {
            ProdBatchParameterPo convert1 = BeanConvertUtils.convert(prodBatchParameterDto, ProdBatchParameterPo.class);
            List<SellPriceInfoPo> sellPriceInfoPos = BeanConvertUtils.convertList(addList, SellPriceInfoPo.class);
            Boolean b = goodPriceService.prodBatchUpdateInfo(convert1,sellPriceInfoPos);
            if (!b){
                throw new RuntimeException("批量上架失败");
            }
            ProductIndexMessage productIndexMessage = new ProductIndexMessage();
            List<String> productIds = prodBatchParameterDto.getProductIds();
            productIndexMessage.setProductIds(productIds);
            for (String productId:productIds){
                String key = PRODUCT_SELL_PRICE_CACHE_KEY_PREX + productId + "_" + prodBatchParameterDto.getBranchCompanyId();
                redisDubboAdapterServie.del(RedisPlatform.common,key);
            }
            simpleMQProducer.sendMsg(productIndexMessage);//只发送修改状态的商品id给主数据
            log.info("发送的mq消息"+JSON.toJSONString(productIndexMessage));
            for (SellPriceInfoPo sellPriceInfoPo:sellPriceInfoPos){
                try {
                    //发起审批流程
                    startBpm(sellPriceInfoPo);
                    //记录销售价格修改日志
                    ProdSellInfoLogPo prodSellInfoLogPo=new ProdSellInfoLogPo();
                    prodSellInfoLogPo.setBranchCompanyId(sellPriceInfoPo.getBranchCompanyId());
                    prodSellInfoLogPo.setProductId(sellPriceInfoPo.getProductId());
                    prodSellInfoLogPo.setOperateDate(new Date());
                    prodSellInfoLogPo.setOperate("批量区域上架新增销售关系");
                    prodSellInfoLogPo.setOperateUserId(sellPriceInfoPo.getCreateUserId());
                    prodSellInfoLogPo.setLowestPrice(sellPriceInfoPo.getLowestPrice());
                    prodSellInfoLogService.insertLog(prodSellInfoLogPo);
                }catch (Exception e){
                    log.error("该参数发起审批流程或记录日志失败",JSON.toJSONString(sellPriceInfoPo));
                }
            }
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
     * 修改销售价格
     *
     * @param prodSellPriceInfoDto
     * @return
     */
    @Override
    public Response<Boolean> updateSellPrice(ProdSellPriceInfoDto prodSellPriceInfoDto) {
        log.info("修改价格参数prodSellPriceInfoDto {}",JSON.toJSONString(prodSellPriceInfoDto));
        Response<Boolean> response = new Response<>();
        try {
            SellPriceInfoPo sellPo = goodPriceService.queryPriceDetailById(prodSellPriceInfoDto.getId());
            //控制审批
            if (ProdAuditStatusType.AUDIT_SUBMIT.getCode().equals(sellPo.getAuditStatus())) {
                response.setCode(CommonsEnum.RESPONSE_10019.getCode());
                response.setErrorMessage("当前销售关系处于审批状态,不能执行修改操作");
                response.setSuccess(false);
                return response;
            }
            SellPriceInfoPo sellPriceInfoPo = BeanConvertUtils.convert(prodSellPriceInfoDto,SellPriceInfoPo.class);
            sellPriceInfoPo.setModifyUserId(prodSellPriceInfoDto.getModifyUserId());
            sellPriceInfoPo.setSellSectionPrices(BeanConvertUtils.convertList(prodSellPriceInfoDto.getSellSectionPrices(), SellSectionPricePo.class));
            BigDecimal price = getPrice(sellPriceInfoPo.getSellSectionPrices());
            sellPriceInfoPo.setLowestPrice(price);//设置最低价
            boolean updateSuccess = goodPriceService.updateSellPrice(sellPriceInfoPo);
            //发起审批流程

            log.info("启动审批流--->prodSellPriceInfoDto>>请求惨，id:{},type:3,branchCompanyId:{},提交人id：{}",sellPriceInfoPo.getBranchCompanyId(),prodSellPriceInfoDto.getModifyUserId());
            workFlowDubboService.saveStartProcess(sellPriceInfoPo.getId(),3,Integer.parseInt(sellPriceInfoPo.getBranchCompanyId()),prodSellPriceInfoDto.getModifyUserId());
            //记录操作日志
            ProdSellInfoLogPo prodSellInfoLogPo=new ProdSellInfoLogPo();
            prodSellInfoLogPo.setBranchCompanyId(sellPriceInfoPo.getBranchCompanyId());
            prodSellInfoLogPo.setOperateUserId(sellPriceInfoPo.getModifyUserId());
            prodSellInfoLogPo.setOperate("修改销售价格进入审核（已提交）");
            prodSellInfoLogPo.setOperateDate(new Date());
            prodSellInfoLogPo.setLowestPrice(getPrice(sellPriceInfoPo.getSellSectionPrices()));
            prodSellInfoLogPo.setProductId(sellPriceInfoPo.getProductId());
            prodSellInfoLogService.insertLog(prodSellInfoLogPo);
            response.setSuccess(updateSuccess);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 审核完成修改状态为已完成或已拒绝
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public Response<Boolean> updateAuditSellPrice(Long id,String userId, Boolean status) {
        log.info("修改价格参数id {}",id+"审核状态"+status+"审核人"+userId);
        Response<Boolean> response = new Response<>();
        try {
            SellPriceInfoPo goodsSellPrice = goodPriceService.queryPriceDetailById(id);//查询最新版本的数据
            SellPriceInfoPo goodsSellPriceById = goodsSellPrice.getSellPricesInReview();//最新版本数据
            if (goodsSellPriceById!=null) {
                BigDecimal min = getPrice(goodsSellPriceById.getSellSectionPrices());
                goodsSellPriceById.setAuditStatus(status == true ? 2 : 3);//设置审核状态
                goodsSellPriceById.setAuditUserId(userId);//审核人id
                boolean updateSuccess = goodPriceService.updateAuditSellPrice(goodsSellPriceById);
                String key = PRODUCT_SELL_PRICE_CACHE_KEY_PREX + goodsSellPriceById.getProductId() + "_" + goodsSellPriceById.getBranchCompanyId();
                if (updateSuccess && 2 == goodsSellPriceById.getAuditStatus()) {
                    redisDubboAdapterServie.del(RedisPlatform.common,key);

                    String productId = goodsSellPriceById.getProductId();
                    ProductIndexMessage productIndexMessage = new ProductIndexMessage();
                    List<String> productIds = new ArrayList<>();
                    productIds.add(productId);
                    productIndexMessage.setProductIds(productIds);
                    simpleMQProducer.sendMsg(productIndexMessage);//sendMQ
                    log.info("修改价格mq消息productIndexMessage {}", JSON.toJSONString(productIndexMessage) + "删除redis" + key);
                }
                //记录操作日志
                ProdSellInfoLogPo prodSellInfoLogPo = new ProdSellInfoLogPo();
                prodSellInfoLogPo.setBranchCompanyId(goodsSellPriceById.getBranchCompanyId());
                prodSellInfoLogPo.setOperateUserId(goodsSellPriceById.getModifyUserId());
                prodSellInfoLogPo.setOperate("审核完成修改销售价格");
                prodSellInfoLogPo.setOperateDate(new Date());
                prodSellInfoLogPo.setLowestPrice(min);
                prodSellInfoLogPo.setProductId(goodsSellPriceById.getProductId());
                prodSellInfoLogService.insertLog(prodSellInfoLogPo);
            }
            response.setSuccess(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    /**
     * 根据传入参数修改子表信息
     *
     * @param prodSellSectionPriceDto
     * @return
     */
    @Override
    public Response<Boolean> prodSellSectionUpadte(ProdSellSectionPriceDto prodSellSectionPriceDto) {
        log.info("修改子表参数信息prodSellSectionPriceDto {}",prodSellSectionPriceDto);
        Response<Boolean> response = new Response<>();
        try {
            SellSectionPricePo sellSectionPricePo = BeanConvertUtils.convert(prodSellSectionPriceDto, SellSectionPricePo.class);
            Boolean aBoolean = goodPriceService.prodSellSectionUpadte(sellSectionPricePo);
            response.setSuccess(aBoolean);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        }catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }





    /**
     * 根据传入参数获取最低价
     * @param sectionPricePos
     * @return
     */
  private BigDecimal getPrice(List<SellSectionPricePo> sectionPricePos){
      BigDecimal min = new BigDecimal(Integer.MAX_VALUE);
      for (int i = 0; i < sectionPricePos.size(); i++) {
          BigDecimal si = sectionPricePos.get(i).getPrice();
          if (si.compareTo(min)==-1) {
              min = si;
          }
      }
      return min;
  }

    /**
     * 批量新增销售关系发起审批流程
     * @param sellPriceInfoPo
     * lvheping
     */
  private void startBpm(SellPriceInfoPo sellPriceInfoPo){
      GoodsSellPriceQueryParamPo sellPriceQueryParamPo = BeanConvertUtils.convert(sellPriceInfoPo, GoodsSellPriceQueryParamPo.class);
      List<SellPriceInfoPo> goodsSellPrice = goodPriceService.getGoodsSellPrice(sellPriceQueryParamPo);
      if (goodsSellPrice!=null&&goodsSellPrice.size()>0){
          SellPriceInfoPo priceInfoPo = goodsSellPrice.get(0);
          workFlowDubboService.saveStartProcess(priceInfoPo.getId(),3,Integer.parseInt(priceInfoPo.getBranchCompanyId()),priceInfoPo.getCreateUserId());
      }

  }

}
