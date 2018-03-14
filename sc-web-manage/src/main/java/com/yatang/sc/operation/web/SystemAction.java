package com.yatang.sc.operation.web;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yatang.sc.operation.vo.pm.UserPasswordVo;
import com.yatang.xc.mbd.biz.system.dto.UserPasswordDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.UserDubboService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.operation.vo.LoginInfoVo;
import com.yatang.sc.operation.vo.MenuVo;
import com.yatang.sc.operation.vo.RoleVo;
import com.yatang.xc.mbd.biz.system.dto.LeftMenuDTO;
import com.yatang.xc.mbd.biz.system.dto.UserInfoDetaileDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.AuthorityDubboService;
import com.yatang.xc.mbd.biz.system.dubboservice.SystemLoginDubboService;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;

import lombok.extern.log4j.Log4j;

/**
 * 
 * @描述 获取用户登录相关信息、获取菜单信息API
 * @作者 libin
 * @创建时间 2017年6月23日-下午2:33:27
 * @版本 
 *
 */
@Log4j
@RestController
@RequestMapping(value = "/sc/system")
public class SystemAction {

	//@Autowired
	//private UserDubboService userDubboService;

	@Autowired
	private AuthorityDubboService authorityDubboService;
	
	@Autowired
	private SystemLoginDubboService systemLoginDubboService;

	@Autowired
	private UserDubboService userDubboService;
	
//	@RequestMapping(value = "/user_self", method = RequestMethod.GET )
//    public Response<UserVo> queryUser_self(HttpServletRequest req){
//        Response<UserVo> response= new Response<>();
//
//        if(SecurityUtils.getSubject().isAuthenticated()){
//        	String username = (String) SecurityUtils.getSubject().getPrincipal();
////        	Response<Set<String>> permissions = userDubboService.findPermissions(username);
////        	username = "zhaoliu";
//
//        	Response<UserDto> userResponse = userDubboService.findByUsername(username);
//        	UserVo userVo = BeanConvertUtils.convert(userResponse.getResultObject(), UserVo.class);
//        	Response<Set<ResourceDto>> resourceResponse = userDubboService.findMenusPermissions(username);
//        	List<ResourceDto> resourceDtoList = new ArrayList<ResourceDto>();
//        	resourceDtoList.addAll(resourceResponse.getResultObject());
////        	List<ResourceVo> resourceVoList = BeanConvertUtils.convertList(resourceDtoList, ResourceVo.class);
////        	Set<ResourceVo> resourceVoSet = new HashSet<ResourceVo>(resourceVoList);
////        	userVo.setMenus(resourceVoList);
//        	req.setAttribute("user", userResponse.getResultObject());
////        	response.setResultObject(SecurityUtils.getSubject().getPrincipal());
//        	response.setCode(String.valueOf(CommonsEnum.RESPONSE_200.getCode()));
//            response.setSuccess(true);
//
////            response.setResultObject(userResponse.getResultObject());
//            response.setResultObject(userVo);
//        }else {
//            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
//        	response.setCode(String.valueOf(CommonsEnum.RESPONSE_401.getCode()));
//            response.setSuccess(Boolean.FALSE);
//        }
//
//
//        return response;
//    }
	
	
	@RequestMapping(value = "/user", method = RequestMethod.GET )
    public Response<LoginInfoVo> queryUser(HttpServletRequest req, HttpSession session){
        Response<LoginInfoVo> response= new Response<>();
        
        if(SecurityUtils.getSubject().isAuthenticated()){
        	String username = (String) SecurityUtils.getSubject().getPrincipal();
            
            
        	LoginInfoVO loginInfoVO_oc = (LoginInfoVO)SecurityUtils.getSubject().getSession().getAttribute(CURRENT_USER);
        	if(log.isDebugEnabled()){
        		log.debug(loginInfoVO_oc.toString());
        	}
        	LoginInfoVo loginInfoVo = null;
        	if(loginInfoVO_oc == null){        		
        		Response<UserInfoDetaileDTO> UserInfoDetaileResponse = systemLoginDubboService.queryUserDetaileByLongName(username, Boolean.FALSE);
        		loginInfoVo = BeanConvertUtils.convert(UserInfoDetaileResponse.getResultObject(), LoginInfoVo.class);        		
        	}else {
        		loginInfoVo = new LoginInfoVo();
        		loginInfoVo.setUserId(Integer.parseInt(loginInfoVO_oc.getUserId()));
        		loginInfoVo.setEmployeeLogin(loginInfoVO_oc.getLoginName());
        		loginInfoVo.setEmployeeName(loginInfoVO_oc.getRealName());
        		loginInfoVo.setEmployeePhonenumber(loginInfoVO_oc.getPhoneNum());
        		loginInfoVo.setEmployeeCompanyId(Integer.parseInt(loginInfoVO_oc.getCompanyId()));
        		loginInfoVo.setCompanyName("");
        		loginInfoVo.setCompanyType(loginInfoVO_oc.getCompanyType());
        		
        		List<RoleVo> roles = BeanConvertUtils.convertList(loginInfoVO_oc.getRoles(), RoleVo.class);
        		loginInfoVo.setRoles(roles);
        	}
        	
        	
//        	Response<UserDto> userResponse = userDubboService.findByUsername(username);
//        	Response<Set<String>> rolesResponse = systemLoginDubboService.queryRolesByloginName(username);
        	Response<List<LeftMenuDTO>> topMenuDTOResponse = authorityDubboService.readAllAuSysByLoginName(username);
        	List<LeftMenuDTO> topMenuDTOList= topMenuDTOResponse.getResultObject();
        	List<MenuVo> topMenus = BeanConvertUtils.convertList(topMenuDTOList, MenuVo.class);
        	
        	loginInfoVo.setTopMenus(topMenus);
        	response.setResultObject(loginInfoVo);
        	response.setCode(CommonsEnum.RESPONSE_200.getCode());
        	response.setSuccess(true);
        }else {
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
        	response.setCode(String.valueOf(CommonsEnum.RESPONSE_401.getCode()));
            response.setSuccess(Boolean.FALSE);
        }
        
        
        return response;
    }
	
	@RequestMapping(value = "/queryLeftMenus", method = RequestMethod.GET )
    public Response<List<MenuVo>> queryLeftMenus(HttpServletRequest req, Integer menuId){
        Response<List<MenuVo>> response= new Response<>();
        
//        if(StringUtils.isEmpty(topMenuCode)){
        if(null == menuId){
        	response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
        	response.setCode(String.valueOf(CommonsEnum.RESPONSE_400.getCode()));
            response.setSuccess(Boolean.FALSE);
        }else if(SecurityUtils.getSubject().isAuthenticated()){
        	String username = (String) SecurityUtils.getSubject().getPrincipal();

//        	Response<List<LeftMenuDTO>> leftMenuDTOResponse = authorityDubboService.readAllmyEnablePrivileages(username);
        	Response<List<LeftMenuDTO>> leftMenuDTOResponse = authorityDubboService.readLeftMenuOrButtonByIdAndLoginName(menuId, username);
        	List<LeftMenuDTO> leftMenuDTOList= leftMenuDTOResponse.getResultObject();
        	List<MenuVo> leftMenus = BeanConvertUtils.convertList(leftMenuDTOList, MenuVo.class);

        	response.setResultObject(leftMenus);
        	response.setCode(CommonsEnum.RESPONSE_200.getCode());
        	response.setSuccess(true);
        }else {
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
        	response.setCode(String.valueOf(CommonsEnum.RESPONSE_401.getCode()));
            response.setSuccess(Boolean.FALSE);
        }
        
        
        return response;
    }
	
	@RequestMapping(value = "/queryButtons", method = RequestMethod.GET )
    public Response<String []> queryButtons(HttpServletRequest req, Integer menuId){
        Response<String []> response= new Response<>();
        
        if(null == menuId){
        	response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
        	response.setCode(String.valueOf(CommonsEnum.RESPONSE_400.getCode()));
            response.setSuccess(Boolean.FALSE);
        }else if(SecurityUtils.getSubject().isAuthenticated()){
        	String username = (String) SecurityUtils.getSubject().getPrincipal();

        	Response<List<LeftMenuDTO>> leftMenuDTOResponse = authorityDubboService.readLeftMenuOrButtonByIdAndLoginName(menuId, username);
        	List<LeftMenuDTO> leftMenuDTOList= leftMenuDTOResponse.getResultObject();
        	if(null == leftMenuDTOList){
        		leftMenuDTOList = new ArrayList<LeftMenuDTO>();
        	}

        	List<Object> buttonList = new ArrayList<Object>();
        	for(LeftMenuDTO menuDTO:leftMenuDTOList){
        		buttonList.add(menuDTO.getAuthorityCode());
        	}
        	String [] buttons = new String[leftMenuDTOList.size()];
        	buttons = (String[]) buttonList.toArray(buttons);
        	response.setResultObject(buttons);
        	response.setCode(CommonsEnum.RESPONSE_200.getCode());
        	response.setSuccess(true);
        }else {
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
        	response.setCode(String.valueOf(CommonsEnum.RESPONSE_401.getCode()));
            response.setSuccess(Boolean.FALSE);
        }
        
        
        return response;
    }


	/**
	 * @Description 修改用户密码
	 * @author kangdong
	 * @date 2017/11/30- 15:58
	 * @param userPasswordVo
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST )
	public Response<Boolean> updateUserPassword(@Validated @RequestBody UserPasswordVo userPasswordVo) {
		Response<Boolean> response= new Response<>();
		if(!SecurityUtils.getSubject().isAuthenticated()) {
			response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
			response.setCode(String.valueOf(CommonsEnum.RESPONSE_401.getCode()));
			response.setSuccess(Boolean.FALSE);
			return response;
		}
		UserPasswordDTO userPasswordDto = BeanConvertUtils.convert(userPasswordVo,UserPasswordDTO.class);
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		userPasswordDto.setLoginName(username);
		if(!Objects.equals(userPasswordDto.getNewPassword(),userPasswordDto.getConfirmPassword())) {
			response.setCode(CommonsEnum.RESPONSE_400.getCode());
			response.setErrorMessage("新密码与确认密码不一致!");
			return response;
		}
		response = userDubboService.updateUserPassword(userPasswordDto);
		if(!response.isSuccess()) {
			response.setCode(CommonsEnum.RESPONSE_402.getCode());
			response.setErrorMessage("原密码输入错误!");
		}
		return response;
	}
}
