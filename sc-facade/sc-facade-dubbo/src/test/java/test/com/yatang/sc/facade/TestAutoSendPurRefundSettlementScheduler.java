package test.com.yatang.sc.facade;

import com.yatang.sc.facade.scheduler.AutoSendPurRefundSettlementScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @描述: demo
 * @作者: tankejia
 * @创建时间: 2017/12/28-13:42 .
 * @版本: 1.0 .
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestAutoSendPurRefundSettlementScheduler {

    @Autowired
    AutoSendPurRefundSettlementScheduler settlementScheduler;

    @Test
    public void autoSendOrderIndexSchedulerTest(){
        try {
            settlementScheduler.execute("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
