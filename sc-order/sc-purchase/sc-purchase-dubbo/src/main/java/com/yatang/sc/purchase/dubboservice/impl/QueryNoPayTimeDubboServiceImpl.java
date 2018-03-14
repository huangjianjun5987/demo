package com.yatang.sc.purchase.dubboservice.impl;

import com.busi.common.resp.Response;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.order.service.OrderPaymentsService;
import com.yatang.sc.purchase.dubboservice.QueryNoPayTimeDubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("queryNoPayTimeDubboService")
public class QueryNoPayTimeDubboServiceImpl implements QueryNoPayTimeDubboService {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderPaymentsService orderPaymentsService;

	@Override
	public Response<Long> getLeftNoPayTime(String companyId) {
		log.info("子公司的Id:{} ", companyId);
		Response<Long> response = new Response<Long>();
		try {
			Long noPayTime = orderPaymentsService.getNoPayByCompanyId(companyId);
			response.setResultObject(noPayTime);
		} catch (Exception e) {
			log.info("错误信息 " + e.getMessage());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(false);
			return response;
		}
		response.setSuccess(true);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		return response;
	}
}
