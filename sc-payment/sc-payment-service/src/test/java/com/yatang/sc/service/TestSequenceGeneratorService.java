package com.yatang.sc.service;

import com.yatang.sc.payment.service.SequenceGeneratorService;
import com.yatang.sc.payment.service.impl.SnowflakeSequenceGeneratorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yuwei on 2017/7/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class TestSequenceGeneratorService {
    @Resource(name = "snowflakeSequenceGeneratorService")
    private SequenceGeneratorService mSequenceGeneratorService;

    @Test
    public void testGenSequnence() {
        SequenceGeneratorService idWorker = new SnowflakeSequenceGeneratorService();
        String id;
        for (int i = 0; i < 1000; i++) {
            id = idWorker.genSequence();
            System.out.println(id);
            System.out.println(Long.toBinaryString(Long.valueOf(id)));
        }
    }
}
