package com.yatang.sc.facade.dubboservice.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.domain.UserPo;
import com.yatang.sc.facade.dto.system.ResourceDto;
import com.yatang.sc.facade.dto.system.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.domain.RolePo;
import com.yatang.sc.facade.dto.system.RoleDto;
import com.yatang.sc.facade.dubboservice.RoleDubboService;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.RoleService;

@Slf4j
@Service("roleDubboService1")
public class RoleDubboServiceImpl implements RoleDubboService {

	@Autowired
	private RoleService	service;

	@Override
	public Response<Boolean> createRole(RoleDto record) {
		log.info("start----createRole--record:{}", record);
		Response<Boolean> response = new Response<Boolean>();
		try {
			RolePo rolePo = BeanConvertUtils.convert(record, RolePo.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.createRole(rolePo));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----createRole");
		return response;
	}

	@Override
	public Response<Boolean> updateRole(RoleDto record) {
		log.info("start----updateRole--record:{}", record);
		Response<Boolean> response = new Response<Boolean>();
		try {
			RolePo rolePo = BeanConvertUtils.convert(record, RolePo.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.updateRole(rolePo));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----updateRole");
		return response;
	}

	@Override
	public Response<RoleDto> findOne(Long roleId) {
		log.info("start----findOne--roleId:{}", roleId);
		Response<RoleDto> response = new Response<RoleDto>();
		try {
			RolePo po = service.findOne(roleId);
			RoleDto dto = BeanConvertUtils.convert(po, RoleDto.class);
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
	public Response<Boolean> deleteRole(List<Long> ids) {
		log.info("start----deleteRole--ids:{}", ids);
		Response<Boolean> response = new Response<Boolean>();
		try {
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.deleteRole(ids));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----deleteRole");
		return response;
	}

	@Override
	public Response<Boolean> isRelatedWithUser(Long id) {
		log.info("start----isRelatedWithUser--id:{}", id);
		Response<Boolean> response = new Response<Boolean>();
		try {
			response.setResultObject(service.countUserByRole(id));
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----isRelatedWithUser");
		return response;
	}

	@Override
	public Response<List<UserDto>> queryRelevantUsers(Long id) {
		log.info("start----queryRelevantUsers--id:{}", id);
		Response<List<UserDto>> response = new Response<>();
		try {
			List<UserPo> pos = service.queryRelevantUsers(id);
			List<UserDto> dtos = BeanConvertUtils.convertList(pos, UserDto.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
			response.setResultObject(dtos);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----queryRelevantUsers");
		return response;
	}

	@Override
	public Response<List<ResourceDto>> queryRelevantRes(Long id) {
		log.info("start----queryRelevantRes--id:{}", id);
		Response<List<ResourceDto>> response = new Response<>();
		try {
			List<ResourcePo> pos = service.queryRelevantRes(id);
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
		log.info("end----queryRelevantRes");
		return response;
	}

	@Override
	public Response<PageResult<RoleDto>> queryRoleByParam(RoleDto record) {
		log.info("start----queryRoleByParam--record:{}", record);
		Response<PageResult<RoleDto>> response = new Response<>();
		try {
			PageInfo<RolePo> pageInfo = service.queryRoleByParam(BeanConvertUtils.convert(record, RolePo.class));
			List<RoleDto> dtos = BeanConvertUtils.convertList(pageInfo.getList(), RoleDto.class);
			response.setResultObject(PageResult.getPageResultByPageInfo(pageInfo, dtos));
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----queryRoleByParam");
		return response;
	}

	@Override
	public Response<Boolean> addResToRole(RoleDto roleDto) {
		log.info("start----addResToRole--roleDto:{}", roleDto);
		Response<Boolean> response = new Response<Boolean>();
		try {
			service.addResToRole(BeanConvertUtils.convert(roleDto, RolePo.class));
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----addResToRole");
		return response;
	}

	@Override
	public Response<Boolean> removeResFromRole(RoleDto roleDto) {
		log.info("start----removeResFromRole--roleDto:{}", roleDto);
		Response<Boolean> response = new Response<Boolean>();
		try {
			service.removeResFromRole(BeanConvertUtils.convert(roleDto, RolePo.class));
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----removeResFromRole");
		return response;
	}

	@Override
	public Response<PageResult<RoleDto>> queryRolesByLoginName(String loginName, Integer pageSize, Integer pageNum) {
		log.info("start----queryRolesByLoginName--loginName:{}", loginName);
		Response<PageResult<RoleDto>> response = new Response<>();
		try {
			PageInfo<RolePo> pageInfo = service.queryRolesByLoginName(loginName, pageSize, pageNum);
			List<RoleDto> dtos = BeanConvertUtils.convertList(pageInfo.getList(), RoleDto.class);
			response.setResultObject(PageResult.getPageResultByPageInfo(pageInfo, dtos));
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		log.info("end----queryRolesByLoginName");
		return response;
	}


}
