package test.com.yatang.sc;


import com.esotericsoftware.minlog.Log;
import com.yatang.sc.order.domain.CompanyCancelNoPayPo;
import com.yatang.sc.order.service.OrderPaymentsService;
import com.yatang.sc.timedtask.CancelNonpaymentOrderScheduler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestTaskExecutor {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	private CancelNonpaymentOrderScheduler cancelNoPay;

	@Autowired
	private OrderPaymentsService orderPaymentsService;


	@Test
	public void testThread(){
		taskExecutor.execute(new PrintTask("线程一"));
		taskExecutor.execute(new PrintTask("线程二"));
		taskExecutor.execute(new PrintTask("线程三"));
		taskExecutor.execute(new PrintTask("线程四"));
	}

	@Test
	public void testExcute(){
		cancelNoPay.execute();
	}

	@Test
	public void testGtCompanyCancelNoPay(){
		List<CompanyCancelNoPayPo> companyCancelNoPay = orderPaymentsService.getCompanyCancelNoPay();
		for (CompanyCancelNoPayPo companyCancelNoPayPo : companyCancelNoPay) {
			log.info("子公司的名称:{},设置的取消时间:{}",companyCancelNoPayPo.getCompanyName(),companyCancelNoPayPo.getCancelTime());
			System.out.println("子公司的名称:{},设置的取消时间:{}" + companyCancelNoPayPo.getCompanyName() + companyCancelNoPayPo.getCancelTime());
		}
	}
}
