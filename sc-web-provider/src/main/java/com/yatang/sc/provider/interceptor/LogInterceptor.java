package com.yatang.sc.provider.interceptor;

import lombok.extern.slf4j.Slf4j;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.servlet.ModelAndView;

/**
 * @描述: 日志拦截器
 * @作者 	 huangjianjun
 * @日期 2017年7月19日 上午11:15:35
 */
@Slf4j
public class LogInterceptor implements MethodInterceptor {



	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.info("Before: interceptor method name: {" + invocation.getMethod().getName() + "}");
		log.info("Arguments: {" + invocation.getArguments() + "}");
//		log.info("Arguments: {" + jsonMapper.writeValueAsString(invocation.getArguments()) + "}");
		log.info("Arguments: {" + ToStringBuilder.reflectionToString(invocation.getArguments()) + "}");
		Object result = invocation.proceed();
		if (result instanceof ModelAndView) {
			ModelAndView modelAndView = (ModelAndView) result;
			log.info("After: result: {" + modelAndView + "}");
		} else {
//			log.info("After: result: {" + jsonMapper.writeValueAsString(result) + "}");
			log.info("After: result: {" + ToStringBuilder.reflectionToString(result) + "}");
		}
		return result;
	}
}
