package com.yatang.sc.shiro.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.SessionException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.CommonsEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述: 系统登录、退出接口
 * @作者: libin
 * @创建时间:
 * @版本: v1.0
 */

@Slf4j
@RequestMapping(value = "sc/system")
@RestController
public class LoginLogoutAction {

	// @RequestMapping(value = "/login")
	// public Response<String> login(HttpServletRequest req){
	// Response<String> response= new Response<>();
	//
	// if(SecurityUtils.getSubject().isAuthenticated()){
	// response.setResultObject("登录成功");
	// response.setCode(String.valueOf(CommonsEnum.RESPONSE_200.getCode()));
	// response.setSuccess(true);
	// }else {
	// String exceptionClassName =
	// (String)req.getAttribute("shiroLoginFailure");
	// String error = null;
	// if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
	// error = "用户名/密码错误";
	// } else
	// if(IncorrectCredentialsException.class.getName().equals(exceptionClassName))
	// {
	// error = "用户名/密码错误";
	// } else if("jCaptcha.error".equals(exceptionClassName)) {
	// error = "验证码错误";
	// } else if(StringUtils.isNotEmpty(exceptionClassName)) {
	// error = "其他错误：" + exceptionClassName;
	// } else if(StringUtils.isEmpty(exceptionClassName)) {
	// error = "其他错误：" + exceptionClassName;
	// }
	//
	// response.setErrorMessage(error);
	// response.setResultObject(error);
	// response.setCode(String.valueOf(CommonsEnum.RESPONSE_402.getCode()));
	// response.setSuccess(Boolean.FALSE);
	// }
	//
	//
	// return response;
	// }
	@RequestMapping(value = "/login")
	public Response<String> login(HttpServletRequest req) {
		Response<String> response = new Response<>();
		response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
		response.setResultObject(CommonsEnum.RESPONSE_401.getName());
		response.setCode(String.valueOf(CommonsEnum.RESPONSE_401.getCode()));
		response.setSuccess(Boolean.FALSE);
		return response;
	}



	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Response<String> logout(HttpServletRequest req) {
		Response<String> response = new Response<>();
		log.debug("log Test");
		if (SecurityUtils.getSubject().isAuthenticated()) {
			try {
				SecurityUtils.getSubject().logout();
			} catch (SessionException ise) {
				log.error("Encountered session exception during logout.  This can generally safely be ignored.", ise);
			}
			response.setResultObject("退出成功");
			response.setCode(String.valueOf(CommonsEnum.RESPONSE_200.getCode()));
			response.setSuccess(true);
		} else {
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			response.setResultObject(CommonsEnum.RESPONSE_401.getName());
			response.setCode(String.valueOf(CommonsEnum.RESPONSE_401.getCode()));
			response.setSuccess(Boolean.FALSE);
		}

		return response;
	}



	@RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
	public Response<Object> unauthorized(HttpServletRequest req) {
		Response<Object> response = new Response<>();

		response.setErrorMessage(CommonsEnum.RESPONSE_403.getName());
		response.setResultObject(CommonsEnum.RESPONSE_403.getName());
		response.setCode(String.valueOf(CommonsEnum.RESPONSE_403.getCode()));
		response.setSuccess(Boolean.FALSE);

		return response;
	}
}
