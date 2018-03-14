package com.yatang.sc.service;

import com.yatang.sc.payment.service.PayRefundRecordsService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yuwei on 2017/7/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class TestPayRefundService {
    @Autowired
    private PayRefundRecordsService mPayRefundRecordsService;
}
