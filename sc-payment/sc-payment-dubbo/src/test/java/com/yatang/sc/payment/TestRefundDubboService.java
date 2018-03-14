package com.yatang.sc.payment;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.payment.dto.request.ApplyRefundRequestDto;
import com.yatang.sc.payment.dto.request.AuditRefundRequestDto;
import com.yatang.sc.payment.dto.request.RefundRequestDto;
import com.yatang.sc.payment.dto.response.RefundResponseDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.PayType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext-test.xml"})
public class TestRefundDubboService {

    @Autowired
    private RefundDubboService mRefundDubboService;

    @Test
    public void testApplyRefund() {
        ApplyRefundRequestDto refundRequestDto = new ApplyRefundRequestDto();
        refundRequestDto.setPayType(PayType.weixin);
        refundRequestDto.setPayTradeNo("test12345678");
        refundRequestDto.setRefundAmount(100D);
        refundRequestDto.setRefundUserId("SYSTEM");
        refundRequestDto.setRefundUserName("SYSTEM");
        refundRequestDto.setTotalAmount(10000D);
        refundRequestDto.setPayNo("74178601381004288");
        refundRequestDto.setOrderNo("o12345678");
        refundRequestDto.setRemark("测试");
        refundRequestDto.setNonceStr(RandomStringUtils.randomAlphanumeric(32));

        Response<Long> response = mRefundDubboService.applyRefund(refundRequestDto);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testAuditRefund() {
        AuditRefundRequestDto auditRefundRequestDto = new AuditRefundRequestDto();
        auditRefundRequestDto.setRemark("测试审核退款");
        auditRefundRequestDto.setRefundNo("74503374539588608");
        auditRefundRequestDto.setPassed(true);
        auditRefundRequestDto.setAuditorName("测试");
        auditRefundRequestDto.setAuditorId("测试");
        auditRefundRequestDto.setNonceStr(RandomStringUtils.randomAlphanumeric(32));

        Response<Boolean> response = mRefundDubboService.auditRefund(auditRefundRequestDto);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testRefund() {
        RefundRequestDto refundRequestDto = new RefundRequestDto();
        refundRequestDto.setOperatorId("测试");
        refundRequestDto.setOperatorName("测试");
        refundRequestDto.setPayType(PayType.weixin);
        refundRequestDto.setRefundNo("74503374539588608");
        refundRequestDto.setRemark("退款");
        refundRequestDto.setNonceStr(RandomStringUtils.randomAlphanumeric(32));
        Response<RefundResponseDto> response = mRefundDubboService.refund(refundRequestDto);
        System.out.println(JSON.toJSONString(response));
    }
}
