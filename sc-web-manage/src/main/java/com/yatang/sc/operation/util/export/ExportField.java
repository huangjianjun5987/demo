package com.yatang.sc.operation.util.export;

import java.lang.annotation.*;

/**
 * @描述:
 * @作者: kangdong
 * @创建时间: 2017/5/25 10:46
 * @版本: v1.0
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExportField {

	String colName() default "";



	int index() default -1;

}
