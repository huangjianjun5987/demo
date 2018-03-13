package com.yatang.sc.inventory.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.inventory.domain.ImAdjustmentReceiptPo;
import com.yatang.sc.inventory.domain.im.ImAdjustmentSendMqInfo;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentDataDto;
import com.yatang.sc.inventory.dto.im.KiddImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dto.im.ImAdjustmentReceiptDto;
import com.yatang.sc.inventory.dubboservice.ImAdjustmentWriteDubboService;
import com.yatang.sc.inventory.dubboservice.flow.ImAdjustmentFlowService;
import com.yatang.sc.inventory.service.ImAdjustmentService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @描述: 库存调整写入dubbo服务实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午9:15
 * @版本: v1.0
 */
@Service("imAdjustmentWriteDubboService")
public class ImAdjustmentWriteDubboServiceImpl implements ImAdjustmentWriteDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "imAdjustmentMQProducer")
    private SimpleMQProducer imAdjustmentMQProducer;    // MQ消息发送

    @Autowired
    private ImAdjustmentService imAdjustmentService;

    @Autowired
    private ImAdjustmentFlowService adjustmentFlowService;//库存调整flowService



	@Override
	public Response<Void> addAdjustmentReceipt(ImAdjustmentReceiptDto imAdjustmentReceiptDto) {
		Response<Void> response = new Response<Void>();
		try {

			log.info("dubbo----addAdjustmentReceipt--新增库存调整单>>imAdjustmentReceiptDto:{}", imAdjustmentReceiptDto);
			// 赋值操作
			// dto2po
			ImAdjustmentReceiptPo adjustmentReceiptPo = BeanConvertUtils.convert(imAdjustmentReceiptDto,
					ImAdjustmentReceiptPo.class);
			imAdjustmentService.addSynAdjustmentReceipt(adjustmentReceiptPo);
			log.info("end----addAdjustmentReceipt");
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(false);
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Void> adjustInventoryItem(ImAdjustmentReceiptDto imAdjustmentReceiptDto) {
		log.info("dubbo---adjustInventoryItem--执行库存调整--请求参数:{}", JSON.toJSONString(imAdjustmentReceiptDto));
		return adjustmentFlowService.adjustInventoryItem(imAdjustmentReceiptDto);
	}

    @Override
    public Response<Void> acceptGLinkImAdjustmentReceipt(KiddImAdjustmentReceiptDto gLinkImAdjustmentReceipt) {
        log.info("dubbo---acceptGLinkImAdjustmentReceipt--从glink获取库存调整传输--请求参数:{}", JSON.toJSONString(gLinkImAdjustmentReceipt));
        Response<Void> response = new Response<>();
        try {
            //调整数据
            //1.数据转换 Glink传输数据调整为scm对应数据
            Response<ImAdjustmentReceiptDto> receiptDtoResponse = adjustmentFlowService.adjustImAdjustmentReceipt(gLinkImAdjustmentReceipt);
            log.info("dubbo----acceptGLinkImAdjustmentReceipt-->>数据转换结果:{}", JSON.toJSONString(receiptDtoResponse));
            //错误的响应
            if (!com.yatang.sc.inventory.common.CommonsEnum.RESPONSE_200.getCode().equals(receiptDtoResponse.getCode())) {
                return BeanConvertUtils.convert(receiptDtoResponse, Response.class);
            }
            //赋值操作
            //dto2po
            ImAdjustmentReceiptDto imAdjustmentReceiptDto = receiptDtoResponse.getResultObject();
            ImAdjustmentReceiptPo adjustmentReceiptPo = BeanConvertUtils.convert(imAdjustmentReceiptDto, ImAdjustmentReceiptPo.class);
            boolean insertSuccess = imAdjustmentService.addAdjustmentReceipt(adjustmentReceiptPo);
            if (insertSuccess) {//插入成功
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                //发送mq  json数据 将调增单信息存入转换为json数据
                ImAdjustmentSendMqInfo imAdjustmentSendMqInfo = new ImAdjustmentSendMqInfo();//mq的发送消息
                imAdjustmentSendMqInfo.setImAdjustmentReceipt(adjustmentReceiptPo);
                SendResult sendResult = imAdjustmentMQProducer.sendMsg(imAdjustmentSendMqInfo);
                log.info("dubbo----acceptGLinkImAdjustmentReceipt--新增库存调整单>> mq消息,sendResult:{}", sendResult);
            } else {//插入失败
                log.error("dubbo----acceptGLinkImAdjustmentReceipt--新增库存调整单>>插入失败");
                response.setCode(CommonsEnum.RESPONSE_20001.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_20001.getName());
            }
            log.info("end----acceptGLinkImAdjustmentReceipt");
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }


    @Override
    public Response<Void> adjustImReceipt2SCMImAdjustmentReceipt(KiddImAdjustmentDataDto kiddImAdjustmentDataDto) {
        log.info("dubbo---adjustImReceipt2SCMImAdjustmentReceipt--从glink获取库存调整传输--请求参数:{}", JSON.toJSONString(kiddImAdjustmentDataDto));
        Response<Void> response = new Response<>();
        try {
            //调整数据
            //1.数据转换 传输数据调整为scm对应数据
            KiddImAdjustmentReceiptDto kiddImAdjustmentReceiptDto = new KiddImAdjustmentReceiptDto();
            kiddImAdjustmentReceiptDto.setData(kiddImAdjustmentDataDto);
            Response<ImAdjustmentReceiptDto> receiptDtoResponse = adjustmentFlowService.adjustImAdjustmentReceipt(kiddImAdjustmentReceiptDto);
            log.info("dubbo----adjustImReceipt2SCMImAdjustmentReceipt-->>数据转换结果:{}", JSON.toJSONString(receiptDtoResponse));
            //错误的响应
            if (!com.yatang.sc.inventory.common.CommonsEnum.RESPONSE_200.getCode().equals(receiptDtoResponse.getCode())) {
                return BeanConvertUtils.convert(receiptDtoResponse, Response.class);
            }
            //赋值操作
            //dto2po
            ImAdjustmentReceiptDto imAdjustmentReceiptDto = receiptDtoResponse.getResultObject();
            ImAdjustmentReceiptPo adjustmentReceiptPo = BeanConvertUtils.convert(imAdjustmentReceiptDto, ImAdjustmentReceiptPo.class);
            imAdjustmentService.addSynAdjustmentReceipt(adjustmentReceiptPo);
            log.info("end----adjustImReceipt2SCMImAdjustmentReceipt");
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        response.setSuccess(true);
        return response;
    }


}
