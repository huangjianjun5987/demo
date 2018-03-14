package test.com.yatang.sc;

import com.yatang.sc.order.handler.OrderMsgListener;
import com.yatang.sc.order.handler.SubmitOrderListener;
import com.yatang.sc.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext-test.xml"})
public class TestOrderMsgProcessor implements ApplicationContextAware {
    ApplicationContext mContext;
    @Autowired
    SubmitOrderListener submitOrderListener;
    @Autowired
    OrderService orderService;

    @Test
    public void testFindInstance() {
        Map<String, OrderMsgListener> orderMsgListenerMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(mContext, OrderMsgListener.class, false, true);
        for (Map.Entry<String, OrderMsgListener> entry : orderMsgListenerMap.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext pApplicationContext) throws BeansException {
        mContext = pApplicationContext;
    }

}
