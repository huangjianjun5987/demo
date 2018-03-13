package test.dubbo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import test.com.busi.kidd.rpc.consumer.HelloworldConsumer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:dubbo/applicationContext-consumer.xml" })
public class TestDubboConsumerService {

	@Autowired
	private HelloworldConsumer consumerService;

	@Test
	public void test() {
		consumerService.call();
	}

}
