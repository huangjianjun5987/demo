package com.yatang.sc.common.localcache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LocalCache {
    String group() default "default";

    long expireAfterAccess() default -1L;

    long expireAfterWrite() default -1L;

    int concurrencyLevel() default 4; //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作

    int initialCapacity() default 20;
    long maximumSize();
}
