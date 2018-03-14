package com.yatang.sc.operation.util;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @描述: session回话的工具类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/17 11:19
 * @版本: v1.0
 */
public class SessionUtil {
    private SessionUtil() {
    }

    /**
     * 设置session会话
     * @param key
     * @param value
     */
    public static void  setSession(String key,Object value){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(key,value);
    }

    /**
     * 通过键获取对应的session保存的value
     * @param key
     * @return
     */
    public static Object getSession(String key){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
      return session.getAttribute(key);
    }

    /**
     * @Description: 如果用户未登录返回未登录
     * @date 2017/7/24- 16:44
     * @param loginInfoVO
     */
    public static Response<Void> logOut(LoginInfoVO loginInfoVO){
        Response<Void> response = new Response();
        if (loginInfoVO == null || loginInfoVO.getUserId() == null || loginInfoVO.getLoginName() == null) {
            response.setCode(CommonsEnum.RESPONSE_401.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
            return response;
        }
        return null;
    }
}
