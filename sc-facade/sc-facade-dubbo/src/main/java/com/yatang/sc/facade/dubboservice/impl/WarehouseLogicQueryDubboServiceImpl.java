package com.yatang.sc.facade.dubboservice.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.WarehouseLogicInfoPo;
import com.yatang.sc.facade.domain.WarehousePhysicalInfoPo;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dto.PhysicalWarehouseDto;
import com.yatang.sc.facade.dto.WarehouseInfoDto;
import com.yatang.sc.facade.dto.WarehouseLogicDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.facade.service.WarehouseLogicService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * <class description>逻辑仓库查询服务实现
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年7月28日
 */
@Slf4j
@Service("warehouseLogicQueryDubboService")
public class WarehouseLogicQueryDubboServiceImpl implements WarehouseLogicQueryDubboService {
	@Autowired
	private WarehouseLogicService warehouseLogicService;



	@Override
	public Response<PageResult<WarehouseLogicDto>> getWarehouseByOrCondition(WarehouseLogicDto warehouseLogicDto,
                                                                             Integer supplierAddressId, Integer pageNum, Integer pageSize) {
		if (log.isInfoEnabled()) {
			log.info(
					"WarehouseLogicQueryDubboServiceImpl.getWarehouseByOrCondition(WarehouseLogicDto {},Integer {}, Integer {}, Integer {})",
					ToStringBuilder.reflectionToString(warehouseLogicDto), supplierAddressId, pageNum, pageSize);
		}
		Response<PageResult<WarehouseLogicDto>> response = new Response<>();
		try {
			WarehouseLogicInfoPo warehouseLogicInfoPo = BeanConvertUtils.convert(warehouseLogicDto,
					WarehouseLogicInfoPo.class);
			warehouseLogicInfoPo.setSupplierAddressId(supplierAddressId);
			PageInfo<WarehouseLogicInfoPo> pageInfo = warehouseLogicService
					.getWarehouseByOrCondition(warehouseLogicInfoPo, pageNum, pageSize);

			PageResult<WarehouseLogicDto> pageResult = new PageResult<>();
			pageResult.setPageNum(pageInfo.getPageNum());
			pageResult.setPageSize(pageInfo.getPageSize());
			pageResult.setTotal(pageInfo.getTotal());
			pageResult.setData(BeanConvertUtils.convertList(pageInfo.getList(), WarehouseLogicDto.class));

			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(pageResult);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<List<WarehouseLogicDto>> queryWarehouseLogicList() {
		Response<List<WarehouseLogicDto>> response = new Response<>();
		try {
			List<WarehouseLogicInfoPo> list = warehouseLogicService.queryWarehouseLogicList();
			List<WarehouseLogicDto> warehouseLogicDtos = BeanConvertUtils.convertList(list, WarehouseLogicDto.class);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(warehouseLogicDtos);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<WarehouseInfoDto> getWarehouseInfoByWarehouseLogicId(Integer warehouseLogicId) {
		if (log.isInfoEnabled()) {
			log.info("WarehouseLogicQueryDubboServiceImpl.getWarehouseInfoByWarehouseLogicId(Integer {})",
					warehouseLogicId);
		}
		Response<WarehouseInfoDto> response = new Response<>();
		try {
			if (null == warehouseLogicId || warehouseLogicId < 0) {
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(null);
				return response;
			}
			WarehouseLogicInfoPo warehouseLogicInfoPo = warehouseLogicService.selectWarehouseLogic(warehouseLogicId);
			if (null != warehouseLogicInfoPo && null != warehouseLogicInfoPo.getWarehousePhysicalInfo()) {
				WarehouseInfoDto warehouseInfoDto = new WarehouseInfoDto();
				warehouseInfoDto.setWarehouseLogicId(warehouseLogicInfoPo.getId());
				warehouseInfoDto.setWarehouseLogicCode(warehouseLogicInfoPo.getWarehouseCode());
				warehouseInfoDto.setWarehouseLogicName(warehouseLogicInfoPo.getWarehouseName());
				WarehousePhysicalInfoPo warehousePhysicalInfoPo = warehouseLogicInfoPo.getWarehousePhysicalInfo();
				warehouseInfoDto.setWarehouseService(warehousePhysicalInfoPo.getWarehouseService());
				String region = new StringBuilder().append(warehousePhysicalInfoPo.getProvince()).append("省")
						.append(warehousePhysicalInfoPo.getCity()).append("市")
						.append(warehousePhysicalInfoPo.getCounty()).append("区").toString();
				warehouseInfoDto.setRegion(region);
				warehouseInfoDto.setAddress(warehousePhysicalInfoPo.getAddress());
				warehouseInfoDto.setContactMode(warehousePhysicalInfoPo.getContactMode());
				warehouseInfoDto.setContactPerson(warehousePhysicalInfoPo.getContactPerson());
				response.setResultObject(warehouseInfoDto);
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<LogicWarehouseDto> selectLogicWarehouseByPrimaryKey(String warehouseLogicCode) {
		if (log.isInfoEnabled()) {
			log.info("WarehouseLogicQueryDubboServiceImpl.selectLogicWarehouseByPrimaryKey(String {})",
					warehouseLogicCode);
		}
		Response<LogicWarehouseDto> response = new Response<>();
		try {
			if (null == warehouseLogicCode) {
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(null);
				return response;
			}
			WarehouseLogicInfoPo warehouseLogicInfoPo = warehouseLogicService
					.selectByWarehouseLogicCode(warehouseLogicCode);
			if (null == warehouseLogicInfoPo) {
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				response.setSuccess(true);
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				response.setResultObject(null);
				return response;
			}
			LogicWarehouseDto logicWarehouseDto = BeanConvertUtils.convert(warehouseLogicInfoPo,
					LogicWarehouseDto.class);
			PhysicalWarehouseDto physicalWarehouseDto = BeanConvertUtils
					.convert(warehouseLogicInfoPo.getWarehousePhysicalInfo(), PhysicalWarehouseDto.class);
			logicWarehouseDto.setPhysicalWarehouseDto(physicalWarehouseDto);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(logicWarehouseDto);
			return response;
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<List<LogicWarehouseDto>> getWarehouseByProviceName(String proviceName) {
		log.info("根据省份名称精确查询物理仓，省份：" + proviceName);
		Response<List<LogicWarehouseDto>> response = new Response<>();
		if (StringUtils.isBlank(proviceName)) {
			response.setCode(CommonsEnum.RESPONSE_10006.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
		}
		try {
			List<WarehouseLogicInfoPo> listPo = warehouseLogicService.getWarehouseByProviceName(proviceName);
			log.info("根据省份名称精确查询物理仓，查询结果：" + JSON.toJSONString(listPo));
			List<LogicWarehouseDto> listDto = BeanConvertUtils.convertList(listPo, LogicWarehouseDto.class);
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

	@Override
	public Response<List<LogicWarehouseDto>> getWarehouseByBranchCompanyId(String companyId) {
		log.info("根据分companyId:{}查询逻辑仓." , companyId);
		Response<List<LogicWarehouseDto>> response = new Response<>();
		if(StringUtils.isEmpty(companyId)){
			response.setCode(CommonsEnum.RESPONSE_10006.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
		}
		try{
			List<WarehouseLogicInfoPo> listPo = warehouseLogicService.getWarehouseByBranchCompanyId(companyId);
			log.info("根据省份名称精确查询物理仓，查询结果：" + JSON.toJSONString(listPo));
			List<LogicWarehouseDto> listDto = BeanConvertUtils.convertList(listPo, LogicWarehouseDto.class);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setSuccess(true);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(listDto);
		}catch (Exception e){
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}

		return response;
	}
}
