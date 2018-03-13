package test.com.busi.kidd.rpc.provider;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.KiddCaller;

import test.com.busi.kidd.rpc.HelloBean;

@Service("helloworldProvider")
public class HelloworldProviderImpl implements HelloworldProvider {

	@Override
	@KiddCaller(url = "www.baidu.com", method = "123123", callType = CallTypeEnum.SYNC_CALL)
	public String hello(HelloBean bean) {
		return null;
	}

}
