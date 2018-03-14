package com.yatang.sc.payment.gateway.web.controller;

import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.payment.dto.request.PrePayRequestDto;
import com.yatang.sc.payment.dto.response.PrePayResponseDto;
import com.yatang.sc.payment.dto.response.WeiXinPrePayResponseDto;
import com.yatang.sc.payment.dubbo.service.PaymentDubboService;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.gateway.web.response.Response;
import com.yatang.sc.payment.gateway.web.vo.PrePayRequestVO;
import com.yatang.sc.payment.gateway.web.vo.PrePayResponseVO;
import com.yatang.sc.payment.gateway.web.vo.WeiXinPrePayResponseVO;
import com.yatang.sc.payment.support.PayTypeEnumPropertyEditor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by yuwei on 2017/7/7.
 */
@Controller
@RequestMapping(value = "/sc/pay")
public class PayController extends BaseController {

    @Autowired
    private PaymentDubboService mPaymentDubboService;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(PayType.class, new PayTypeEnumPropertyEditor());
    }

    @RequestMapping(value = "/prePay", method = RequestMethod.POST)
    @ResponseBody
    public PrePayResponseVO<?> prePay(@RequestBody @Validated PrePayRequestVO pPrePayVO) {
        logger.info("prePay request parameters:{}", pPrePayVO.toString());
        PrePayResponseVO<Object> voResponse =new PrePayResponseVO<>();

        PrePayRequestDto prePayRequestDto = BeanConvertUtils.convert(pPrePayVO, PrePayRequestDto.class);
        prePayRequestDto.setNonceStr(RandomStringUtils.randomAlphanumeric(32));
        com.busi.common.resp.Response<?> responseDto = mPaymentDubboService.getPrePayInfo(prePayRequestDto);
        if (!responseDto.isSuccess()) {
            return convertResponse(responseDto);
        }
        voResponse.setSuccess(true);
        voResponse.setCode(5001);
        PrePayResponseDto<?> prePayResponseDto = (PrePayResponseDto<?>) responseDto.getResultObject();
        switch (prePayResponseDto.getPayType()) {
            case weixin: {
                voResponse.setCode(200);
                WeiXinPrePayResponseDto weiXinPrePayResponseDto = (WeiXinPrePayResponseDto) prePayResponseDto.getPrePayInfo();
                WeiXinPrePayResponseVO weiXinPrePayResponseVO = BeanConvertUtils.convert(weiXinPrePayResponseDto, WeiXinPrePayResponseVO.class);
                voResponse.setData(weiXinPrePayResponseVO);
                return voResponse;
            }
            case jd: {
                voResponse.setSuccess(false);
                voResponse.setMessage("支付类型不支持。");
                return voResponse;
            }
            case alipay: {
                voResponse.setCode(200);
                voResponse.setData(responseDto.getResultObject());
                return voResponse;
            }
            case cmb: {
                voResponse.setSuccess(false);
                voResponse.setMessage("支付类型不支持。");
                return voResponse;
            }
            case ytpay: {
                voResponse.setCode(200);
                voResponse.setData(responseDto.getResultObject());
                return voResponse;
            }
        }
        voResponse.setSuccess(false);
        voResponse.setMessage("支付类型不支持。");
        return voResponse;
    }

    private <T> PrePayResponseVO<T> convertResponse(com.busi.common.resp.Response<T> pResponse){
        PrePayResponseVO<T> voResponse =new PrePayResponseVO<>();
        voResponse.setCode(Integer.valueOf(pResponse.getCode()));
        voResponse.setMessage(pResponse.getErrorMessage());
        voResponse.setData(pResponse.getResultObject());
        voResponse.setSuccess(pResponse.isSuccess());
        return voResponse;
    }
}
