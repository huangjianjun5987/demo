/**
 * 
 */
package com.yatang.sc.common.cache;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author dengdongshan
 *action.getClass().getName() RedisCacheEvictCallback
 */
public class CustomRedisTemplate<K, V> extends RedisTemplate<K, V> {
	private RedisConnectionFactory evictConnectionFactory;

	ThreadLocal<Boolean> currentThread = new ThreadLocal<>();
	@Override
	public <T> T execute(RedisCallback<T> action, boolean exposeConnection, boolean pipeline) {
		if(action!=null&&action.getClass().getName()!=null&&action.getClass().getName().contains("RedisCacheEvictCallback")){
			currentThread.set(true);
		}else if(action!=null&&action.getClass().getName()!=null&&action.getClass().getName().contains("RedisCachePutCallback"))
		{
			currentThread.set(true);
		}
		else{
			currentThread.set(false);
		}
		
		return super.execute(action, exposeConnection, pipeline);
	}

	@Override
	public RedisConnectionFactory getConnectionFactory() {
		if(currentThread.get()!=null&&currentThread.get()){
			return evictConnectionFactory;
		}
		return super.getConnectionFactory();
	}

	public RedisConnectionFactory getEvictConnectionFactory() {
		return evictConnectionFactory;
	}

	public void setEvictConnectionFactory(RedisConnectionFactory evictConnectionFactory) {
		this.evictConnectionFactory = evictConnectionFactory;
	}

}
