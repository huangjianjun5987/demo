package com.yatang.sc.common.localcache;


import com.alibaba.fastjson.JSON;
import com.busi.common.utils.BeanConvertUtils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class LocalCacheContext {
    private static final Logger logger = LoggerFactory.getLogger("LocalCacheContext");

    private static Map<String, Cache<String, Object>> mCacheGroup = new HashMap<String, Cache<String, Object>>();


    public static <T> T get(String group, String key, Class<T> cls) {
        if (!mCacheGroup.containsKey(group)) {
            return null;
        }
        key = key.toUpperCase();
        Object obj = mCacheGroup.get(group).getIfPresent(key);
        if (obj == null) {
            return null;
        }

        return BeanConvertUtils.convert(obj, cls);
    }

    public static <T> void put(String group, String key, Object val, Class<T> cls, int initialCapacity, long expireAfterWrite, long expireAfterAccess, long maximumSize, int concurrencyLevel) {

        if (!mCacheGroup.containsKey(group)) {
            synchronized (LocalCacheContext.class) {
                if (!mCacheGroup.containsKey(group)) {
                    logger.info("创建本地缓存分组:{}", group);
                    mCacheGroup.put(group, createCache(initialCapacity, expireAfterWrite, expireAfterAccess, maximumSize, concurrencyLevel));
                }
            }
        }
        key = key.toUpperCase();
        mCacheGroup.get(group).put(key, BeanConvertUtils.convert(val, cls));
    }

    public static void invalidateAll(String group) {
        if (mCacheGroup.containsKey(group)) {
            mCacheGroup.get(group).invalidateAll();
        }
    }

    public static void invalidate(String group, String key) {
        if (!mCacheGroup.containsKey(group)) {
            return;
        }
        key = key.toUpperCase();
        mCacheGroup.get(group).invalidate(key);
    }

    public static void invalidateAll(String group, Iterable<String> keys) {
        if (!mCacheGroup.containsKey(group)) {
            return;
        }
        List<String> convertKeys = new ArrayList<>();
        for (String key : keys) {
            convertKeys.add(key.toUpperCase());
        }
        mCacheGroup.get(group).invalidateAll(convertKeys);
    }

    public static void printCache(String group) {
        if (!mCacheGroup.containsKey(group)) {
            return;
        }
        logger.info("=============================== 打印[{}]cache:{} ==================================", group, JSON.toJSONString(mCacheGroup.get(group).asMap()));
    }

    protected static <T> Cache<String, T> createCache(int initialCapacity, long expireAfterWrite, long expireAfterAccess, long maximumSize, int concurrencyLevel) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        if (initialCapacity > 0) {
            //设置cache的初始大小为10，要合理设置该值
            cacheBuilder.initialCapacity(initialCapacity);
        }
        if (concurrencyLevel > 0) {
            //设置并发数为5，即同一时间最多只能有5个线程往cache执行写入操作
            cacheBuilder.concurrencyLevel(concurrencyLevel);
        }
        if (expireAfterWrite > 0) {
            //设置cache中的数据在写入之后的存活时间为10秒
            cacheBuilder.expireAfterWrite(expireAfterWrite, TimeUnit.MINUTES);
        }
        if (expireAfterAccess > 0) {
            cacheBuilder.expireAfterAccess(expireAfterAccess, TimeUnit.MINUTES);
        }
        if (maximumSize > 0) {
            cacheBuilder.maximumSize(maximumSize);
        }
        /*RemovalListener<String, T> removalListener = new RemovalListener<String, T>() {
            public void onRemoval(RemovalNotification<String, T> removal) {
                logger.info("Cache is removed:{}->{}", removal.getKey(), JSON.toJSONString(removal.getValue()));
            }
        };*/
        return cacheBuilder.build();
    }

}
