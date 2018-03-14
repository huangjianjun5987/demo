package test.com.yatang.sc;

import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by qiugang on 7/24/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext.xml"})
public class TestOrderMessageSender {

    @Autowired
    OrderMessageSender orderMessageSender;

    @Test
    public void start() {
//        OrderMessage msg = new OrderMessage();
//        msg.setMssageType(OrderMessageType.SubmitOrder);
//        msg.setOrderId("o12311231");
//        msg.setOrderState("pending_approve");
//        orderMessageSender.sendMsg(msg);
    }

}
