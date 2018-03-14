package test.com.yatang.sc.promotion;

import com.busi.common.resp.Response;
import com.yatang.sc.order.dubboservice.PromotionDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @描述: 促销测试类
 * @类名: TestPromotionDubboService
 * @作者: zhudanning
 * @创建时间: 2017/12/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestPromotionDubboService {

	@Autowired
	private PromotionDubboService promotionDubboService;

	@Test
	public void testBatchUpdatePromoStatus(){
		String[] ids = {"J315","J320","J331"};
		String status = "closed";
		Response<Boolean> response = promotionDubboService.batchUpdatePromoStatus(ids, status);
		System.out.println("执行结果：" + response.getResultObject() + "," + response.getErrorMessage());
	}
}
