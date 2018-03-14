package test.com.yatang.xc.msg;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.orderIndex.OrderDetailIndex;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext*.xml" })
public class TestPOService {

	protected final Log		log	= LogFactory.getLog(this.getClass());

	@Autowired
	CommerceItemService commerceItemService;

	@Autowired
	OrderService orderService;

	@Before
	public void setUp() throws Exception {
	}




	@Test
	public void testFindByPage() {
	}


	@Test
	public void getCommerceItemForId(){

		CommerceItem commerceItem = commerceItemService.getCommerceItemForId(47879l);
		System.out.println(commerceItem);
	}

	@Test
	public void testUpdateStates() {
		List<Long> less = new ArrayList<Long>();
		less.add(47879l);
		less.add(47894l);
		List<Long> enough = new ArrayList<Long>();
		enough.add(47970l);
		enough.add(47970l);
		commerceItemService.updateState(enough,less);
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		CommerceItem commerceItem = commerceItemService.getCommerceItemForId(58294l);
		commerceItem.setAvCost(2.2d);
		commerceItemService.updateByPrimaryKeySelective(commerceItem);
	}

	@Test
	public void testLoadOrderDetail(){
		OrderDetailIndex orderDetailPo = orderService.loadOrderDetail("100001708087877");
		System.out.println(JSONObject.toJSONString(orderDetailPo, SerializerFeature.WriteMapNullValue));
	}

	@Test
	public void upadteOrder(){
		Order order = orderService.selectByPrimaryKey("100001708088143");
		order.setCancelReason("111");
		orderService.update(order);
	}

}
