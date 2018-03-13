package com.yatang.sc.facade.flow.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.message.ProductIndexMessage;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.bpm.dubboservice.WorkFlowDubboService;
import com.yatang.sc.common.utils.BigDemicalUtil;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.ProdPriceChangePo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoPo;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.enums.ProdAuditStatusType;
import com.yatang.sc.facade.enums.ProdPriceChangeType;
import com.yatang.sc.facade.enums.ProdPriceCreateStatus;
import com.yatang.sc.facade.exception.ProdPurchaseException;
import com.yatang.sc.facade.flow.ProdPurchaseWriteFlowService;
import com.yatang.sc.facade.service.ProdPriceChangeService;
import com.yatang.sc.facade.service.ProdPurchaseService;
import com.yatang.sc.facade.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @描述: 商品采购价格设定flow
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/26 15:08
 * @版本: v1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPurchaseWriteFlowServiceImpl implements ProdPurchaseWriteFlowService {

    @Resource(name = "productIndexMQProducer")
    private SimpleMQProducer simpleMQProducer;    // MQ消息发送
    private final ProdPurchaseService prodPurchaseService;
    private final ProdPriceChangeService priceChangeService;


    private final WorkFlowDubboService workFlowDubboService;//工作流服务


    private final SupplierService service;


    @Override
    public Response<Void> addProdPurchase(ProdPurchaseInfoDto prodPurchaseInfoDto) {

        log.info("start----addProdPurchase--prodPurchaseInfoDto:{}", JSON.toJSONString(prodPurchaseInfoDto));

        Response<Void> response = new Response<>();

        // 1.先判断当前供应商是否已经存在且处于正常的状态 供应商和status
        String spAdrId = prodPurchaseInfoDto.getSpAdrId();// 供应商地点id
        String productId = prodPurchaseInfoDto.getProductId();// 商品id
        int count = prodPurchaseService.getPurchasePriceCount(null, spAdrId, productId);
        // 当前已存在
        if (count >= 1) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage("正常状态的供应商已存在");
            return response;
        }

        //从1.2从供应商地点上面获取分公司信息
        SupplierAdrInfoPo supplierAdrInfoPo = service.queryProviderPlace(Integer.valueOf(spAdrId));
        String adrBranchCompanyId = supplierAdrInfoPo.getSpAdrBasic().getOrgId();//供应商地点上面分公司
        prodPurchaseInfoDto.setBranchCompanyId(adrBranchCompanyId);
        prodPurchaseInfoDto.setFirstCreated(ProdPriceCreateStatus.FIRST_CREATE_YES.getCode());//首次创建
        // dto2po
        ProdPurchaseInfoPo prodPurchaseInfoPo = BeanConvertUtils.convert(prodPurchaseInfoDto, ProdPurchaseInfoPo.class);
        prodPurchaseInfoPo.setAuditStatus(ProdAuditStatusType.AUDIT_SUBMIT.getCode());//设置默认为已提交


        //2.新增采购价格并记录价格变更表
        boolean insertSuccess = prodPurchaseService.addProdPurchase(prodPurchaseInfoPo);
        if (!insertSuccess) {// 插入失败
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("新增采购价格插入失败");
        } else {
            //调用插入 prod
            //  2.1.调用价格变更Service
            ProdPriceChangePo priceChangePo = new ProdPriceChangePo();
            priceChangePo.setChangeType(ProdPriceChangeType.PURCHASE_PRICE_TYPE.getCode());
            priceChangePo.setSpAdrId(prodPurchaseInfoPo.getSpAdrId());
            priceChangePo.setSpId(prodPurchaseInfoPo.getSpId());
            priceChangePo.setBranchCompanyId(adrBranchCompanyId);
            priceChangePo.setProductId(prodPurchaseInfoPo.getProductId());
            priceChangePo.setPriceId(prodPurchaseInfoPo.getId());
            priceChangePo.setPercentage(new BigDecimal(String.valueOf(0)));
            priceChangePo.setNewestPrice(prodPurchaseInfoDto.getNewestPrice());
            priceChangePo.setCreateTime(new Date());
            priceChangePo.setCreateUserId(prodPurchaseInfoPo.getCreateUserId());
            priceChangePo.setProductCode(prodPurchaseInfoDto.getProductCode());
            boolean addPriceChangeSuccess = priceChangeService.addProdPriceChange(priceChangePo);

            if (addPriceChangeSuccess) {//更新成功
                log.info("采购价格审批请求参数>>采购主键：{},请求类型:2,分公司ID:{},提交者id:{}", prodPurchaseInfoPo.getId(), prodPurchaseInfoPo.getBranchCompanyId(), prodPurchaseInfoPo.getCreateUserId());
                Response<Boolean> workFlowResponse = workFlowDubboService.saveStartProcess(prodPurchaseInfoPo.getId(), 2, Integer.parseInt(prodPurchaseInfoPo.getBranchCompanyId()), prodPurchaseInfoPo.getCreateUserId());
                log.info("采购价格审批请求参数:启动审批流返回参数:{}", JSON.toJSONString(workFlowResponse));
                if (!workFlowResponse.isSuccess()) {

                    log.error("调用工作流失败：{}", JSON.toJSONString(workFlowResponse));
                    //事务
                    throw new ProdPurchaseException(CommonsEnum.RESPONSE_500.getCode(), "启动工作流失败");
                }
            }


        }
        response.setSuccess(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());

        return response;

    }

    @Override
    public Response<Void> updateProdPurchase(ProdPurchaseInfoDto prodPurchaseInfoDto) {

        Response<Void> response = new Response<>();
        //1.判定除开当前的id是否有其他的采购关系存在
        int count = prodPurchaseService.getPurchasePriceCount(prodPurchaseInfoDto.getId(),
                prodPurchaseInfoDto.getSpAdrId(), prodPurchaseInfoDto.getProductId());
        // 当前已存在
        if (count >= 1) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage("正常状态的供应商已存在");
            return response;
        }


        //2.更新采购关系
        //2.1查询获取采购关系
        ProdPurchaseInfoPo purchaseInfoPo = prodPurchaseService.selectByPrimaryId(prodPurchaseInfoDto.getId());
        //控制审批
        if (ProdAuditStatusType.AUDIT_SUBMIT.getCode().equals(purchaseInfoPo.getAuditStatus())) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage("当前采购关系处于审批状态,不能执行修改操作");
            response.setSuccess(false);
            return response;
        }
        //2.2从供应商地点上面获取分公司信息
        SupplierAdrInfoPo supplierAdrInfoPo = service.queryProviderPlace(Integer.valueOf(prodPurchaseInfoDto.getSpAdrId()));
        //供应商地点上面分公司
        String adrBranchCompanyId = supplierAdrInfoPo.getSpAdrBasic().getOrgId();
        prodPurchaseInfoDto.setBranchCompanyId(adrBranchCompanyId);


        BigDecimal newestPrice = prodPurchaseInfoDto.getNewestPrice();
        ProdPurchaseInfoPo prodPurchaseInfoPo = BeanConvertUtils.convert(prodPurchaseInfoDto, ProdPurchaseInfoPo.class);

        //2.3首次创建
        if (ProdPriceCreateStatus.FIRST_CREATE_YES.getCode().equals(purchaseInfoPo.getFirstCreated())) {

            //此时只有最新价格 无法算百分比 获取最新的采购价格采购价格

            //2.3.1记录价格变更记录表
            ProdPriceChangePo priceChangePo = new ProdPriceChangePo();
            priceChangePo.setChangeType(ProdPriceChangeType.PURCHASE_PRICE_TYPE.getCode());
            priceChangePo.setSpAdrId(prodPurchaseInfoPo.getSpAdrId());
            priceChangePo.setSpId(prodPurchaseInfoPo.getSpId());
            priceChangePo.setBranchCompanyId(prodPurchaseInfoPo.getBranchCompanyId());
            priceChangePo.setProductId(prodPurchaseInfoPo.getProductId());
            priceChangePo.setPriceId(prodPurchaseInfoPo.getId());
            priceChangePo.setNewestPrice(newestPrice);
            priceChangePo.setCreateTime(new Date());
            priceChangePo.setCreateUserId(prodPurchaseInfoPo.getModifyUserId());
            priceChangePo.setProductCode(prodPurchaseInfoDto.getProductCode());

            boolean addPriceChangeSuccess = priceChangeService.addProdPriceChange(priceChangePo);
            //2.3.2需要走审批流
            if (addPriceChangeSuccess) {
                log.info("采购价格审批请求参数>>采购主键：{},请求类型:2,分公司ID:{},提交者id:{}", prodPurchaseInfoPo.getId(), prodPurchaseInfoPo.getBranchCompanyId(), prodPurchaseInfoPo.getModifyUserId());
                Response<Boolean> workFlowResponse = workFlowDubboService.saveStartProcess(prodPurchaseInfoPo.getId(), 2, Integer.parseInt(prodPurchaseInfoPo.getBranchCompanyId()), prodPurchaseInfoPo.getModifyUserId());
                log.info("采购价格审批请求参数:启动审批流返回参数:{}", JSON.toJSONString(workFlowResponse));
                if (!workFlowResponse.isSuccess()) {
                    log.error("调用工作流失败：{}", JSON.toJSONString(workFlowResponse));
                    throw new ProdPurchaseException(CommonsEnum.RESPONSE_500.getCode(), "启动审批流失败");
                }
            }
            //2.3.3更新审批状态为已提交
            prodPurchaseInfoPo.setAuditStatus(ProdAuditStatusType.AUDIT_SUBMIT.getCode());

        }
        //2.4非首次创建
        else {
            //获取当前采购价格
            BigDecimal purchasePrice = purchaseInfoPo.getPurchasePrice();
            // 有更新采购价格操作(调增或者调减)
            if (0 != purchasePrice.compareTo(newestPrice)) {
                //相减
                double subPrice = BigDemicalUtil.sub(newestPrice.doubleValue(), purchasePrice.doubleValue());
                BigDecimal bigPercentage = null;
                try {
                    //扩大保留四位有效数字 100倍 10.24%
                    double percentageValue = BigDemicalUtil.div(subPrice, purchasePrice.doubleValue(), 4) * 100;
                    bigPercentage = new BigDecimal(String.valueOf(percentageValue));
                    prodPurchaseInfoPo.setPercentage(bigPercentage);
                } catch (IllegalAccessException e) {
                    log.error(ExceptionUtils.getFullStackTrace(e));

                }

                //2.4.1记录价格变更记录表
                ProdPriceChangePo priceChangePo = new ProdPriceChangePo();
                priceChangePo.setChangeType(ProdPriceChangeType.PURCHASE_PRICE_TYPE.getCode());
                priceChangePo.setSpAdrId(prodPurchaseInfoPo.getSpAdrId());
                priceChangePo.setSpId(prodPurchaseInfoPo.getSpId());
                priceChangePo.setBranchCompanyId(prodPurchaseInfoPo.getBranchCompanyId());
                priceChangePo.setProductId(prodPurchaseInfoPo.getProductId());
                priceChangePo.setPriceId(prodPurchaseInfoPo.getId());
                priceChangePo.setPercentage(bigPercentage);
                priceChangePo.setPrice(purchasePrice);
                priceChangePo.setNewestPrice(newestPrice);
                priceChangePo.setCreateTime(new Date());
                priceChangePo.setCreateUserId(prodPurchaseInfoPo.getModifyUserId());
                priceChangePo.setProductCode(prodPurchaseInfoDto.getProductCode());

                boolean addPriceChangeSuccess = priceChangeService.addProdPriceChange(priceChangePo);
                //2.4.2需要走审批流
                if (addPriceChangeSuccess) {
                    log.info("采购价格审批请求参数>>采购主键：{},请求类型:2,分公司ID:{},提交者id:{}", prodPurchaseInfoPo.getId(), prodPurchaseInfoPo.getBranchCompanyId(), prodPurchaseInfoPo.getModifyUserId());
                    Response<Boolean> workFlowResponse = workFlowDubboService.saveStartProcess(prodPurchaseInfoPo.getId(), 2, Integer.parseInt(prodPurchaseInfoPo.getBranchCompanyId()), prodPurchaseInfoPo.getModifyUserId());
                    log.info("采购价格审批请求参数:启动审批流返回参数:{}", JSON.toJSONString(workFlowResponse));
                    if (!workFlowResponse.isSuccess()) {
                        log.error("调用工作流失败：{}", JSON.toJSONString(workFlowResponse));
                        throw new ProdPurchaseException(CommonsEnum.RESPONSE_500.getCode(), "启动审批流失败");
                    }
                }
                //2.4.3更新审批状态为已提交
                prodPurchaseInfoPo.setAuditStatus(ProdAuditStatusType.AUDIT_SUBMIT.getCode());

            }
            //普通更新
            else {
                ////设置最新的采购价格为空
                prodPurchaseInfoDto.setNewestPrice(null);
            }
        }
        //设置采购为空
        prodPurchaseInfoDto.setPurchasePrice(null);
        boolean updateSuccess = prodPurchaseService.updateProdPurchaseSelective(prodPurchaseInfoPo);
        if (updateSuccess) {
            String productId = prodPurchaseInfoDto.getProductId();
            ProductIndexMessage productIndexMessage = new ProductIndexMessage();
            List<String> productIds = new ArrayList<>();
            productIds.add(productId);
            productIndexMessage.setProductIds(productIds);
            simpleMQProducer.sendMsg(productIndexMessage);// sendMQ
            response.setSuccess(true);
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } else {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage("采购更新失败");
        }
        return response;
    }
}
