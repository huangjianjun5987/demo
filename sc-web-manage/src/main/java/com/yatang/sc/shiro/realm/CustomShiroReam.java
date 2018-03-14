package com.yatang.sc.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.facade.dto.system.UserDto;
import com.yatang.sc.facade.dubboservice.UserDubboService;

public class CustomShiroReam extends AuthorizingRealm {

//	private static final String myRealmName = "OlesRealm";
	

	@Autowired
	private UserDubboService userDubboService;
	
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pPrincipals) {
		String loginName = (String)pPrincipals.getPrimaryPrincipal();
		if(!StringUtils.isEmpty(loginName)){
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

			return authorizationInfo;
		}
		return null;
	}

	/* 
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken pToken)
			throws AuthenticationException {
		String loginName = (String)pToken.getPrincipal();

		if(!StringUtils.isEmpty(loginName)){
			Response<UserDto> userDetaileResponse = userDubboService.findByUsername(loginName);
			if(userDetaileResponse.isSuccess()){
				UserDto userDetaile = userDetaileResponse.getResultObject();
				if(userDetaile != null){
					AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
							userDetaile.getUserName(), 
							userDetaile.getPassword(), 
							ByteSource.Util.bytes(userDetaile.getCredentialsSalt()),//salt=username+salt
							this.getName());
					return authcInfo;
				}
			}
		}
		return null;
	}

}
