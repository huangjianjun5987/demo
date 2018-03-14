package com.yatang.sc.operation.util.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @描述:
 * @作者: kangdong
 * @创建时间: 2017/5/25 10:46
 * @版本: v1.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportModel {

	String fileName() default "default";

}
