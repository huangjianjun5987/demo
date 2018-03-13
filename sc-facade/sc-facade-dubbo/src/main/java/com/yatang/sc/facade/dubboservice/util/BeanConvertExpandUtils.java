package com.yatang.sc.facade.dubboservice.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.busi.common.utils.BeanConvertUtils;

/**
 * @描述:多对象类转换扩展工具类
 * @类名:BeanConvertExpandUtils
 * @作者: huangjianjun
 * @创建时间: 2017/5/22 20:48
 * @版本: v1.0
 */
public class BeanConvertExpandUtils extends BeanConvertUtils {
	
	@SuppressWarnings("static-access")
	public static <T> T convertMoreParam(Class<T> targetClass, Object... source) {
		String jsonStr = JSON
				.toJSONString(
						source,
						SerializerFeature.WriteMapNullValue.WriteNullListAsEmpty.WriteNullStringAsEmpty.WriteNullNumberAsZero.UseSingleQuotes)
				.replaceAll("\\{", "").replaceAll("\\}", "").replaceFirst("\\[", "\\{").replaceFirst("\\]", "\\}")
				.replaceAll("'handler':,", "").replaceAll("'modification':", "").replaceAll("\'", "\"");
		return JSON.parseObject(jsonStr, targetClass);
	}
}
