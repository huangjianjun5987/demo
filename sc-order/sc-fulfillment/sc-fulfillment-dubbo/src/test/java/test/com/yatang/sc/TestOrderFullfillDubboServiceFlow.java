package test.com.yatang.sc;

import com.busi.common.resp.Response;
import com.yatang.sc.flow.OrderFulfillerDubboServiceFlow;
import com.yatang.sc.fulfillment.dto.OrderDeliveryDto;
import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.thirdorder.busi.BusinessOrderService;
import com.yatang.sc.thirdorder.busi.dto.BusiOrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liusongjie on 2017/8/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestOrderFullfillDubboServiceFlow {

    @Autowired
    OrderFulfillerDubboServiceFlow orderFulfillerDubboServiceFlow;

    @Autowired
    OrderInventoryHelper mInventoryHelper;

    @Autowired
    OrderService mOrderService;

    @Autowired
    BusinessOrderService businessOrderService;
    @Test
    public void testOrderDelivery() {
        OrderDeliveryDto orderDeliveryDto = new OrderDeliveryDto();
        orderDeliveryDto.setOrderId("b11708041898");
        try {
            Response<Boolean> res = orderFulfillerDubboServiceFlow.orderDelivery(orderDeliveryDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReserveOrder() {
        Order order = mOrderService.selectByPrimaryKey("10000201708287959");
        mInventoryHelper.reserveOrderInventory(order, true);
    }

    @Test
    public void testUpdateBusiOrder(){
        BusiOrderDto busiOrderDto = new BusiOrderDto();
        busiOrderDto.setOrderId("sit2-29790001");
        busiOrderDto.setState("PENDING_DELIVERY");
        businessOrderService.updateShippingStates(busiOrderDto);

    }

}
