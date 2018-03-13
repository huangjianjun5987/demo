package test.com.yatang.sc.inventory;

import com.yatang.sc.inventory.service.ItemLocSohService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by qiugang on 7/10/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestItemLocSohService {

    @Autowired
    ItemLocSohService itemLocSohService;

//    @Test
//    public void testSaleOut(){
//        itemLocSohService.reserveOrder("1","2",3);
//    }

    @Test
    public void testAgreeSaleOutd(){
        itemLocSohService.saleOutOrder("1","2",3,4);
    }

    @Test
    public void testBAgreeSaleOut(){
        itemLocSohService.orderArrived("1","2",3,4);
    }

//    @Test
//    public void testSaleOutUndelivered(){
//        itemLocSohService.orderUnArrive("1","2",3,4);
//    }

    @Test
    public void testCancelSaleOrder(){
        itemLocSohService.cancelReserveOrder("1","2",3);
    }

}
