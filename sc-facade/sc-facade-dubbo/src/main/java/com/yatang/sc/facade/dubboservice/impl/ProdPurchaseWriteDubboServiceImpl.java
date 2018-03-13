package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.message.ProductIndexMessage;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.ProPurchaseBatchChangeStatusPo;
import com.yatang.sc.facade.domain.ProdPurchaseInfoPo;
import com.yatang.sc.facade.domain.ProdPurchaseModifyParamPo;
import com.yatang.sc.facade.dto.prod.ProPurchaseBatchChangeStatusDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseModifyParamDto;
import com.yatang.sc.facade.dubboservice.ProdPurchaseWriteDubboService;
import com.yatang.sc.facade.enums.ProdAuditStatusType;
import com.yatang.sc.facade.enums.ProdPriceCreateStatus;
import com.yatang.sc.facade.exception.ProdPurchaseException;
import com.yatang.sc.facade.flow.ProdPurchaseWriteFlowService;
import com.yatang.sc.facade.service.ProdPurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @描述: 商品采购的writeDubbo服务实现
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/15 14:10
 * @版本: v1.0
 */
@Slf4j
@Service("prodPurchaseWriteDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProdPurchaseWriteDubboServiceImpl implements ProdPurchaseWriteDubboService {
    private final ProdPurchaseService prodPurchaseService;
    @Resource(name = "productIndexMQProducer")
    private SimpleMQProducer simpleMQProducer;    // MQ消息发送


    private final ProdPurchaseWriteFlowService prodPurchaseWriteFlowService;


    @Override
    public Response<Void> addProdPurchase(ProdPurchaseInfoDto prodPurchaseInfoDto) {

        log.info("start----addProdPurchase--prodPurchaseInfoDto:{}", JSON.toJSONString(prodPurchaseInfoDto));
        Response<Void> response = new Response<>();
        try {

            return prodPurchaseWriteFlowService.addProdPurchase(prodPurchaseInfoDto);

        } catch (ProdPurchaseException e) {
            response.setCode(e.getCode());
            response.setErrorMessage(e.getMessage());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Void> updateProdPurchase(ProdPurchaseInfoDto prodPurchaseInfoDto) {

        log.info("start----updateProdPurchase--prodPurchaseInfoDto:{}", JSON.toJSONString(prodPurchaseInfoDto));
        Response<Void> response = new Response<>();
        try {
            return prodPurchaseWriteFlowService.updateProdPurchase(prodPurchaseInfoDto);
        } catch (ProdPurchaseException e) {
            response.setCode(e.getCode());
            response.setErrorMessage(e.getMessage());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    @Override
    public Response<Void> deleteProdPurchaseById(ProdPurchaseModifyParamDto prodPurchaseModifyParamDto) {
        Response<Void> response = new Response<>();
        try {

            log.info("start---deleteProdPurchaseById--prodPurchaseModifyParamDto:{}", JSON.toJSONString(prodPurchaseModifyParamDto));
            // dto2po
            ProdPurchaseModifyParamPo modifyParamPo = BeanConvertUtils.convert(prodPurchaseModifyParamDto,
                    ProdPurchaseModifyParamPo.class);

            //查询获取采购关系
            ProdPurchaseInfoPo purchaseInfoPo = prodPurchaseService.selectByPrimaryId(prodPurchaseModifyParamDto.getId());
            //控制审批
            if (ProdAuditStatusType.AUDIT_SUBMIT.getCode().equals(purchaseInfoPo.getAuditStatus())) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("当前采购关系处于审批状态,不能执行删除操作");
                response.setSuccess(false);
                return response;
            }
            boolean deleteSuccess = prodPurchaseService.deleteProdPurchaseById(modifyParamPo);
            if (deleteSuccess) {// 删除成功
                // 发送mq
                String productId = prodPurchaseModifyParamDto.getProductId();
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
                response.setErrorMessage("采购价格关系删除失败");
            }
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    @Override
    public Response<Void> changeSupplierType(ProdPurchaseModifyParamDto prodPurchaseModifyParamDto) {
        Response<Void> response = new Response<>();
        log.info("start---changeSupplierType--prodPurchaseModifyParamDto:{}", JSON.toJSONString(prodPurchaseModifyParamDto));
        try {


            // dto2po
            ProdPurchaseModifyParamPo prodPurchaseModifyParamPo = BeanConvertUtils.convert(prodPurchaseModifyParamDto,
                    ProdPurchaseModifyParamPo.class);
            boolean changeSuccess = prodPurchaseService.changeSupplierType(prodPurchaseModifyParamPo);
            if (changeSuccess) {
                String productId = prodPurchaseModifyParamDto.getProductId();
                // mq
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
                response.setErrorMessage("更新失败");
            }
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    @Override
    public Response<Void> changeProPurchaseStatus(ProdPurchaseModifyParamDto prodPurchaseModifyParamDto) {
        Response<Void> response = new Response<>();
        log.info("start---changeProPurchaseStatus--prodPurchaseModifyParamDto:{}", JSON.toJSONString(prodPurchaseModifyParamDto));
        try {


            //查询获取采购关系
            ProdPurchaseInfoPo purchaseInfoPo = prodPurchaseService.selectByPrimaryId(prodPurchaseModifyParamDto.getId());
            //控制审批
            if (ProdPriceCreateStatus.FIRST_CREATE_YES.getCode().equals(purchaseInfoPo.getFirstCreated())) {//处于新建状态 不能更改启禁用状态
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("当前新建采购关系处于审批状态,不能执行启禁用操作");
                response.setSuccess(false);
                return response;
            }
            // dto2po
            ProdPurchaseModifyParamPo prodPurchaseModifyParamPo = BeanConvertUtils.convert(prodPurchaseModifyParamDto,
                    ProdPurchaseModifyParamPo.class);
            boolean changeStatusSuccess = prodPurchaseService.changeProPurchaseStatus(prodPurchaseModifyParamPo);
            if (changeStatusSuccess) {
                String productId = prodPurchaseModifyParamDto.getProductId();
                // mq
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
                response.setErrorMessage("更新失败");
            }

        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    @Override
    public Response<Void> batchChangeProPurchaseStatus(
            ProPurchaseBatchChangeStatusDto proPurchaseBatchChangeStatusDto) {
        Response<Void> response = new Response<>();
        log.info("start---batchChangeProPurchaseStatus--proPurchaseBatchChangeStatusDto:{}", JSON.toJSONString(proPurchaseBatchChangeStatusDto));
        try {


            // dto2po
            ProPurchaseBatchChangeStatusPo proPurchaseBatchChangeStatusPo = BeanConvertUtils
                    .convert(proPurchaseBatchChangeStatusDto, ProPurchaseBatchChangeStatusPo.class);
            response.setSuccess(prodPurchaseService.batchChangeProPurchaseStatus(proPurchaseBatchChangeStatusPo));
            // 发送mq
            ProductIndexMessage productIndexMessage = new ProductIndexMessage();
            List<String> productIds = proPurchaseBatchChangeStatusDto.getProductIdList();
            productIndexMessage.setProductIds(productIds);
            simpleMQProducer.sendMsg(productIndexMessage);// sendMQ
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


    @Override
    public Response<Void> updateProdPurchaseAuditStatus(Long id, Boolean auditStatus, String auditUserId) {
        log.info("start---updateProdPurchaseAuditStatus>>更新采购关系id为:{},的审核状态:{},审核人id:{}", id, auditStatus, auditUserId);
        Response<Void> response = new Response<>();
        try {


            ProdPurchaseInfoPo prodPurchaseInfoPo = new ProdPurchaseInfoPo();
            prodPurchaseInfoPo.setId(id);
            prodPurchaseInfoPo.setAuditStatus(auditStatus ? ProdAuditStatusType.AUDIT_SUCCESS.getCode() : ProdAuditStatusType.AUDIT_REJECT.getCode());
            prodPurchaseInfoPo.setAuditUserId(auditUserId);
            prodPurchaseInfoPo.setAuditTime(new Date());

            //查询获取采购关系
            ProdPurchaseInfoPo purchaseInfoPo = prodPurchaseService.selectByPrimaryId(id);
            Integer firstCreated = purchaseInfoPo.getFirstCreated();
            //当审批状态为审核通过并且采购关系为首次创建的时候==》更新采购关系的首次创建为否
            if (ProdPriceCreateStatus.FIRST_CREATE_YES.getCode().equals(firstCreated) && auditStatus) {
                prodPurchaseInfoPo.setFirstCreated(ProdPriceCreateStatus.FIRST_CREATE_NO.getCode());
            }
            if (auditStatus) {//审核通过
                prodPurchaseInfoPo.setPurchasePrice(purchaseInfoPo.getNewestPrice());
            }
            log.info("采购价格审核请求参数:{}", JSON.toJSONString(prodPurchaseInfoPo));
            boolean updateSuccess = prodPurchaseService.updateProdPurchaseSelective(prodPurchaseInfoPo);
            //修改mq并且审核通过
            if (updateSuccess && auditStatus) {// 更新成功
                String productId = purchaseInfoPo.getProductId();
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
                response.setErrorMessage("更新失败");
            }
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


}
