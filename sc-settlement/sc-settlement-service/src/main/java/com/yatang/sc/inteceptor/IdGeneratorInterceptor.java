package com.yatang.sc.inteceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Service;

@Service("idGeneratorInterceptor")
public class IdGeneratorInterceptor implements MethodInterceptor {


	@Override
	public Object invoke(MethodInvocation pMethodInvocation) throws Throwable {
		return pMethodInvocation.proceed();
	}

}
