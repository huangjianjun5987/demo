package com.yatang.sc.facade.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageInfo;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.domain.ResourcePo;
import com.yatang.sc.facade.domain.RolePo;
import com.yatang.sc.facade.domain.SupplierBasicInfoPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.domain.SupplierOperTaxInfoPo;
import com.yatang.sc.facade.domain.UserPo;
import com.yatang.sc.facade.dto.system.ResourceDto;
import com.yatang.sc.facade.dto.system.RoleDto;
import com.yatang.sc.facade.dto.system.UserDetailDto;
import com.yatang.sc.facade.dto.system.UserDto;
import com.yatang.sc.facade.dubboservice.UserDubboService;
import com.yatang.sc.facade.service.SupplierService;
import com.yatang.sc.facade.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service("userDubboService1")
public class UserDubboServiceImpl implements UserDubboService {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private SupplierService spService;
	
	@Override
	public Response<Boolean> createUser(UserDto record) {
		log.info("UserDubboServiceImpl--->createUser--param:{}",JSON.toJSONString(record));
		Response<Boolean> response = new Response<>();
		try {
			if (service.checkUserExist(record.getUserName())) {
				response.setCode(CommonsEnum.RESPONSE_10000.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_10000.getName());
				response.setSuccess(false);
				return response;
			}
			if(null != record.getSpId() && service.checkSpIdExist(record.getSpId())){
				response.setCode(CommonsEnum.RESPONSE_510.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_510.getName());
				response.setSuccess(false);
			}else{
				UserPo userPo = BeanConvertUtils.convert(record, UserPo.class);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setSuccess(service.createUser(userPo));
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<Boolean> updateUser(UserDto record) {
		log.info("UserDubboServiceImpl--->updateUser--param:{}",JSON.toJSONString(record));
		Response<Boolean> response = new Response<Boolean>();
		try {
			UserPo userPo = BeanConvertUtils.convert(record, UserPo.class);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(service.updateUser(userPo));
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> deleteUser(Long userId) {
		log.info("UserDubboServiceImpl--->updateUser--param:{}",userId);
		Response<Boolean> response = new Response<Boolean>();
		try {
			response.setSuccess(service.deleteUser(userId));
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> changePassword(Long userId, String newPassword) {
		log.info("UserDubboServiceImpl--->updateUser--userId:{},newPassword:{}",userId,newPassword);
		Response<Boolean> response = new Response<Boolean>();
		try {
			service.changePassword(userId, newPassword);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<UserDetailDto> findOne(Long userId) {
		log.info("UserDubboServiceImpl--->findOne--userId:{}",userId);
		Response<UserDetailDto> response = new Response<UserDetailDto>();
		try {
			UserPo po = service.findOne(userId);
			UserDetailDto userDetail = BeanConvertUtils.convert(po, UserDetailDto.class);
			log.info("UserDubboServiceImpl--->findOne--userDetail:{}",JSON.toJSONString(userDetail));
			queryProviderInfo(userDetail);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(userDetail);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	private void queryProviderInfo(UserDetailDto userDetail) {
		if(userDetail!=null && StringUtils.isNotBlank(userDetail.getSpId())){
			log.info("UserDubboServiceImpl--->queryProviderInfo--spId:{}",userDetail.getSpId());
			SupplierInfoPo spInfo = spService.queryById(userDetail.getSpId());
			if (null == spInfo) {
				log.error("查询不到用户对应的供应商信息，供应商id：{}",userDetail.getSpId());
				return;
			}
			SupplierBasicInfoPo basePo = spInfo.getSupplierBasicInfo();
			SupplierOperTaxInfoPo operTaxPo = spInfo.getSupplierOperTaxInfo();
			userDetail.setSpNo(basePo.getSpNo());
			userDetail.setSpName(basePo.getCompanyName());
			userDetail.setSettledTime(basePo.getSettledTime());
			userDetail.setGrade(basePo.getGrade());
			userDetail.setAddress(operTaxPo.getCompanyDetailAddress());
			log.info("UserDubboServiceImpl--->queryProviderInfo--userDetail:{}",JSON.toJSONString(userDetail));
		}
	}



	@Override
	public Response<List<UserDto>> findAll() {
		log.info("UserDubboServiceImpl--->findAll");
		Response<List<UserDto>> response = new Response<List<UserDto>>();
		try {
			List<UserPo> pos = service.findAll();
			List<UserDto> dtos = BeanConvertUtils.convertList(pos, UserDto.class);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(dtos);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<PageResult<UserDetailDto>> findByPage(UserDetailDto record,Integer pageNum,Integer pageSize) {
		log.info("UserDubboServiceImpl--->findByPage--userDetail:{}",JSON.toJSONString(record));
		Response<PageResult<UserDetailDto>> response = new Response<PageResult<UserDetailDto>>();
		try {
			UserPo po = BeanConvertUtils.convert(record, UserPo.class);
			PageInfo<UserPo> pageInfo = service.findByPage(po,pageNum,pageSize);
			List<UserPo> pos = pageInfo.getList();
			List<UserDetailDto> users = BeanConvertUtils.convertList(pos, UserDetailDto.class);
			for (UserDetailDto userDetailDto : users) {
				queryProviderInfo(userDetailDto);
			}
			PageResult<UserDetailDto> page = new PageResult<UserDetailDto>();
			page.setPageNum(pageInfo.getPageNum());
			page.setPageSize(pageInfo.getPageSize());
			page.setTotal(pageInfo.getTotal());
			page.setData(users);
			log.info("UserDubboServiceImpl--->findByPage--users:{}",JSON.toJSONString(users));
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(page);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<UserDto> findByUsername(String username) {
		log.info("UserDubboServiceImpl--->findByUsername--username:{}",JSON.toJSONString(username));
		Response<UserDto> response = new Response<UserDto>();
		try {
			UserPo po = service.findByUsername(username);
			UserDto dto = BeanConvertUtils.convert(po, UserDto.class);
			UserDetailDto userDetailDto = new UserDetailDto();
			userDetailDto.setSpId(po.getSpId());
			queryProviderInfo(userDetailDto);
			dto.setSpName(userDetailDto.getSpName());
			log.info("UserDubboServiceImpl--->findByUsername--result:{}",JSON.toJSONString(dto));
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(dto);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Set<RoleDto>> findUserRoles(String userName) {
		Response<Set<RoleDto>> response = new Response<Set<RoleDto>>();
		try {
			Set<RolePo> roles = service.findRoles(userName);
			Set<RoleDto> dtos= new HashSet<RoleDto>();
			for (RolePo rolePo : roles) {
				dtos.add(BeanConvertUtils.convert(rolePo, RoleDto.class));
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(dtos);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Set<String>> findPermissions(String username) {
		Response<Set<String>> response = new Response<Set<String>>();
		try {
			Set<String> permissions = service.findPermissions(username);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(permissions);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<Set<ResourceDto>> findMenusPermissions(String username) {
		Response<Set<ResourceDto>> response = new Response<Set<ResourceDto>>();
		try {
			Set<ResourcePo> pos = service.findMenusPermissions(username);
			Set<ResourceDto> result = new HashSet<ResourceDto>();
			for (ResourcePo resourcePo : pos) {
				result.add(BeanConvertUtils.convert(resourcePo, ResourceDto.class));
			}
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(result);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<Boolean> authorRoles(Long userId, String roleIds) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			service.authorRoles(userId, roleIds);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<Boolean> changeCurUserPassword(Long userId, String oldPassword, String newPassword) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			if(!service.changeCurUserPassword(userId, oldPassword,newPassword)){
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_509.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_509.getName());
			}else{
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<Boolean> deleteUserRoles(Long userId, String roleIds) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			service.deleteUserRole(userId, roleIds);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


}
