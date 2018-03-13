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
public @interface KiddProvider {

	// 调用类型
	ProviderTypeEnum providerType() default ProviderTypeEnum.MD5;
}
