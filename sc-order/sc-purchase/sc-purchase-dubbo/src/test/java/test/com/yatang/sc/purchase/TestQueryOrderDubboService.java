package test.com.yatang.sc.purchase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.busi.common.resp.Response;
import com.yatang.sc.common.PageResult;
import com.yatang.sc.purchase.dto.OrderClassificationDto;
import com.yatang.sc.purchase.dto.OrderSummaryDto;
import com.yatang.sc.purchase.dubboservice.QueryOrderDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @描述: 供应商只读服务测试类
 * @作者: xiangyonghong
 * @创建时间: 2017年3月31日-下午8:36:20 .
 * @版本: 1.0 .
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestQueryOrderDubboService{

	@Autowired
	private QueryOrderDubboService service;
	
	@Test
	public void getOrder(){
		//pro1,dfgd1
		OrderClassificationDto orderClassificationDto = new OrderClassificationDto();
		orderClassificationDto.setProfileId("0");
		orderClassificationDto.setState("60");
		orderClassificationDto.setPage("1");
		orderClassificationDto.setPageSize("10");

		Response<PageResult<OrderSummaryDto>> result = service.queryOrder(orderClassificationDto);
		System.out.println(JSONObject.toJSONString(result));
		System.out.println(JSONObject.toJSONString(result, SerializerFeature.WriteMapNullValue));

	}

}
