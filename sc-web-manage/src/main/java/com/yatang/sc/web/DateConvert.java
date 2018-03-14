package com.yatang.sc.web;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * Created by yipeng on 2017/7/25.
 */
public class DateConvert implements Converter<String, Date> {

	@Override
	public Date convert(String longDate) {
		return new Date(Long.valueOf(longDate));
	}

}