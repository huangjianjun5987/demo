package com.yatang.sc.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.yatang.sc.facade.common.Constants;
import com.yatang.sc.facade.dubboservice.UserDubboService;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	
	@Autowired
	private UserDubboService userDubboService;
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// TODO Auto-generated method stub
		
		if(request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
		
		return super.onAccessDenied(request, response, mappedValue);
	}
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
//		return super.onLoginSuccess(token, subject, request, response);
		
//		request.getServletContext().setAttribute(Constants.CURRENT_USER, userDubboService.findByUsername(subject.getPrincipal().toString()));
		return true;
	}

}
