package test.com.yatang.sc.coupon;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.coupon.dubboservice.CouponActivityDubbleService;
import com.yatang.sc.coupon.dubboservice.CouponsQueryDubboService;
import com.yatang.sc.dto.GrantCouponDto;
import com.yatang.sc.order.service.OrderService;

/**
 * @描述: 优惠券测试类
 * @类名: TestCouponDubboService
 * @作者: dengdongshan
 * @创建时间: 2017/11/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestCouponDubboService {

	@Autowired
	OrderService						orderService;
	@Autowired
	private CouponsQueryDubboService	couponsQueryDubboService;
	@Autowired
	private CouponActivityDubbleService	couponActivityDubbleService;



	@Test
	public void revertCoupons() {
		Response<String> response = orderService.revertCoupons("", 1022841l);
		System.out.print("======" + response.getResultObject());
	}



	@Test
	public void giveCouponToStore() {
		// Response<String> response =
		// orderService.giveCouponToStore("jms_000541", 0d,1, 61955l);
		// System.out.print("======"+response.getResultObject());
	}



	@Test
	public void testGrant() {
		GrantCouponDto grantCouponDto = new GrantCouponDto();
		grantCouponDto.setStoreIds(new String[] { "1" });
		grantCouponDto.setPromoIds(new String[] { "HD192" });
		Response<String> response = couponActivityDubbleService.grantCoupon(grantCouponDto);
		System.out.println("******************" + JSON.toJSONString(response));
	}
}
