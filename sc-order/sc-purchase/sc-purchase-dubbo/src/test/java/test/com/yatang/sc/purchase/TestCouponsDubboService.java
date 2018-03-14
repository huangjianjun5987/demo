package test.com.yatang.sc.purchase;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.order.service.CouponActivityService;
import com.yatang.sc.purchase.dto.CouponsDto;
import com.yatang.sc.purchase.dto.CouponsParamDto;
import com.yatang.sc.purchase.dubboservice.CouponsDubboService;

/**
 * @description:
 * @author: yinyuxin
 * @date: 2017/11/15 9:40
 * @version: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestCouponsDubboService {

	@Autowired
	private CouponsDubboService couponsDubboService;

	@Autowired
	private CouponActivityService couponActivityService;
	@Test
	public void testReceiveCoupon(){
		CouponsParamDto couponsParamDto=new CouponsParamDto();
		couponsParamDto.setStoreId("A000541");
		couponsParamDto.setId("J295");
		Response<Integer> response = couponsDubboService.receiveCoupon(couponsParamDto);
		System.out.println("测试:"+ JSONObject.toJSONString(response));
	}
	
	@Test
	public void testReceiveCountCoupon(){
		List<Map<String, Object>> couponReceiveCount = couponActivityService.getCouponReceiveCount(new String[]{"J221","J222"}, "A001217");
		System.out.println("############测试:"+ JSONObject.toJSONString(couponReceiveCount));
	}
	
	
	@Test
	public void queryPersonalCoupons(){
		CouponsParamDto couponsParamDto=new CouponsParamDto();
		couponsParamDto.setStoreId("A001217");
		couponsParamDto.setId("J222");
		Response<PageResult<CouponsDto>> couponReceiveCount = couponsDubboService.queryPersonalCoupons(couponsParamDto);
		System.out.println("############测试:"+ JSONObject.toJSONString(couponReceiveCount));
	}
	
	
}
