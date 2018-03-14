package test.com.yatang.sc.purchase;

import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dubboservice.QueryNoPayTimeDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhudanning on 28/12/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestQueryNoPayTimeDubboService {

	@Autowired
	private QueryNoPayTimeDubboService queryNoPayTimeDubboService;

	@Test
	public void testGetLeftNoPayTime() {
		Response<Long> leftNoPayTime = queryNoPayTimeDubboService.getLeftNoPayTime("10000");
		System.out.println("子公司设置的最大未付款时间为：" + leftNoPayTime.getResultObject() + "分钟");
	}
}
