package com.busi.kidd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 调用第三方接口注解
 * 
 * @author yangqingsong
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface KiddCaller {

	// 调用类型
	InterfaceProviderTypeEnum providerType() default InterfaceProviderTypeEnum.GLINK;

	// 调用类型
	CallTypeEnum callType() default CallTypeEnum.SYNC_CALL;

	// 接口地址
	String url() default "";

	// 接口方法名
	String method() default "";

	// 重试次数
	int reCallCount() default 3;

	// 异步请求时,回执消息最大等待时间
	int asyncReceiptWaitTime() default -1;

	// 超出最大回执等待时间,调用的方法名,必须在同一个类中
	String callbackMethod() default "";

	// 安全验证,单项还是双向
	// SecurityProtocolEnum security() default SecurityProtocolEnum.ONEWAY;
}
