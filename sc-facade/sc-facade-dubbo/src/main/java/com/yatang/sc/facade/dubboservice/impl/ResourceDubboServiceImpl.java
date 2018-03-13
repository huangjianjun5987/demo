package com.yatang.sc.facade.dubboservice.impl;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.dto.system.ResourceDto;
import com.yatang.sc.facade.dubboservice.ResourceDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.ResourceService;

@Slf4j
@Service("resourceDubboService1")
public class ResourceDubboServiceImpl implements ResourceDubboService {

	@Autowired
	private ResourceService	service;
	
	@Override
	public Response<ResourceDto> findOne(Long id) {
		log.info("start----findOne--resourceId:{}", id);
		Response<ResourceDto> response = new Response<ResourceDto>();
		try {
			ResourcePo po = service.findOne(id);
			ResourceDto dto = BeanConvertUtils.convert(po, ResourceDto.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
			response.setResultObject(dto);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----findOne");
		return response;
	}



	@Override
	public Response<List<ResourceDto>> queryAllRes() {
		log.info("start----queryAllRes");
		Response<List<ResourceDto>> response = new Response<List<ResourceDto>>();
		try {
			List<ResourcePo> pos = service.queryAllRes();
			List<ResourceDto> dtos = BeanConvertUtils.convertList(pos, ResourceDto.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
			response.setResultObject(dtos);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----queryAllRes");
		return response;
	}

	@Override
	public Response<List<ResourceDto>> readLeftMenuOrButtonByIdAndLoginName(Long resParentId, String loginName) {
		log.info("start----readLeftMenuOrButtonByIdAndLoginName--resParentId:{}--loginName:{}", resParentId, loginName);
		Response<List<ResourceDto>> response = new Response<List<ResourceDto>>();
		try {
			List<ResourcePo> pos = service.readLeftMenuOrButtonByIdAndLoginName(resParentId, loginName);
			List<ResourceDto> dtos = BeanConvertUtils.convertList(pos, ResourceDto.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
			response.setResultObject(dtos);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----readLeftMenuOrButtonByIdAndLoginName");
		return response;
	}

	@Override
	public Response<Boolean> isRelatedWithRole(Long id) {
		log.info("start----isRelatedWithRole--resourceId:{}", id);
		Response<Boolean> response = new Response<>();
		try {
			response.setResultObject(service.isRelatedWithRole(id));
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----isRelatedWithRole");
		return response;
	}

	@Override
	public Response<Boolean> deleteRes(List<Long> ids) {
		log.info("start----deleteRes--resourceIds:{}", ids);
		Response<Boolean> response = new Response<>();
		try {
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.deleteRes(ids));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----deleteRes");
		return response;
	}

	@Override
	public Response<Boolean> updateRes(ResourceDto resourceDto) {
		log.info("start----updateRes--resourceDto:{}", resourceDto);
		Response<Boolean> response = new Response<>();
		try {
			ResourcePo resourcePo = BeanConvertUtils.convert(resourceDto, ResourcePo.class);
			response.setSuccess(service.updateRes(resourcePo));
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----updateRes");
		return response;
	}

	@Override
	public Response<Boolean> createRes(ResourceDto resourceDto) {
		log.info("start----createRes--resourceDto:{}", resourceDto);
		Response<Boolean> response = new Response<>();
		try {
			ResourcePo resourcePo = BeanConvertUtils.convert(resourceDto, ResourcePo.class);
			response.setSuccess(service.createRes(resourcePo));
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----createRes");
		return response;
	}


}
