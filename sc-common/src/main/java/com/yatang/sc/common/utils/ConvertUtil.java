package com.yatang.sc.common.utils;


/**
 * @描述: 类型转换工具类
 * @作者: huangjianjun
 * @创建时间: 2017年10月25日-下午1:59:42 .
 */
public class ConvertUtil {

	/**
	 * @Description: String数组转Long数组
	 * @author huangjianjun
	 * @date 2017年10月25日下午2:19:32
	 */
	public static Long[] strArrToLongArr(String [] strs){
		if(strs != null ){
			Long [] result = new Long[strs.length];
			for (int i = 0; i < strs.length; i++) {
				result[i] = Long.parseLong(strs[i]);
			}
			return result;
		}
		return null;
	}
	
	
}
