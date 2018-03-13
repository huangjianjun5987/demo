package com.busi.kidd.behavior.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.busi.kidd.behavior.KiddBehaviorRecord;
import com.busi.kidd.behavior.RecordBean;

/**
 * 打印日志方式记录
 * 
 * @author yangqingsong
 *
 */
public class LogBehaviorRecord implements KiddBehaviorRecord {
	private static final Logger logger = LoggerFactory.getLogger(LogBehaviorRecord.class);

	/**
	 * 日志记录
	 */
	@Override
	public void record(RecordBean recordBean) {
		logger.info(JSON.toJSONString(recordBean));
	}

}
