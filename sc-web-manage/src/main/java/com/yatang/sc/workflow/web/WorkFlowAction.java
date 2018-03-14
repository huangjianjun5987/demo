package com.yatang.sc.workflow.web;

import static com.yatang.sc.facade.common.Constants.CURRENT_USER;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.bpm.common.PageResult;
import com.yatang.sc.bpm.dto.TaskProcessRecordDto;
import com.yatang.sc.payment.common.ResponseUtils;
import com.yatang.sc.workflow.vo.QueryProcessParam;
import com.yatang.sc.workflow.vo.TaskProcessRecordVo;
import com.yatang.xc.mbd.biz.system.dto.UserTypeCompanyDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.CompanyDubboService;
import com.yatang.xc.mbd.web.commons.ResourceUtil;
import com.yatang.xc.mbd.web.system.vo.RoleVO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.bpm.dto.WorkFlowDto;
import com.yatang.sc.bpm.dubboservice.WorkFlowDubboService;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.operation.util.SessionUtil;
import com.yatang.sc.operation.web.BaseAction;
import com.yatang.sc.sorder.web.ReturnRequestAction;
import com.yatang.sc.workflow.vo.WorkFlowVo;
import com.yatang.xc.mbd.web.system.vo.LoginInfoVO;

/**
 * @描述: 工作流引擎Action
 * @作者: huangjianjun
 * @创建时间: 2017年11月16日-下午4:43:19 .
 */
@RestController
@RequestMapping(value = "/sc/bpm")
public class WorkFlowAction extends BaseAction{
	
	private static final Logger log= LoggerFactory.getLogger(ReturnRequestAction.class);
	public final static String SPLIT = "_";
	
	@Autowired
	private WorkFlowDubboService workFlowDubboService;

	@Autowired
	private CompanyDubboService companyDubboService;

	
	/**
	 * @Description: 发布流程
	 * @author huangjianjun
	 * @date 2017年11月16日下午4:44:48
	 */
	@RequestMapping(value = "/newdeploy", method = RequestMethod.POST)
	public Response<Boolean> newdeploy(@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request, @Valid String fileName) throws IOException{
		log.info("上传文件名：{}", file.getOriginalFilename());
		if (file.isEmpty()) {
			log.info("上传文件为空，返回500");
			return getFailResponse();
		}
		return workFlowDubboService.saveNewDeploye(file.getBytes(), fileName);
	}
	

	/**
	 * @Description:删除部署信息 
	 * @author huangjianjun
	 * @date 2017年11月16日下午4:44:58
	 */
	@RequestMapping(value = "/delDeployment", method = RequestMethod.GET)
	public Response<Boolean> delDeployment(HttpServletRequest request, @Valid WorkFlowVo workFlowVo) {
		return workFlowDubboService.deleteProcessDefinitionByDeploymentId(workFlowVo.getDeploymentId());
	}
	
	
	/**
	 * @Description: 查询部署信息和流程定义信息
	 * @author huangjianjun
	 * @date 2017年11月20日下午5:15:50
	 */
	@RequestMapping(value = "/findDeployAndProcessDefList", method = RequestMethod.GET)
	public Response<Map<String,Object>> findDeployAndProcessDefList(){
		return workFlowDubboService.findDeployAndProcessDefList();
	}
	
	
	/**
	 * @Description:查看流程图
	 * @author huangjianjun
	 * @date 2017年11月16日下午4:44:58
	 */
	@RequestMapping(value = "/viewImage", method = RequestMethod.GET)
	public Response<String> viewImage(HttpServletRequest request, @Valid WorkFlowVo workFlowVo) {
		return workFlowDubboService.findImageToBase64(workFlowVo.getDeploymentId(), workFlowVo.getImageName());
	}


	/**
	 * @Description:启动流程实例
	 * @author huangjianjun
	 * @date 2017年11月16日下午4:45:34
	 */
	@RequestMapping(value = "/startProcess", method = RequestMethod.GET)
	public Response<Boolean> startProcess(HttpServletRequest request, @Valid WorkFlowVo workFlowVo) {
		//启动流程实例，让启动的流程实例关联业务
		return workFlowDubboService.saveStartProcess(workFlowVo.getId(),workFlowVo.getType(),10003,"10083");
	}

	/**
	 * @Description:审批任务
	 * @author huangjianjun
	 * @date 2017年11月16日下午4:45:34
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.GET)
	public Response<Boolean> audit(HttpServletRequest request, @Valid WorkFlowVo workFlowVo) {
//		 Response<Boolean> response = new Response<Boolean>();
//		 LoginInfoVO loginInfoVO = (LoginInfoVO) SessionUtil.getSession(CURRENT_USER);
//		 if (loginInfoVO == null || loginInfoVO.getUserId() == null) {
//            response.setCode(CommonsEnum.RESPONSE_401.getCode());
//            response.setSuccess(false);
//            response.setErrorMessage(CommonsEnum.RESPONSE_401.getName());
//            return response;
//		 }
		 WorkFlowDto param= BeanConvertUtils.convert(workFlowVo, WorkFlowDto.class);
		 param.setUserId("1");
		 param.setUserName("admin");
		 //启动流程实例，让启动的流程实例关联业务
		 return workFlowDubboService.audit(param);
	}

	/*
     *@描述:获取高亮流程图
     *@作者:tangqi
     *@时间:2017/11/29 9:43
     */
	@RequestMapping(value = "/processImage", method = RequestMethod.GET)
	public Response<String> processImage(@NotNull(message = "参数不能为空") String taskId, HttpServletResponse response) throws IOException {
		Response<String> imageByteArray = workFlowDubboService.queryProcessImage(taskId);
		/*byte[] bytes = Base64.decodeBase64(imageByteArray.getResultObject());
		String imgString = new String(bytes);
		//response.getOutputStream().write(bytes);
		imageByteArray.setResultObject(imgString);*/
		return imageByteArray;
	}

	@RequestMapping(value = "/processImageByBusi", method = RequestMethod.GET)
	public Response<String> processImageByBusi(String id, String processType) throws IOException {
		Response<String> imageByteArray = workFlowDubboService.queryProcessImage(id, processType);
		return imageByteArray;
	}

	/*
     *@描述:查询流程审核记录
     *@作者:tangqi
     *@时间:2017/11/28 14:49
     */
	@RequestMapping(value = "/queryCommentHis", method = RequestMethod.GET)
	public Response<List<TaskProcessRecordVo>> queryCommentHis(@NotNull(message = "参数不能为空") String taskId) {
		Response<List<TaskProcessRecordVo>> response = new Response<>();
		Response<List<TaskProcessRecordDto>> listResponse = workFlowDubboService.queryCommentHis(taskId);
		response.setErrorMessage(listResponse.getErrorMessage());
		response.setCode(listResponse.getCode());
		response.setSuccess(listResponse.isSuccess());
		response.setResultObject(BeanConvertUtils.convertList(listResponse.getResultObject(), TaskProcessRecordVo.class));
		return response;
	}

	/*
     *@描述:查询流程审核记录
     *@作者:tangqi
     *@时间:2017/11/28 14:49
     */
	@RequestMapping(value = "/queryCommentHisByBusi", method = RequestMethod.GET)
	public Response<List<TaskProcessRecordVo>> queryCommentHisByBusi(String id, String processType) {
		Response<List<TaskProcessRecordVo>> response = new Response<>();
		Response<List<TaskProcessRecordDto>> listResponse = workFlowDubboService.queryCommentHis(id, processType);
		response.setErrorMessage(listResponse.getErrorMessage());
		response.setCode(listResponse.getCode());
		response.setSuccess(listResponse.isSuccess());
		response.setResultObject(BeanConvertUtils.convertList(listResponse.getResultObject(), TaskProcessRecordVo.class));
		return response;
	}


	@RequestMapping(value = "/queryProcessMsgList", method = RequestMethod.POST)
	public Response<PageResult<Map<String, Object>>> queryProcessMsgList(
			@RequestBody @Validated QueryProcessParam param, HttpServletRequest request) {
		String processType = param.getProcessType();
		Map<String, Object> queryParam = param.getMap();
		Response<PageResult<Map<String, Object>>> response = new Response<>();
		//检查参数
		boolean complate = checkParam(processType, queryParam);
		if (!complate) {
			response.setCode("400");
			response.setSuccess(false);
			response.setErrorMessage("传入参数有误");
			return response;
		}
		LoginInfoVO userInfo = (LoginInfoVO) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        if (userInfo == null) {
            response.setCode("400");
            response.setSuccess(false);
            response.setErrorMessage("用户信息为空");
            return response;
        }
		//检查权限
		List<String> assignee = new ArrayList<>();
		Response<List<String>> checkUserAuth = checkUserAuth(userInfo, processType);
		if(checkUserAuth.isSuccess()){
			assignee = checkUserAuth.getResultObject();
		}else{
			response.setSuccess(checkUserAuth.isSuccess());
			response.setErrorMessage(checkUserAuth.getErrorMessage());
			response.setCode(checkUserAuth.getCode());
			return response;
		}
		//查询代办
		queryParam.put("processType", processType);
		response = workFlowDubboService.queryProcessMsgList(assignee, queryParam);
		return response;
	}

	private Response<List<String>> checkUserAuth(LoginInfoVO userInfo, String processType) {
		Response<UserTypeCompanyDTO> userTypeCompany = companyDubboService.findUserTypeCompanyByUserId(new Integer(userInfo.getUserId()));//1
		if(!userTypeCompany.isSuccess()){
			Response<List<String>> failResponse = getFailResponse();
			failResponse.setErrorMessage(userTypeCompany.getErrorMessage());
			return failResponse;
		}
		UserTypeCompanyDTO resultObject = userTypeCompany.getResultObject();
		List<String> assigneeList = new ArrayList<>();
        String companyId = userInfo.getCompanyId();
        List<RoleVO> roles = userInfo.getRoles();
		if(CollectionUtils.isEmpty(roles)){
			return ResponseUtils.RESPONSE_400();
		}
		for (RoleVO roleVO : roles){
			if(roleVO == null){
				continue;
			}
			Integer roleId = roleVO.getRoleId();
			//如果是总公司用户，不添加company前缀
			if(resultObject.isHeadQuarters()){
				assigneeList.add(roleId.toString());
			}else{
				assigneeList.add(companyId+SPLIT+roleId);
			}
		}
		logger.info("检查用户权限参数：权限列表：{}----流程类型：{}", JSON.toJSONString(assigneeList), processType);
		Response<List<String>> listResponse = workFlowDubboService.checkUserAuth(assigneeList, processType);
		return listResponse;
	}

	private boolean checkParam(String processType, Map<String, Object> queryParam) {
		if (StringUtils.isEmpty(processType)) {
			return false;
		}
		int complate = 0;
		for (Map.Entry<String, Object> entry : queryParam.entrySet()) {
			if ("pageNum".equals(entry.getKey())) {
				complate++;
			}
			if ("pageSize".equals(entry.getKey())) {
				complate++;
			}
			if ("status".equals(entry.getKey())) {
				complate++;
			}
		}
		if (complate != 3) {
			return false;
		}
		return true;
	}

}
