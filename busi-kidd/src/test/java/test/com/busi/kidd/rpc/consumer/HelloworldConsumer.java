package test.com.busi.kidd.rpc.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.com.busi.kidd.rpc.HelloBean;
import test.com.busi.kidd.rpc.provider.HelloworldProvider;

@Service
public class HelloworldConsumer {

	@Autowired
	private HelloworldProvider provider;

	public void call() {
		HelloBean bean = new HelloBean();
		String result = provider.hello(bean);
		System.out.println("HelloworldConsumer---  result : " + result);
	}
}
