package test.com.yatang.xc.msg;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.order.domain.MqFailedMsg;
import com.yatang.sc.order.service.MqFailedMsgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class TestMqFailedMsgService {

    @Autowired
    private MqFailedMsgService mMqFailedMsgService;

    @Test
    public void TestInsert() {
        MqFailedMsg mqFailedMsg = new MqFailedMsg();
        mqFailedMsg.setOrderId("12366");
        mqFailedMsg.setMessageType("CANCELED_ORDER");
        mMqFailedMsgService.insert(mqFailedMsg);
    }

    @Test
    public void TestSelectByMessageType() {
        List<MqFailedMsg> mqFailedMsgs = mMqFailedMsgService.selectByMessageType("CANCELED_ORDER");
        System.out.println(JSON.toJSONString(mqFailedMsgs));
    }
}
