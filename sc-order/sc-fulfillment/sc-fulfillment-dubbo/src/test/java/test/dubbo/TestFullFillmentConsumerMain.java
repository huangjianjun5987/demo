package test.dubbo;

import com.yatang.sc.order.handler.OrderMsgListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-consumer.xml"})
public class TestFullFillmentConsumerMain {

    @Resource(name = "approvedOrderListener")
    private OrderMsgListener approvedOrderListener;
    @Test
    public void testSplitOrder() {
//        approvedOrderListener.handle(null);
    }
}
