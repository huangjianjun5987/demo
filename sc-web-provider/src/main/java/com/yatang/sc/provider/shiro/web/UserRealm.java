package com.yatang.sc.provider.shiro.web;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.system.RoleDto;
import com.yatang.sc.facade.dto.system.UserDto;
import com.yatang.sc.facade.dubboservice.UserDubboService;
import org.apache.commons.lang.StringUtils;
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

import java.util.HashSet;
import java.util.Set;

/**
 * @描述: 登录认证授权realm
 * @作者: huangjianjun
 * @创建时间: 2018年1月11日-下午2:33:33 .
 */
public class UserRealm extends AuthorizingRealm {
	
	private static final String		myRealmName	= "ProviderRealm";
	
	@Autowired
	private UserDubboService userService;
	
	/**
	 * 权限认证授权
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection paramPrincipalCollection) {
		String loginName = (String) paramPrincipalCollection.getPrimaryPrincipal();
		if (!StringUtils.isEmpty(loginName)) {
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
			Response<Set<RoleDto>> rolsRes = userService.findUserRoles(loginName);
			if (rolsRes.isSuccess()){
				Set<RoleDto> roles = rolsRes.getResultObject();
				Set<String> roleStrs = new HashSet<String>();
				for (RoleDto roleDto : roles) {
					roleStrs.add(roleDto.getRole());
				}
				authorizationInfo.setRoles(roleStrs);
				return authorizationInfo;
			}
		}
		return null;
	}


	/**
	 * 登录认证
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken paramAuthenticationToken)
			throws AuthenticationException {
		String loginName = (String)paramAuthenticationToken.getPrincipal();
		if (!StringUtils.isEmpty(loginName)) {
			Response<UserDto> userDetailRes = userService.findByUsername(loginName);
			UserDto userDetail = userDetailRes.getResultObject();
			if (userDetail != null) {
				AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userDetail.getUserName(),
						userDetail.getPassword(), ByteSource.Util.bytes(userDetail.getCredentialsSalt()),
						myRealmName);
				return authcInfo;
			}
		}
		return null;
	}
}
