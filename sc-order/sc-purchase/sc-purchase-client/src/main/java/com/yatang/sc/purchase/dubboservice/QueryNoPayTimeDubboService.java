package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;

/**
 * @描述:
 * @类名:
 * @作者:zhudanning
 * @创建时间:2017/12/28 9:38
 * @版本:v1.0
 */
public interface QueryNoPayTimeDubboService {

	/**
	 * 根据子公司Id查询设置的最大未付款时间
	 * @param companyId
	 * @return
	 */
	Response<Long> getLeftNoPayTime(String companyId);
}
