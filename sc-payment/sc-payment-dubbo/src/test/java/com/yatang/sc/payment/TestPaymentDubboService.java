package com.yatang.sc.payment;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.payment.dto.request.PayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.PrePayRequestDto;
import com.yatang.sc.payment.dto.request.QueryPayStatusRequestDto;
import com.yatang.sc.payment.dto.response.WeiXinPrePayResponseDto;
import com.yatang.sc.payment.dubbo.service.PaymentDubboService;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.PayWayCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yuwei on 2017/7/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext-test.xml"})
public class TestPaymentDubboService {
    @Autowired
    private PaymentDubboService mPaymentDubboService;

    @Test
    public void testGetPrePayInf() {
        PrePayRequestDto prePayRequestDto = new PrePayRequestDto();
        prePayRequestDto.setBody("test prePayInfo");
        prePayRequestDto.setOutOrderNo("100042017110200150");
        prePayRequestDto.setPayType(PayType.ytpay);
        prePayRequestDto.setPayWayCode(PayWayCode.APP_PAY);
        prePayRequestDto.setSpbillCreateIp("127.0.0.1");
        prePayRequestDto.setTotalFee(100.0);
        prePayRequestDto.setUserName("小小面条");
        prePayRequestDto.setNonceStr(RandomStringUtils.randomAlphanumeric(32));
        Response<WeiXinPrePayResponseDto> prePayInfo = mPaymentDubboService.getPrePayInfo(prePayRequestDto);
        System.out.println(JSON.toJSONString(prePayInfo));
    }

    @Test
    public void testQueryPayStatus() {
        QueryPayStatusRequestDto queryPayStatusRequestDto = new QueryPayStatusRequestDto();
        queryPayStatusRequestDto.setPayNo("74502954127721472");
        queryPayStatusRequestDto.setNonceStr(RandomStringUtils.randomAlphanumeric(32));
        Response<?> response = mPaymentDubboService.queryPayStatus(queryPayStatusRequestDto);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testPayNotify() {
        PayNotifyRequestDto payNotifyRequestDto = new PayNotifyRequestDto();
        payNotifyRequestDto.setPayType(PayType.weixin);
        payNotifyRequestDto.put("return_code", "SUCCESS");
        payNotifyRequestDto.put("return_msg", "");
        payNotifyRequestDto.put("appid", "");
        payNotifyRequestDto.put("mch_id", "");
        payNotifyRequestDto.put("result_code", "SUCCESS");
        payNotifyRequestDto.put("err_code", "");
        payNotifyRequestDto.put("err_code_des", "");
        payNotifyRequestDto.put("trade_type", "app");
        payNotifyRequestDto.put("total_fee", "10000");
        payNotifyRequestDto.put("transaction_id", "test12345678");
        payNotifyRequestDto.put("out_trade_no", "74502954127721472");
        payNotifyRequestDto.put("time_end", "20170713134812");

        mPaymentDubboService.payNotify(payNotifyRequestDto);
    }
}
