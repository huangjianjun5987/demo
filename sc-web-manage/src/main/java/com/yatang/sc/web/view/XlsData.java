package com.yatang.sc.web.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @描述:Xls下载数据,代表了一个excel表
 * @作者: zby
 * @创建时间: 2017年7月4日
 */
@Getter
@Setter
public class XlsData {
	// 导出excel的名字
	private String					workBookName;

	private String					pattern;

	private Map<Class<?>, List<?>>	sheetData;



	public XlsData(String workBookName) {
		this.workBookName = workBookName;
	}



	public void setData(Class<?> clazz, List<?> list) {
		if (null == sheetData) {
			sheetData = new LinkedHashMap<>();
		}
		if (sheetData.containsKey(clazz)) {
			throw new RuntimeException("repeat add sheet!");
		}
		sheetData.put(clazz, list);
	}

}
