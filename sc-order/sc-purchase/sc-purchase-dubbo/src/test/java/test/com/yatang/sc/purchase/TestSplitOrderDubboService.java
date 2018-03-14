package test.com.yatang.sc.purchase;

import com.yatang.sc.purchase.dubboservice.SplitOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestSplitOrderDubboService {

    @Autowired
    private SplitOrderService mSplitOrderService;

    @Test
    public void testSplitOrder() {
        Map<String, Integer> lessStock = new HashMap<String, Integer>(2);
        lessStock.put("57973",1);
        lessStock.put("57967",12);
        Map<String, Integer>  enoughStock = new HashMap<String, Integer>(2);
        enoughStock.put("57973",3);
        enoughStock.put("57961",12);
        enoughStock.put("57979",1);

       // mSplitOrderService.splitOrderByInventory("10000201708287959",enoughStock,lessStock);
    }
}
