package com.yatang.sc.operation.web;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.google.common.base.Strings;
import com.yatang.sc.common.utils.CollectionUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.operation.util.JsonUtil;
import com.yatang.sc.operation.vo.prod.BranchCompanyInfoVo;
import com.yatang.sc.web.view.XlsData;
import com.yatang.sc.web.view.XlsView;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationSCService;
import com.yatang.xc.mbd.biz.system.dto.CompanyDTO;
import com.yatang.xc.mbd.biz.system.dto.CompanyQueryDTO;
import com.yatang.xc.mbd.biz.system.dto.UserTypeCompanyDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.CompanyDubboService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @描述: BaseAction.
 * @作者: huangjianjun
 * @创建时间: 2017年5月19日-下午2:28:40 .
 * @版本: 1.0 .
 */
public class BaseAction {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CompanyDubboService companyDubboService;
	@Autowired
	private OrganizationSCService organizationSCService;

	@InitBinder()
	public void initBinder(WebDataBinder binder) {
		// 注册自定义的属性编辑器
		// 1、日期
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// CustomDateEditor dateEditor = new CustomDateEditor(df, true);
		// // 表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换
		// binder.registerCustomEditor(Date.class, dateEditor);
		// 自定义的电话号码编辑器(和【4.16.1、数据类型转换】一样)
	}



	/** 获取客户端的ip地址 */
	protected static String getIp(HttpServletRequest req) {
		String ip = req.getHeader("x-forwarded-for") == null ? req.getHeader("X-Forwarded-For")
				: req.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}



	/** 获取当前的openid */
	protected static String getCurrentOpenid(HttpServletRequest req) {
		String openid = req.getParameter("openid");
		if (StringUtils.isBlank(openid)) {
			openid = (String) req.getSession().getAttribute("openid");
		}
		return openid;
	}



	/**
	 * 从uri中找出参数
	 * 
	 * @param request
	 * @param method
	 * @return
	 */
	protected static String getParameterByUri(HttpServletRequest request, String method) {
		String uri = request.getRequestURI();
		int b = uri.indexOf(method);
		int e = uri.indexOf('.', b);
		return uri.substring(b + method.length(), e);
	}



	/**
	 * 向客户端返回json或者jsonp数据，支持ajax跨域请求 修改成不往上抛异常
	 * 
	 * @param result
	 * @param req
	 * @param res
	 */
	protected void writeToView(Object result, HttpServletRequest req, HttpServletResponse res) {
		try {
			if (result != null) {
				res.setHeader("Pragma", "No-cache");
				res.setHeader("Cache-Control", "no-cache");
				res.setDateHeader("Expires", 0);
				res.setContentType("text/json");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				String callback = req.getParameter("callback");
				String json = "";
				if (result instanceof String) {
					json = (String) result;
				} else {
					json = JsonUtil.beanToJsonStrFormat(result);
				}
				// 是否为跨域请求，如果是，则拼接jsonp数据
				if (StringUtils.isNotBlank(callback)) {
					json = callback + "(" + json + ")";
				}
				out.write(json);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.info("json writeToView 出错!", e);
		}

	}



	/**
	 * @Description:返回客户端数据
	 * @author huangjianjun
	 * @date 2017年5月19日 下午2:30:05
	 * @param result
	 * @param request
	 * @param response
	 */
	protected void responseJson(Object result, HttpServletRequest request, HttpServletResponse response) {
		writeToView(result, request, response);
	}



	/** 向客户端返回json数据 */
	public static void responseJson(Object result, HttpServletResponse res) throws Exception {
		if (result != null) {
			res.setContentType("text/json");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(JsonUtil.beanToJsonStr(result));
			out.flush();
			out.close();
		}
	}



	// /**
	// * 校验必要参数
	// * @param reqParams
	// * 必要参数名称数组
	// * @throws RequParamsException
	// * @return 参数map
	// */
	// protected Map<String, Object> validateParams(String[] reqParams,
	// HttpServletRequest request,
	// HttpServletResponse response) throws RequParamsException {
	// Map<String, Object> paramsMap = RequestUtils.getParamMap(request);
	// Map<String, Object> resultMap = new HashMap<String, Object>();
	// StringBuffer sb = new StringBuffer("缺少【");
	// for (String param : reqParams) {
	// if (!paramsMap.containsKey(param)) {
	// sb.append("'" + param + "' ");
	// }
	// resultMap.put(param, paramsMap.get(param));
	// }
	// if (sb.length() > 3) {
	// sb.append("】参数！");
	// responseJson(new ApiResponse(ApiResponse.FAILURE_CODE,
	// ApiResponse.FAILURE_MSG), request, response);
	// throw new RequParamsException(sb.toString());
	// }
	// if (paramsMap.containsKey("pageNum") && paramsMap.containsKey("perPage"))
	// {
	// Integer pageNum = Integer.valueOf((String) paramsMap.get("pageNum"));
	// Integer perPage = Integer.valueOf((String) paramsMap.get("perPage"));
	// paramsMap.put("rowStart", (pageNum - 1) * perPage);
	// paramsMap.put("limit", perPage);
	// } else {
	// paramsMap.put("rowStart", 0);
	// paramsMap.put("limit", 10);
	// }
	// resultMap.putAll(paramsMap);
	// return resultMap;
	// }
	/**
	 * 
	 * <method description> 请求成功的通用Response
	 * 
	 * @author zhoubaiyun
	 * @return
	 */
	protected <T> Response<T> getSuccessResponse() {
		Response<T> response = new Response<T>();
		response.setSuccess(true);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		return response;
	}



	/**
	 * 
	 * <method description> 请求成功的通用Response
	 * 
	 * @author zhoubaiyun
	 * @param data
	 * @return
	 */
	protected <T> Response<T> getSuccessResponse(T data) {
		Response<T> response = new Response<T>();
		response.setSuccess(true);
		response.setCode(CommonsEnum.RESPONSE_200.getCode());
		response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		response.setResultObject(data);
		return response;
	}



	/**
	 * 
	 * <method description> 请求失败通用Response
	 * 
	 * @author zhoubaiyun
	 * @return
	 */
	protected <T> Response<T> getFailResponse() {
		Response<T> response = new Response<T>();
		response.setSuccess(false);
		response.setCode(CommonsEnum.RESPONSE_500.getCode());
		response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		return response;
	}



	/**
	 * 
	 * <method description> 获取导出XLS的视图模型对象
	 * 
	 * @author zhoubaiyun
	 * @param xlsData
	 * @return
	 */
	protected ModelAndView getXlsModelAndView(XlsData xlsData) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setView(new XlsView());
		modelAndView.addObject("xlsData", xlsData);
		return modelAndView;
	}



	/**
	 * 根据用户id 查询所有的子公司id  若是总部则返回空（用于业务查询参数用）
	 * @param userId
	 * @return
	 */
	public List<String> queryUserCompanyIds(String userId){
		UserTypeCompanyDTO companyDTO = getUserTypeCompanyInfo(userId);
		return queryUserCompanyIds(companyDTO);

	}

	/**
	 * 在用户权限的基础上根据用户所输入的子公司id 或者子公司名称查询子公司值清单  若是总部则返回所有的子公司（用于子公司值清单）
	 * @param userId 用户id
	 * @param companyId 子公司id 没有值则传null
	 * @param companyName 子公司名称 没有值则传null
	 * @author yinyuxin
	 * @return
	 */
	public List<BranchCompanyInfoVo> queryUserCompanyInfosByParam(String userId,String companyId,String companyName){
		//1:根据用户id 查询当前用户权限内的子公司集合
		List<BranchCompanyInfoVo> usersComapanyInfos=this.queryUserCompanyInfosByUserId(userId);
		if (null==usersComapanyInfos||usersComapanyInfos.size()<=0){
			return usersComapanyInfos;
		}
		if (Strings.isNullOrEmpty(companyId) && Strings.isNullOrEmpty(companyName)){
			return usersComapanyInfos;
		}
		//根据参数中的子公司id和子公司名称查询满足条件的子公司集合
		CompanyDTO companyQueryDTO=new CompanyDTO();
		companyQueryDTO.setCompanyName(companyName);
		if(!Strings.isNullOrEmpty(companyId)){
			companyQueryDTO.setCompanyId(Integer.valueOf(companyId));
		}

		List<CompanyDTO> companyDtos= companyDubboService.fuzzyFindCompanyInfo(companyQueryDTO).getResultObject();
		List<BranchCompanyInfoVo> returnCompanyInfos=new ArrayList<>();
		//权限内的子公司与 查询出的子公司做交集处理
		if (companyDtos !=null && companyDtos.size()>0){
			for (CompanyDTO companyDTO:companyDtos){
				for (BranchCompanyInfoVo branchCompanyInfoVo:usersComapanyInfos){
					if (branchCompanyInfoVo.getId().equals(String.valueOf(companyDTO.getCompanyId()))){
						returnCompanyInfos.add(branchCompanyInfoVo);
					}
				}
			}
			return returnCompanyInfos;
		}else {
			return usersComapanyInfos;
		}
	}


	/**
	 * 查询用户下所有的子公司信息，返回类型为对象(先从小超运营获取权限内所有的公司，再发给主数据筛选真实的公司)
	 * @author yinyuxin
	 * @param userId
	 * @return
	 */
	public List<BranchCompanyInfoVo> queryUserCompanyInfosByUserId(String userId){
		Response<UserTypeCompanyDTO> dtoResponse = companyDubboService
				.findUserTypeCompanyByUserId(Integer.valueOf(userId));
		if (dtoResponse==null || !dtoResponse.isSuccess()){
			return null;
		}
		List<BranchCompanyInfoVo> data=new ArrayList<>();
		List<CompanyDTO> companys=dtoResponse.getResultObject().getCompanys();
		List<String> companyIds=new ArrayList<>();
		if (dtoResponse.getResultObject()!=null && companys!=null && companys.size()>0){
			if (dtoResponse.getResultObject().isHeadQuarters()){
				//总部人员返回所有的子公司
				Response<List<CompanyDTO>> allCompanyResponse= companyDubboService.findAllCompany();
				if (dtoResponse==null || !dtoResponse.isSuccess()){
					return null;
				}
				if (allCompanyResponse.getResultObject()!=null && allCompanyResponse.getResultObject().size()>0){
					for (CompanyDTO companyDTO:allCompanyResponse.getResultObject()){
						companyIds.add(companyDTO.getCompanyId()+"");
					}
				}
			}else {
				for (CompanyDTO companyDTO:companys){
					companyIds.add(companyDTO.getCompanyId()+"");
				}
			}
		}

		//因为小超运营带有虚拟公司，供应链只用真实公司，所以筛选出的子公司列表还需要在主数据过滤
		Response<Map<String, String>> mapResponse = organizationSCService.queryCompanyByIds(companyIds);
		if (mapResponse.isSuccess() && mapResponse.getResultObject()!=null){
			Set<String> companyIdSet=mapResponse.getResultObject().keySet();
			if (companyIdSet!=null && companyIdSet.iterator().hasNext()){
				for (String companyId:companyIdSet){
					BranchCompanyInfoVo branchCompanyInfoVo=new BranchCompanyInfoVo();
					branchCompanyInfoVo.setId(companyId);
					branchCompanyInfoVo.setName(mapResponse.getResultObject().get(companyId));
					data.add(branchCompanyInfoVo);
				}
			}
		}
		return data;
	}


	public UserTypeCompanyDTO getUserTypeCompanyInfo(String userId){

		Response<UserTypeCompanyDTO> dtoResponse = companyDubboService.findUserTypeCompanyByUserId(Integer.valueOf(userId));
		if(dtoResponse != null && dtoResponse.isSuccess()){
			return dtoResponse.getResultObject();
		}
		return null;
	}

	/**
	 * 检查是否是总部账号
	 * @param param
	 * @return
	 */
	public boolean isHeaderCompany(UserTypeCompanyDTO param){

		if(param == null){
			return false;
		}
		if(param.isHeadQuarters()){
			return true;
		}
		return false;
	}


	public List<String> queryUserCompanyIds(UserTypeCompanyDTO param){

		List<BranchCompanyInfoVo> data=new ArrayList<>();
		List<String> companyIds=new ArrayList<>();
		if(param == null){
			return companyIds;
		}
		if (param.isHeadQuarters()){
			return companyIds;
		}
		List<CompanyDTO> companys = param.getCompanys();
		if (companys!=null && companys.size()>0){
			for (CompanyDTO companyDTO:companys){
				companyIds.add(companyDTO.getCompanyId()+"");
			}
		}
		//因为小超运营带有虚拟公司，供应链只用真实公司，所以筛选出的子公司列表还需要在主数据过滤
		Response<Map<String, String>> mapResponse = organizationSCService.queryCompanyByIds(companyIds);
		if (mapResponse.isSuccess() && mapResponse.getResultObject()!=null){
			Set<String> companyIdSet=mapResponse.getResultObject().keySet();
			if (companyIdSet!=null && companyIdSet.iterator().hasNext()){
				for (String companyId:companyIdSet){
					BranchCompanyInfoVo branchCompanyInfoVo=new BranchCompanyInfoVo();
					branchCompanyInfoVo.setId(companyId);
					branchCompanyInfoVo.setName(mapResponse.getResultObject().get(companyId));
					data.add(branchCompanyInfoVo);
				}
			}
		}
		for (BranchCompanyInfoVo branchCompanyInfoVo : data){
			companyIds.add(branchCompanyInfoVo.getId());
		}
		return companyIds;
	}


	public boolean isContains(List<String> listParam, String companyId){

		boolean result = false;
		if(listParam != null){
			for (String param : listParam){
				if(param.equals(companyId)){
					result = true;
					break;
				}
			}
		}
		return result;
	}

}
