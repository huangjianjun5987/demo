package com.yatang.sc.provider.web;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * @描述: 日期转换类
 * @作者: huangjianjun
 * @创建时间: 2018年1月11日-下午6:52:26 .
 */
public class DateConvert implements Converter<String, Date> {

	@Override
	public Date convert(String longDate) {
		return new Date(Long.valueOf(longDate));
	}

}