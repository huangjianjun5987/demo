package com.yatang.sc.bpm.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.bpm.common.PageResult;
import com.yatang.sc.bpm.domain.TaskProcessRecordPo;
import com.yatang.sc.bpm.domain.WorkFlowPo;
import com.yatang.sc.bpm.dto.DeploymentDto;
import com.yatang.sc.bpm.dto.ProcessDefinitionDto;
import com.yatang.sc.bpm.dto.TaskProcessRecordDto;
import com.yatang.sc.bpm.dto.WorkFlowDto;
import com.yatang.sc.bpm.dubboservice.WorkFlowDubboService;
import com.yatang.sc.bpm.service.IworkFlowService;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.common.utils.JSONUtils;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;
/**
 * @描述: 流程引擎服务类
 * @作者: huangjianjun
 * @创建时间: 2017年11月16日-下午3:30:16 .
 */
@Service("workFlowDubboService")
public class WorkFlowDubboServiceImpl implements WorkFlowDubboService {
	private static final Logger log = LoggerFactory.getLogger(WorkFlowDubboServiceImpl.class);
	
	
	@Autowired
	private IworkFlowService workFlowService;

	@Override
	public Response<Boolean> saveNewDeploye(byte[] paramByte, String filename) {
		log.info("parameter" + JSON.toJSONString(filename));
		Response<Boolean> response = new Response<>();
		try {
			InputStream stream = new ByteArrayInputStream(paramByte);
			ZipInputStream zipInputStream = new ZipInputStream(stream);
			workFlowService.saveNewDeploye(zipInputStream, filename);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<Map<String,Object>> findDeployAndProcessDefList() {
		Response<Map<String,Object>> response = new Response<Map<String,Object>>();
		try {
			Map<String,Object> map = new HashMap<>();
			List<Deployment> depList = workFlowService.findDeploymentList();
			List<ProcessDefinition> proDeflist = workFlowService.findProcessDefinitionList();
			List<DeploymentDto> depll = new ArrayList<DeploymentDto>();
			List<ProcessDefinitionDto> proll = new ArrayList<ProcessDefinitionDto>();
			DeploymentDto ddto;
			ProcessDefinitionDto pdto;
			for (Deployment dep : depList) {
				ddto = new DeploymentDto();
				ddto.setId(dep.getId());
				ddto.setName(dep.getName());
				ddto.setDeploymentTime(dep.getDeploymentTime());
				depll.add(ddto);
			}
			for (ProcessDefinition bean : proDeflist) {
				pdto = new ProcessDefinitionDto();
				pdto.setId(bean.getId());
				pdto.setName(bean.getName());
				pdto.setKey(bean.getKey());
				pdto.setVersion(bean.getVersion());
				pdto.setDeploymentId(bean.getDeploymentId());
				pdto.setResourceName(bean.getResourceName());
				pdto.setDiagramResourceName(bean.getDiagramResourceName());
				proll.add(pdto);
			}
			map.put("deps", depll);
			map.put("pros", proll);
			response.setResultObject(map);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	@Override
	public Response<Boolean> deleteProcessDefinitionByDeploymentId(String deploymentId) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			workFlowService.deleteProcessDefinitionByDeploymentId(deploymentId);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<String> findTaskFormKeyByTaskId(String taskId) {
		Response<String> response = new Response<String>();
		try {
			String formKey = workFlowService.findTaskFormKeyByTaskId(taskId);
			response.setResultObject(formKey);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<List<String>> findOutComeListByTaskId(String taskId) {
		Response<List<String>> response = new Response<List<String>>();
		try {
			List<String> nextlineList = workFlowService.findOutComeListByTaskId(taskId);
			response.setResultObject(nextlineList);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<List<Comment>> findCommentByTaskId(String taskId) {
		Response<List<Comment>> response = new Response<>();
		try{
			List<Comment> commentByTaskId = workFlowService.findCommentByTaskId(taskId);
			response.setSuccess(true);
			response.setResultObject(commentByTaskId);
			response.setCode("200");
		}catch (Exception e){
			response.setCode("500");
			response.setSuccess(false);
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}



	@Override
	public Response<List<Comment>> findCommentByLeaveBillId(Long id) {
		return null;
	}



	@Override
	public Response<ProcessDefinition> findProcessDefinitionByTaskId(String taskId) {
		return null;
	}



	@Override
	public Response<Map<String, Object>> findCoordingByTask(String taskId) {
		return null;
	}



	@Override
	public Response<List<Task>> findTaskListByName(String name) {
		return null;
	}


	/**
	 * 启动流程
	 * @see com.yatang.sc.bpm.dubboservice.WorkFlowDubboService(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Response<Boolean> saveStartProcess(Long id,Integer type,Integer branchCompanyId,String userId) {
		Response<Boolean> response = new Response<>();
		try {
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(workFlowService.saveStartProcess(id,type,branchCompanyId,userId));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}


	/**
	 * 查看流程图
	 * @see com.yatang.sc.bpm.dubboservice.WorkFlowDubboService#findImageToBase64(java.lang.String, java.lang.String)
	 */
	@Override
	public Response<String> findImageToBase64(String deploymentId, String imageName) {
		log.info("parameter" + JSON.toJSONString(deploymentId));
		log.info("parameter" + JSON.toJSONString(imageName));
		Response<String> response = new Response<>();
		try {
			InputStream stream = workFlowService.findImageInputStream(deploymentId, imageName);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(org.apache.commons.codec.binary.Base64.encodeBase64String(IOUtils.toByteArray(stream)));
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@Override
	public Response<List<TaskProcessRecordDto>> queryCommentHis(String taskId) {
		log.info("WorkFlowDubboServiceImpl-->queryCommentHis-->参数：{}", JSONUtils.toJson(taskId));
		Response<List<TaskProcessRecordDto>> response = new Response<>();
		try{
			List<TaskProcessRecordPo> taskProcessRecordPos = workFlowService.queryCommentHis(taskId);
			response.setSuccess(true);
			response.setCode("200");
			response.setResultObject(BeanConvertUtils.convertList(taskProcessRecordPos, TaskProcessRecordDto.class));
		}catch (Exception e){
			log.error("WorkFlowDubboServiceImpl-->queryCommentHis-->获取审核记录失败:{}", ExceptionUtils.getMessage(e));
			response.setSuccess(false);
			response.setCode("500");
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	/**
	 * 查看流程进度
	 * @see com.yatang.sc.bpm.dubboservice.WorkFlowDubboService#queryProcessImage(java.lang.String)
	 */
	@Override
	public Response<String> queryProcessImage(String taskId) throws IOException {
		log.info("WorkFlowDubboServiceImpl-->queryProcessImage-->参数：{}", taskId);
		Response<String> response = new Response<>();
		try{
			InputStream inputStream = workFlowService.queryProcessImage(taskId);
			byte[] bytes = IOUtils.toByteArray(inputStream);
			String string = Base64.encodeBase64String(bytes);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setResultObject(string);
		}catch (Exception e){
			log.error("WorkFlowDubboServiceImpl-->queryProcessImage-->获取流程图失败:{}", ExceptionUtils.getMessage(e));
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	/**
	 * 查询待办任务列表
	 * @see com.yatang.sc.bpm.dubboservice.WorkFlowDubboService#(java.lang.String, java.util.Map)
	 */
	@Override
	public Response<PageResult<Map<String, Object>>> queryProcessMsgList(List<String> assignee, Map<String, Object> queryParam) {
		log.info("WorkFlowDubboServiceImpl-->queryProcessMsgList-->参数：{}", JSON.toJSONString(assignee) +"+"+ JSON.toJSONString(queryParam));
		Response<PageResult<Map<String, Object>>> response = new Response<>();
		PageResult<Map<String, Object>> pageResult = new PageResult<>();
		List<Map<String, Object>> list = new ArrayList<>();
		try{
			pageResult.setPageNum(new Integer(queryParam.get("pageNum").toString()));
			pageResult.setPageSize(new Integer(queryParam.get("pageSize").toString()));
			Map<String, Object> objectMap = workFlowService.queryProcessMsgList(assignee, queryParam);
			for(Map.Entry<String, Object> entry : objectMap.entrySet()){
				String key = entry.getKey();
				if(key.equals("total")){
					pageResult.setTotal(new Long(entry.getValue().toString()));
					continue;
				}
				if(key.equals("list")){
					list = (List<Map<String, Object>>) entry.getValue();
				}
			}

			pageResult.setData(list);
			response.setResultObject(pageResult);
			response.setCode("200");
			response.setSuccess(true);
		}catch (Exception e){
			log.error("WorkFlowDubboServiceImpl-->queryProcessMsgList-->获取代办流程失败：", e);
			response.setErrorMessage(e.getMessage());
			response.setSuccess(false);
			response.setCode("500");
		}
		return response;
	}


	/**
	 * 审核任务
	 * @see com.yatang.sc.bpm.dubboservice.WorkFlowDubboService#audit(com.yatang.sc.bpm.dto.WorkFlowDto)
	 */
	@Override
	public Response<Boolean> audit(WorkFlowDto bean) {
		log.info("WorkFlowDubboServiceImpl-->audit-->参数：{}", JSON.toJSONString(bean));
		Response<Boolean> response = new Response<>();
		try {
			WorkFlowPo  workflowPo = BeanConvertUtils.convert(bean, WorkFlowPo.class);
			workFlowService.saveSubmitTask(workflowPo);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setSuccess(true);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}

	@Override
	public Response<List<String>> checkUserAuth(List<String> assigneeList, String processType) {
		log.info("WorkFlowDubboServiceImpl-->checkUserAuth-->参数：{}", JSON.toJSONString(assigneeList) +"+"+ JSON.toJSONString(processType));
		Response<List<String>> response = new Response<>();
		try{
			List<String> assignList = workFlowService.checkUserAuth(assigneeList, processType);
			if (CollectionUtils.isEmpty(assignList)){
				response.setSuccess(false);
				response.setCode("200");
				response.setErrorMessage("没有流程");
				return response;
			}
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setResultObject(assignList);
		}catch (Exception e){
			log.error("WorkFlowDubboServiceImpl-->checkUserAuth-->监测权限报错：{}", ExceptionUtils.getMessage(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	@Override
	public Response<String> queryProcessImage(String id, String processType) throws IOException {
		log.info("WorkFlowDubboServiceImpl-->queryProcessImage2-->参数：{}", id+":"+processType);
		Response<String> response = new Response<>();
		try{
			InputStream inputStream = workFlowService.queryProcessImage(id, processType);
			byte[] bytes = IOUtils.toByteArray(inputStream);
			String string = Base64.encodeBase64String(bytes);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setResultObject(string);
		}catch (Exception e){
			log.error("WorkFlowDubboServiceImpl-->queryProcessImage2-->获取流程图失败:{}", ExceptionUtils.getMessage(e));
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}

	@Override
	public Response<List<TaskProcessRecordDto>> queryCommentHis(String id, String processType) {
		log.info("WorkFlowDubboServiceImpl-->queryCommentHis2-->参数：{}", JSONUtils.toJson(id+":"+processType));
		Response<List<TaskProcessRecordDto>> response = new Response<>();
		try{
			List<TaskProcessRecordPo> taskProcessRecordPos = workFlowService.queryCommentHis(id, processType);
			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setResultObject(BeanConvertUtils.convertList(taskProcessRecordPos, TaskProcessRecordDto.class));
		}catch (Exception e){
			log.error("WorkFlowDubboServiceImpl-->queryCommentHis2-->获取审核记录失败:", e);
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(e.getMessage());
		}
		return response;
	}
}
