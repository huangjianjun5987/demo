package com.busi.kidd.serialize;

import com.busi.kidd.KiddException;

/**
 * 数据序列化顶级接口
 * 
 * @author yangqingsong
 *
 */
public interface KiddDataSerialization<T extends DataBean> {
	/**
	 * 序列化
	 */
	public void serialize(T dataBean) throws KiddException;

	/**
	 * 反序列化
	 */
	public void deserialize(T dataBean) throws KiddException;
}
