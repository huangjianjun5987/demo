package test.com.yatang.xc.msg;

import com.yatang.sc.order.domain.MqMsgLog;
import com.yatang.sc.order.service.MqMsgLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class TestMqMsgLog {

    @Autowired
    private MqMsgLogService mMqMsgLogService;

    @Test
    public void testInsert(){
        MqMsgLog mqMsgLog = new MqMsgLog();
        mqMsgLog.setMsgType("testTyoe");
        mqMsgLog.setMsgBody("test body");
        mqMsgLog.setOrderId("testId");
        mMqMsgLogService.insert(mqMsgLog);
    }
}
