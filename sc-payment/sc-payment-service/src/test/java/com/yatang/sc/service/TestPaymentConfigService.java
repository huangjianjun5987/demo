package com.yatang.sc.service;

import com.yatang.sc.payment.dao.PaymentRecordDao;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.enums.PayStatus;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.service.PaymentConfigService;
import com.yatang.sc.payment.service.PaymentRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class TestPaymentConfigService {
    @Autowired
    private PaymentConfigService mPaymentConfigService;
    @Autowired
    private PaymentRecordService mPaymentRecordService;

    @Autowired
    private PaymentRecordDao mPaymentRecordDao;

    @Test
    public void testGetConfigByType() {
        PayType payType = PayType.weixin;
        PaymentConfigPO payConfig = mPaymentConfigService.getConfigByType(payType);
        System.out.println(payConfig);
    }

    @Test
    public void testQueryPayRecordsByOrderNo() {
        List<PayStatus> payStatusList = new ArrayList<>();
        payStatusList.add(PayStatus.SUCCESS);
        Map<String, Object> params = new HashMap<>();
        params.put("orderNo", "b11708015322");
        params.put("payStatusList", payStatusList);
        List<PaymentRecordPO> payConfig = mPaymentRecordDao.queryPayRecordsByOrderNo(params);
        System.out.println(payConfig);
    }
}
