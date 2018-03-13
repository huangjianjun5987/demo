package com.yatang.sc.facade.dubboservice.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.WarehousePhysicalInfoPo;
import com.yatang.sc.facade.dto.WarehousePhysicalInfoDto;
import com.yatang.sc.facade.dubboservice.WarehousePhysicalQueryDubboService;
import com.yatang.sc.facade.service.WarehousePhysicalService;

/**
 * 
 * <class description>物理仓库查询服务实现
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月17日
 */
@Service("warehousePhysicalQueryDubboService")
public class WarehousePhysicalQueryDubboServiceImpl implements WarehousePhysicalQueryDubboService {
	private static final Logger			log	= LoggerFactory.getLogger(WarehousePhysicalQueryDubboServiceImpl.class);
	@Autowired
	private WarehousePhysicalService	warehousePhysicalService;



	@Override
	public Response<List<WarehousePhysicalInfoDto>> getWarehouseByProviceName(String proviceName) {
		log.info("根据省份名称精确查询物理仓，省份：" + proviceName);
		Response<List<WarehousePhysicalInfoDto>> response = new Response<>();
		if (StringUtils.isBlank(proviceName)) {
			response.setCode(CommonsEnum.RESPONSE_10006.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
		}
		try {
			List<WarehousePhysicalInfoPo> listPo = warehousePhysicalService.getWarehouseByProviceName(proviceName);
			log.info("根据省份名称精确查询物理仓，查询结果：" + JSON.toJSONString(listPo));
			List<WarehousePhysicalInfoDto> listDto = BeanConvertUtils.convertList(listPo,
					WarehousePhysicalInfoDto.class);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(listDto);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}

}
