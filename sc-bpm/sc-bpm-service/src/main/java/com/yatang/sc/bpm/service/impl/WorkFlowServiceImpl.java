package com.yatang.sc.bpm.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.NativeHistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.google.common.collect.Lists;
import com.yatang.sc.bpm.domain.TaskProcessRecordPo;
import com.yatang.sc.bpm.domain.WorkFlowPo;
import com.yatang.sc.bpm.service.IworkFlowService;
import com.yatang.sc.common.staticvalue.Constants;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderInfoDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseRefundDto;
import com.yatang.sc.facade.dto.prod.ProSellPriceBpmDto;
import com.yatang.sc.facade.dto.prod.ProdPurchasePriceAuditDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.facade.dubboservice.GoodsPriceWriteDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderWriteDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseRefundWriteDubboService;
import com.yatang.sc.facade.dubboservice.ProdPurchaseQueryDubboService;
import com.yatang.sc.facade.dubboservice.ProdPurchaseWriteDubboService;
import com.yatang.xc.mbd.biz.system.dto.UserTypeCompanyDTO;
import com.yatang.xc.mbd.biz.system.dubboservice.CompanyDubboService;

@Service("IworkFlowService")
public class WorkFlowServiceImpl implements IworkFlowService {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
    private ProcessEngine processEngine;
	@Autowired
	private PmPurchaseRefundQueryDubboService refundQueryService;
	@Autowired
	private PmPurchaseRefundWriteDubboService refundWriteDubboService;
	@Autowired
	private PmPurchaseOrderQueryDubboService purchaseQueryDubboService;
	@Autowired
	private PmPurchaseOrderWriteDubboService purchaseWriteDubboService;
	@Autowired
	private CompanyDubboService companyDubboService;
	@Autowired
	private GoodsPriceQueryDubboService goodsPriceQueryDubboService;
	@Autowired
	private GoodsPriceWriteDubboService goodsPriceWriteDubboService;
	@Autowired
	private ProdPurchaseQueryDubboService prodPurchaseQueryDubboService;
	@Autowired
	private ProdPurchaseWriteDubboService prodPurchaseWriteDubboService;
	
	
	@Value("${pro.audit.pm}")
	private  String auditPm;
	@Value("${pro.audit.gm}")
	private  String auditGm;
	@Value("${pro.audit.fm}")
	private  String auditFm;
	@Value("${pro.audit.pd}")
	private  String auditPd;
	
	@Value("${pro.audit.total.tpm}")
	private  String auditTpm;
	@Value("${pro.audit.total.tpd}")
	private  String auditTpd;
	@Value("${pro.audit.total.tpf}")
	private  String auditTpf;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 部署流程定义 */
	@Override
	public void saveNewDeploye(ZipInputStream zipInputStream, String filename) {
		try {
			repositoryService.createDeployment()// 创建部署对象
							  .name(filename)// 添加部署名称
							  .addZipInputStream(zipInputStream)//
							  .deploy();// 完成部署
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/** 查询部署对象信息，对应表(act_re_deployment) */
	@Override
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()// 创建部署对象查询
				.orderByDeploymenTime().asc()//排序
				.list();
		return list;
	}


	/** 查询流程定义的信息，对应表(act_re_procdef) */
	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询
				.orderByProcessDefinitionVersion().asc()//排序
				.list();
		return list;
	}


	/** 使用部署对象ID和资源图片名称，获取图片的输入流 */
	@Override
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}


	/** 使用部署对象ID，删除流程定义 */
	@Override
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}


	/** 使用任务ID，获取当前任务节点中对应的Form key中的连接的值 */
	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		// 获取Form key的值
		String url = formData.getFormKey();
		return url;
	}


	/** 办理任务:已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中 */
	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		// 返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		Task task = findTaskById(taskId);
		// 2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = findProcessDefEntityByDefId(processDefinitionId);
		// 使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = findProcessInsByInsId(processInstanceId);
		// 获取当前活动的id
		String activityId = pi.getActivityId();
		// 4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		// 5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if (pvmList != null && pvmList.size() > 0) {
			for (PvmTransition pvm : pvmList) {
				String name = (String) pvm.getProperty("name");
				if (StringUtils.isNotBlank(name)) {
					list.add(name);
				}
			}
		}
		return list;
	}


	/**
	 * @Description: 根据流程实例ID获取流程实例
	 * @author huangjianjun
	 * @date 2017年12月7日下午5:35:37
	 * @param processInstanceId
	 */
	private ProcessInstance findProcessInsByInsId(String processInstanceId) {
		logger.info("查询流程实例param:{}", processInstanceId);
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		return pi;
	}


	/**
	 * @Description: 根据流程实例ID获取流程实例对象
	 * @author huangjianjun
	 * @date 2017年12月7日下午3:58:42
	 * @param processDefinitionId
	 */
	private ProcessDefinitionEntity findProcessDefEntityByDefId(String processDefinitionId) {
		logger.info("查询流程定义param:{}", processDefinitionId);
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
		return processDefinitionEntity;
	}


	/** 获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注 */
	@Override
	public List<Comment> findCommentByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		Task task = findTaskById(taskId);
		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		 //使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
		 List<HistoricTaskInstance> htiList = historyService.createHistoricTaskInstanceQuery()//历史任务表查询
		 .processInstanceId(processInstanceId)//使用流程实例ID查询
		 .list();
		 //遍历集合，获取每个任务ID
		 if(htiList!=null && htiList.size()>0){
			 for(HistoricTaskInstance hti:htiList){
			 //任务ID
			 String htaskId = hti.getId();
			 //获取批注信息
			 List<Comment> taskList = taskService.getTaskComments(htaskId);//对用历史完成后的任务ID
			 list.addAll(taskList);
			 }
		 }
		list = taskService.getProcessInstanceComments(processInstanceId);
		return list;
	}



	/** 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象 */
	@Override
	public ProcessDefinition findProcessDefinitionByTaskId(String taskId) {
		Task task = findTaskById(taskId);
		// 查询流程定义的对象
		ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询对象，对应表act_re_procdef
				.processDefinitionId(task.getProcessDefinitionId())// 使用流程定义ID查询
				.singleResult();
		return pd;
	}

    /** 
     * 根据任务ID获取流程定义 
     * @author huangjianjun
     * @date 2017年12月7日下午5:13:49
     * @param taskId 任务ID 
     * @return ProcessDefinitionEntity
     */  
    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId){
    	logger.info("查询流程定义实例任务ID:{}", taskId);
        // 取得流程定义  
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
                								.getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());  
        return processDefinition;  
    }  

	
    /**
     * @Description: 根据任务ID和节点ID获取活动节点
     * @author huangjianjun
     * @date 2017年12月7日下午3:13:49
     * @param taskId 任务ID 
     * @param activityId 活动节点ID
     *            		   如果为null或""，则默认查询当前活动节点
     *            		   如果为"end"，则查询结束节点
     */
	private ActivityImpl findActivitiImpl(String taskId,String activityId){
        // 取得流程定义  ProcessDefinition
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);
        
        // 获取当前活动节点ID
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }
        // 根据流程定义，获取该流程实例的结束节点  
        if (activityId.toUpperCase().equals(Constants.END)) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                List<PvmTransition> pvmTransitionList = activityImpl
                        .getOutgoingTransitions();
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }
        // 根据节点ID，获取对应的活动节点
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition)
                .findActivity(activityId);
        return activityImpl;
    }  



	/**
	 * 使用当前assignee查询正在执行的任务表，获取当前任务的集合List<Task>
	 * @see com.yatang.sc.bpm.service.IworkFlowService#
	 */
	@Override
	public List<Task> findTaskListByAssignee(String assignee) {
		List<Task> list = taskService.createTaskQuery()
				.taskAssignee(assignee)
				.includeProcessVariables()
				.processVariableValueEquals("refundNo", "1709120001")
				.listPage(0,2);
		return list;
	}

	/**
	 * 更新单据状态，启动流程实例，让启动的流程实例关联业务 
	 * @see com.yatang.sc.bpm.service.IworkFlowService
	 */
	@Override
	public boolean saveStartProcess(Long id,Integer type,Integer branchCompanyId, String userId) {
		logger.info("启动流程实例,单据ID:{},单据类型:{},提交单据分公司ID:{},提交人:{}", id,type,branchCompanyId,userId);
	
		//从Session中获取当前任务的办理人，使用流程变量设置下一个任务的办理人 PM是流程变量的名称， 获取的办理人是流程变量的值
		Map<String, Object> variables = new HashMap<String, Object>();
		
		//使用当前对象获取到流程定义的key(对象的名称就是流程定义的key)
		String key = matchProcess(id, type, branchCompanyId, userId,variables);
		
		// (1)使用流程变量设置字符串(格式：pro.cg.id的形式)，通过设置，让启动的流程(流程实例)关联业务
		// (2)使用正在执行对象表中的一个字段BUSINESS_KEY(Activiti提供的一个字段)，让启动的流程(流程实例)关联业务
		String objId = key + "." + id;
		
		//使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		if(org.apache.commons.lang.StringUtils.isNotBlank(key)){
			runtimeService.startProcessInstanceByKey(key, objId, variables);
			logger.info("启动流程实例成功!,流程参数variables:{}", JSON.toJSONString(variables));
			return true;
		}else{
			logger.info("启动流程实例失败!,流程业务数据为空!", JSON.toJSONString(variables));
			return false;
		}
	}


	/**
	 * @Description: 匹配流程
	 * @author huangjianjun
	 * @date 2017年12月20日下午4:45:33
	 */
	private String matchProcess(Long id, Integer type, Integer branchCompanyId,
										String userId, Map<String, Object> variables) {
		boolean isTotalCompany = judgmentUserType(userId);
		String key ="";
		//不同的单据走不同的流程(启动以key区分启动哪个流程)
		switch (type) {
		case 0:
			if(isTotalCompany){
				key = Constants.PROCESS_T_CG;
				variables.put("TPM", auditTpm);
				variables.put("TPD", auditTpd);
			}else{
				key = Constants.PROCESS_CG;
				//设置首节点办理人
				variables.put("PM", branchCompanyId+"_"+auditPm);
				variables.put("GM", branchCompanyId+"_"+auditGm);
			}
			logger.info("启动流程实例key:{}", key);
			variables.put(Constants.PROCESS_TYPE, Constants.CG);
			Response<PmPurchaseOrderInfoDto> cgRes = purchaseQueryDubboService.getPurchaseOrderInfoById(id);
			//如果调用单据查询服务失败或数据为空不启动流程
			if(!cgRes.isSuccess() || cgRes.getResultObject() == null){
				return "";
			}
			PmPurchaseOrderInfoDto purchaseOrder = cgRes.getResultObject();
			variables.put("purchaseNo", purchaseOrder.getPurchaseOrderNo());
			variables.put("businessMode", purchaseOrder.getBusinessMode());
			variables.put("adrType", purchaseOrder.getAdrType());
			variables.put("purchaseType", purchaseOrder.getPurchaseOrderType());
			variables.put("adrTypeName", purchaseOrder.getAdrTypeName());
			variables.put("spName", purchaseOrder.getSpName());
			variables.put("spAdrName", purchaseOrder.getSpAdrName());
			variables.put("category", purchaseOrder.getSecondCategoryName());
			variables.put("purchaseMoney", purchaseOrder.getTotalAmount()!=null?purchaseOrder.getTotalAmount().toString():0);
			variables.put("createUser", purchaseOrder.getCreateUserId());
			variables.put("createTime", purchaseOrder.getCreateTime());
			break;
		case 1:
			if(isTotalCompany){
				key = Constants.PROCESS_T_CGTH;
				variables.put("TPM", auditTpm);
				variables.put("TPD", auditTpd);
				variables.put("TPF", auditTpf);
			}else{
				key = Constants.PROCESS_CGTH;
				//设置每个节点办理人
				variables.put("PM", branchCompanyId+"_"+auditPm);
				variables.put("GM", branchCompanyId+"_"+auditGm);
				variables.put("FM", branchCompanyId+"_"+auditFm);
			}
			logger.info("启动流程实例key:{}", key);
			variables.put(Constants.PROCESS_TYPE, Constants.CGTH);
			Response<PmPurchaseRefundDto> cgthRes = refundQueryService.queryRefundDetailById(id);
			if(!cgthRes.isSuccess() || cgthRes.getResultObject() == null){
				return "";
			}
			PmPurchaseRefundDto refundOrder = cgthRes.getResultObject();
			variables.put("refundNo", refundOrder.getPurchaseRefundNo());
			variables.put("adrType", refundOrder.getAdrType());
			variables.put("refundAdrName", refundOrder.getRefundAdrName());
			variables.put("spName", refundOrder.getSpName());
			variables.put("spAdrName", refundOrder.getSpAdrName());
			variables.put("refundAmount", refundOrder.getTotalRefundAmount());
			variables.put("refundMoney", refundOrder.getTotalRefundMoney()!=null?refundOrder.getTotalRefundMoney().toString():0);
			variables.put("refundCost", refundOrder.getTotalRefundCost()!=null?refundOrder.getTotalRefundCost().toString():0);
			variables.put("createUser", refundOrder.getCreateUserId());
			variables.put("createTime", refundOrder.getCreateTime());
			break;
		case 2:
			key = Constants.PROCESS_SP;
			logger.info("启动流程实例key:{}", key);
			variables.put(Constants.PROCESS_TYPE, Constants.SPCG);
			Response<ProdPurchasePriceAuditDto> spcgRes = prodPurchaseQueryDubboService.getProdPurchasePriceAuditDtoById(id);
			if(!spcgRes.isSuccess() || spcgRes.getResultObject() == null){
				return "";
			}
			ProdPurchasePriceAuditDto prodPrice = spcgRes.getResultObject();
			variables.put("PM", branchCompanyId+"_"+auditPm);
			variables.put("PD", branchCompanyId+"_"+auditPd);
			variables.put("spNo", prodPrice.getSpNo());
			variables.put("spName", prodPrice.getSpName());
			variables.put("spAdrNo", prodPrice.getSpAdrCode());
			variables.put("spAdrName", prodPrice.getSpAdrName());
			variables.put("productNo", prodPrice.getProductCode());
			variables.put("productName", prodPrice.getProductName());
			variables.put("modifyTime", prodPrice.getModifyTime()==null?prodPrice.getCreateTime():prodPrice.getModifyTime());
			break;
		case 3:
			key = Constants.PROCESS_SP;
			logger.info("启动流程实例key:{}", key);
			variables.put(Constants.PROCESS_TYPE, Constants.SPXS);
			Response<ProSellPriceBpmDto> spxsRes = goodsPriceQueryDubboService.getProdSellPriceInfo(id);
			if(!spxsRes.isSuccess() || spxsRes.getResultObject() == null){
				return "";
			}
			ProSellPriceBpmDto proSell = spxsRes.getResultObject();
			variables.put("PM", branchCompanyId+"_"+auditPm);
			variables.put("PD", branchCompanyId+"_"+auditPd);
			variables.put("companyId", proSell.getBranchCompanyId());
			variables.put("companyName", proSell.getBranchCompanyName());
			variables.put("productNo", proSell.getProductCode());
			variables.put("productName", proSell.getProductName());
			variables.put("modifyTime", proSell.getModifyTime());
			break;
		}
		return key;
	}

	/**
	 * @Description: 判定用户类型
	 * @author huangjianjun
	 * @date 2017年12月21日上午11:04:06
	 */
	private boolean judgmentUserType(String userId) {
		//判断子公司总公司角色
		Response<UserTypeCompanyDTO> comRes = companyDubboService.findUserTypeCompanyByUserId(Integer.parseInt(userId));
		UserTypeCompanyDTO userTypeBean = new UserTypeCompanyDTO();
		boolean isTotalCompany = false;
		if(comRes.isSuccess()){
			userTypeBean = comRes.getResultObject();
			isTotalCompany = userTypeBean.isHeadQuarters();
		}
		return isTotalCompany;
	}

	/**
	 * 办理任务
	 * @see com.yatang.sc.bpm.service.IworkFlowService#saveSubmitTask(com.yatang.sc.bpm.domain.WorkFlowPo)
	 */
	@Override
	public void saveSubmitTask(WorkFlowPo workflowPo) {
		logger.info("办理任务param:{}", JSON.toJSONString(workflowPo));
		// 获取任务ID
		String taskId = workflowPo.getTaskId();
		// 获取审批结果
		String outcome = workflowPo.getOutcome();
		// 批注信息
		String message = workflowPo.getComment();

		// 使用任务ID，查询任务对象，获取流程流程实例ID
		Task task = findTaskById(taskId);
		// 获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 设置批注人,添加批注信息
		Authentication.setAuthenticatedUserId(task.getAssignee());
		taskService.addComment(taskId, processInstanceId,outcome,message==null?"":message);
		logger.info("为流程实例:{},添加批注信息成功", processInstanceId);
		if (outcome != null) {
			if(outcome.equals(Constants.PASS)){
				//使用任务ID，完成当前人的个人任务
				commitProcess(taskId, null, null);
				logger.info("办理任务:{}成功！",taskId);
			}else{
				//审批不通过终止流程,并把单据设置为制单状态
				logger.info("审批拒绝,终止流程操作");
				endProcess(taskId);
			}
		}
		
		//查询下一个任务实例对象
//		Task nextTask = taskService.createTaskQuery()
//				.taskDefinitionKey(getNextNode(processInstanceId))
//				.processInstanceId(processInstanceId)
//				.singleResult();
		
		ProcessInstance pi = findProcessInsByInsId(processInstanceId);
		//流程结束了
		if (pi == null) {
			//查询历史流程实例
			HistoricProcessInstance hisProins = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			String key = hisProins.getBusinessKey();
			String id = key.substring(key.lastIndexOf(".") + 1, key.length());
			workflowPo.setId(Long.parseLong(id));
			//正常通过结束流程
			if(outcome.equals(Constants.PASS)){
				//更新单据表的状态(审核中-->审核完成)
				logger.info("流程审批结束调用单据服务更新下发单据信息!param:{}",JSON.toJSONString(workflowPo));
			}else{
				//更新单据表的状态(审核中-->已拒绝)
				logger.info("领导拒绝流程提前结束回滚单据状态!param:{}",JSON.toJSONString(workflowPo));
			}
			updateOrderStatus(workflowPo);
		}
	}
	

	/**
	 * @Description: 更新单据状态
	 * @author huangjianjun
	 * @date 2017年12月11日下午2:22:54
	 * @param
	 */
	private void updateOrderStatus(WorkFlowPo workflowPo) {
		switch (workflowPo.getType()) {
		case 0:
			purchaseWriteDubboService.auditCallbackPurchaseOrderInfo(workflowPo.getId(), workflowPo.getUserId(), 
							workflowPo.getUserName(), workflowPo.getOutcome().equals(Constants.PASS)?true:false);
			logger.info("任务结束更新采购单状态成功!");
			break;
		case 1:
			refundWriteDubboService.workFlowCallBack(workflowPo.getId(), workflowPo.getUserId(), 
							workflowPo.getUserName(), workflowPo.getOutcome().equals(Constants.PASS)?true:false);
			logger.info("任务结束更新采购单状态成功!");
			break;
		case 2:
			prodPurchaseWriteDubboService.updateProdPurchaseAuditStatus(workflowPo.getId(),
							workflowPo.getOutcome().equals(Constants.PASS)?true:false, workflowPo.getUserId());
			logger.info("任务结束更新商品采购定价单状态成功!");
			break;
		case 3:
			goodsPriceWriteDubboService.updateAuditSellPrice(workflowPo.getId(), 
							workflowPo.getUserId(), workflowPo.getOutcome().equals(Constants.PASS)?true:false);
			logger.info("任务结束更新商品销售定价单状态成功!");
			break;
		}
	}

	/**
	 * @Description: 根据任务ID查询单个任务
	 * @author huangjianjun
	 * @date 2017年12月7日下午3:12:01
	 */
	private Task findTaskById(String taskId) {
		logger.info("查询任务,任务ID:{}",taskId);
		Task task = taskService.createTaskQuery()
				.taskId(taskId)
				.singleResult();
		return task;
	}
	
	
	/**
	 * @Description: 获取当前流程的下一个节点
	 * @author huangjianjun
	 * @date 2017年12月5日下午5:45:50
	 */
    public String getNextNode(String procInstanceId){
        // 1、首先是根据流程ID获取当前任务：
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(procInstanceId).list();
        String nextId = "";
        for (Task task : tasks) {
            // 2、根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
            String excId = task.getExecutionId();
            ExecutionEntity execution = (ExecutionEntity) runtimeService.createExecutionQuery().executionId(excId)
                    .singleResult();
            nextId = execution.getActivityId();
            logger.info("下个任务节点ID:{}", nextId);
        }
        return nextId;
    }
    
    
    
    /**
     * @Description: 中止流程(审批不通过终止流程) 
     * @author huangjianjun
     * @date 2017年12月7日下午2:14:47
     * @param taskId 
     */
    public void endProcess(String taskId) {  
        ActivityImpl endActivity = findActivitiImpl(taskId,Constants.END);  
        commitProcess(taskId, null, endActivity.getId());  
    }
    
    /** 
     * @Description: 提交流程  
     * @author huangjianjun
     * @date 2017年12月7日下午2:39:58
     * @param taskId 
     *            当前任务ID 
     * @param variables 
     *            流程变量 
     * @param activityId 
     *            流程转向执行任务节点ID<br> 
     *            此参数为空，默认为提交操作 
     */  
    private void commitProcess(String taskId, Map<String, Object> variables,String activityId) {  
        if (variables == null) {  
            variables = new HashMap<String, Object>();  
        }  
        // 跳转节点为空，默认提交操作  
        if (StringUtils.isEmpty(activityId)) {  
            taskService.complete(taskId, variables);  
        } else {// 流程转向操作  
            turnTransition(taskId, activityId, variables);  
        }  
    } 
    
    

    /**
     * @Description: 流程转向操作  
     * @author huangjianjun
     * @date 2017年12月7日下午2:39:58
     * @param taskId 当前任务ID 
     * @param activityId 目标节点任务ID 
     * @param variables 流程变量 
     */
    private void turnTransition(String taskId, String activityId, Map<String, Object> variables){  
        // 当前节点  
        ActivityImpl currActivity = findActivitiImpl(taskId,null);  
        // 清空当前流向  
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);  
  
        // 创建新流向  
        TransitionImpl newTransition = currActivity.createOutgoingTransition();  
        // 目标节点  
        ActivityImpl pointActivity = findActivitiImpl(taskId,activityId);  
        // 设置新流向的目标节点  
        newTransition.setDestination(pointActivity);  
        logger.info("执行流程转向操作,任务ID:{},参数:{}",taskId,variables);
        // 执行转向任务  
        taskService.complete(taskId, variables);  
        logger.info("转向成功!删除目标节点新流入");
        // 删除目标节点新流入  
        pointActivity.getIncomingTransitions().remove(newTransition);  
  
        // 还原以前流向  
        restoreTransition(currActivity, oriPvmTransitionList);  
    }  
    
   
    /**
     * @Description:  清空指定活动节点流向 
     * @author huangjianjun
     * @date 2017年12月7日下午2:38:53
     * @param activityImpl 
     * @return 节点流向集合 
     */
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {  
        // 存储当前节点所有流向临时变量  
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();  
        // 获取当前节点所有流向，存储到临时变量，然后清空  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        for (PvmTransition pvmTransition : pvmTransitionList) {  
            oriPvmTransitionList.add(pvmTransition);  
        }  
        pvmTransitionList.clear();  
  
        return oriPvmTransitionList;  
    }
    
    
    /** 
     * @Description:还原指定活动节点流向 
     * @author huangjianjun
     * @date 2017年12月7日下午2:38:53
     * @param activityImpl 
     *            活动节点 
     * @param oriPvmTransitionList 
     *            原有节点流向集合 
     */  
    private void restoreTransition(ActivityImpl activityImpl,  
            List<PvmTransition> oriPvmTransitionList) {  
        // 清空现有流向  
        List<PvmTransition> pvmTransitionList = activityImpl  
                .getOutgoingTransitions();  
        pvmTransitionList.clear();  
        // 还原以前流向  
        for (PvmTransition pvmTransition : oriPvmTransitionList) {  
            pvmTransitionList.add(pvmTransition);  
        }  
    }  
    

    /**
     * 查询审批记录
     * @see com.yatang.sc.bpm.service.IworkFlowService#queryCommentHis(java.lang.String)
     */
	@Override
	public List<TaskProcessRecordPo> queryCommentHis(String taskId) {
		logger.info("WorkFlowServiceImpl-->queryCommentHis-->参数：{}", JSON.toJSONString(taskId));
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		if(historicTaskInstance == null){
			throw new RuntimeException("数据库中不存在此任务");
		}
		String processInstanceId = historicTaskInstance.getProcessInstanceId();
		List<HistoricActivityInstance> hisList = getHisActivityInsList(processInstanceId);
		List<TaskProcessRecordPo> records = new ArrayList<>();
        if(CollectionUtils.isEmpty(hisList)){
            return records;
        }
		List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
        for(Comment comment : comments){
			HistoricTaskInstance task = historyService.createHistoricTaskInstanceQuery().taskId(comment.getTaskId()).singleResult();
			TaskProcessRecordPo po = new TaskProcessRecordPo();
			po.setId(comment.getId());
			po.setContent(comment.getFullMessage());
			po.setHandler(task.getAssignee());
			po.setHandlerDate(task.getEndTime());
			if("pass".equals(comment.getType())){
				po.setPass(true);
			}else{
				po.setPass(false);
			}
			records.add(po);
		}
        logger.info("审批记录列表：{}", JSON.toJSONString(records));
        return records;
	}

	/**
	 * 判断当前任务是否通过
	 * @param taskId
	 * @return
	 */
	private boolean isPass(String taskId, String processInstanceId){
		try{
			//通过taskId获取下一个活动节点
			String activityId = findActivityIdByTaskId(taskId);
			logger.info("当前任务对应的活动节点：{}", activityId);
			//流程图中，找到历史task下一个任务节点
			List<String> nextNode = findNextNode(activityId, processInstanceId);
			logger.info("当前任务的下一个目标节点列表：{}", JSON.toJSONString(nextNode.toString()));
			//高亮列表中，找到历史task下一个任务节点
			String nextHightNightNode = findNextHightNightNode(activityId, processInstanceId);
			logger.info("当前任务的下一个高亮节点：{}", nextHightNightNode);
			//对比两个task的下一个节点，相同为true，不同为false
			if(nextNode.contains(nextHightNightNode)){
				return true;
			}
			return false;
		}catch (Exception e){
			logger.debug("判断当前任务审核是否通过出错：{}", e.getMessage());
			return true;
		}

	}

	private String findActivityIdByTaskId(String taskId){
		HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();

		HistoricActivityInstance activityInstance = historyService.
				createHistoricActivityInstanceQuery().taskAssignee(taskInstance.getAssignee()).singleResult();
		return activityInstance.getActivityId();
	}

	private List<String> findNextNode(String activityId, String processInstanceId){
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		String activityTarget = "";
		int targetRefNum = 0;
		List<String> targetActivity = new ArrayList<>();
		Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
		List<FlowElement> elements = Lists.newArrayList(flowElements);
		for(int i=0; i<elements.size(); i++){
			FlowElement Flow = elements.get(i);
			if(!(Flow instanceof SequenceFlow)){
				continue;
			}
			SequenceFlow flow = (SequenceFlow)Flow;
			//节点对应的线
			if(!(flow.getSourceRef().equals(activityId))){
				continue;
			}
			//线的指向
			activityTarget = flow.getTargetRef();
			break;
		}
		if(StringUtils.isEmpty(activityTarget)){
			return targetActivity;
		}
		//判断activityTarget是否有分支
		for(int i=0; i<elements.size(); i++){
			FlowElement Flow = elements.get(i);
			if(!(Flow instanceof SequenceFlow)){
				continue;
			}
			SequenceFlow flow = (SequenceFlow)Flow;
			//如果有线的起始位activityTarget，说明activityTarget是网关
			if(!(flow.getSourceRef().equals(activityTarget))){
				continue;
			}
			targetActivity.add(flow.getTargetRef());
		}
		//如果targetActivity为空，表示activityTarget不是网关
		if(CollectionUtils.isEmpty(targetActivity)){
			targetActivity.add(activityTarget);
		}
		return targetActivity;
	}

	private String findNextHightNightNode(String activityId, String processInstanceId){
		String hightNightNode = "";
		List<HistoricActivityInstance> highLightedActivitList = historyService.
				createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
		for(int i=0; i<highLightedActivitList.size(); i++){
			if(highLightedActivitList.get(i).getActivityId().equals(activityId)){
				hightNightNode = highLightedActivitList.get(i+1).getActivityId();
			}
		}
		return hightNightNode;
	}

    private List<HistoricActivityInstance> getHisActivityInsList(String processInstanceId) {
        List<HistoricActivityInstance> userTask = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityType(Constants.USER_TASK)
                .list();
        return userTask;
    }

    @Override
    public InputStream queryProcessImage(String taskId) {
		logger.info("WorkFlowServiceImpl-->queryProcessImage-->参数：{}", taskId);
	    //历史流程实例
		HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
		String processInstanceId = historicTaskInstance.getProcessInstanceId();
		String processDefinitionId = historicTaskInstance.getProcessDefinitionId();

		//流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        logger.info("流程图对象：{}", JSON.toJSONString(bpmnModel));
        //获取流程引擎配置
        ProcessEngineConfiguration engineConfig = processEngine.getProcessEngineConfiguration();

		engineConfig.setXmlEncoding("UTF-8");
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) engineConfig);
        
        //图形生成器
        ProcessDiagramGenerator diagramGenerator = engineConfig.getProcessDiagramGenerator();
        //流程定义实体类
        ProcessDefinitionEntity definitionEntity = findProcessDefEntityByDefId(processDefinitionId);
        //为获取路径做准备
        List<HistoricActivityInstance> highLightedActivitList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //存放高亮Id
        List<String> highLightedActivitis = new ArrayList<String>();
        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity,highLightedActivitList);
        for(HistoricActivityInstance tempActivity : highLightedActivitList){
        	logger.info("任务节点对象：{}", JSON.toJSONString(tempActivity));
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
        InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "SimSun", "SimSun", "SimSun", null, 0.0);
        return imageStream;
    }

    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
            List<HistoricActivityInstance> historicActivityInstances) {

        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            ActivityImpl activityImpl = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i)
                            .getActivityId());// 得到节点定义的详细信息
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
            ActivityImpl sameActivityImpl1 = processDefinitionEntity
                    .findActivity(historicActivityInstances.get(i + 1)
                            .getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances
                        .get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances
                        .get(j + 1);// 后续第二个节点
                if (activityImpl1.getStartTime().equals(
                        activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl
                    .getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
                        .getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

	@Override
	public Map<String, Object> queryProcessMsgList(List<String> assignees, Map<String, Object> queryParam) {
		logger.info("WorkFlowServiceImpl-->queryProcessMsgList-->参数：{}", JSON.toJSONString(assignees) + "+" + JSON.toJSONString(queryParam));
		Map<String, Object> map = new HashMap<>();//存放返回数据
		List<Map<String, Object>> queryList = new ArrayList<>();
		//排除参数 ： pageNum, pageSize, status, currentAssignee,
		int pageNum = new Integer(queryParam.get("pageNum").toString());
		int  status = new Integer(queryParam.get("status").toString());
		int pageSize = new Integer(queryParam.get("pageSize").toString());
		//获取参数查询条件key
		Set<String> keySet = queryParam.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()){
			String next = iterator.next();
			if(StringUtils.equals(next, "pageNum") || StringUtils.equals(next, "status")
					|| StringUtils.equals(next, "pageSize")){
				iterator.remove();
			}
		}
		Object[] objects = keySet.toArray();
		StringBuilder sqlCount = new StringBuilder("SELECT count(ID_) FROM(");
		StringBuilder sqlList = new StringBuilder("SELECT * FROM(");
		StringBuilder processInstId = new StringBuilder("SELECT * FROM(SELECT av.PROC_INST_ID_,");
		//加入查询条件
		for(int i=0; i<objects.length; i++){
			if(i != objects.length - 1){
				processInstId.append("(SELECT av1.TEXT_ FROM ACT_HI_VARINST av1 WHERE av1.NAME_='"+objects[i]+"' AND av1.PROC_INST_ID_=av.PROC_INST_ID_) AS " + objects[i] + ",");
			}else{
				processInstId.append("(SELECT av1.TEXT_ FROM ACT_HI_VARINST av1 WHERE av1.NAME_='"+objects[i]+"' AND av1.PROC_INST_ID_=av.PROC_INST_ID_) AS " + objects[i]);
			}
		}
		processInstId.append(" FROM ACT_HI_VARINST av GROUP BY av.PROC_INST_ID_) temp where ");
		//加入查询条件值
		for(int i=0; i<objects.length; i++){
			if(i != objects.length - 1){
				processInstId.append(objects[i]+"='"+queryParam.get(objects[i]) + "' and ");
			}else{
				processInstId.append(objects[i]+"='"+queryParam.get(objects[i])+"'");
			}
		}
		processInstId.append(") temp1 INNER JOIN ACT_HI_TASKINST temp2 ON temp2.PROC_INST_ID_=temp1.PROC_INST_ID_ AND temp2.ASSIGNEE_ in ('");
		for(int i=0; i<assignees.size(); i++){
			if(i != assignees.size()-1){
				processInstId.append(assignees.get(i)+"','");
			}else{
				processInstId.append(assignees.get(i)+"')");
			}
		}
		if(status == 0){
			processInstId.append(" and ISNULL(END_TIME_)");
		}else if(status ==1){
			processInstId.append(" and !ISNULL(END_TIME_)");
		}
		logger.info("查询代办列表sql：{}", processInstId);
		long total = historyService.createNativeHistoricTaskInstanceQuery().sql(sqlCount.append(processInstId.toString()).toString()).count();
		logger.info("代办个数：{}", total);

		String sortSql = " ORDER BY temp2.START_TIME_ DESC";

		List<HistoricTaskInstance> historicTaskInstances = historyService.createNativeHistoricTaskInstanceQuery().
				sql(sqlList.append(processInstId).append(sortSql).toString()).listPage((pageNum - 1) * pageSize, pageSize);
		if(CollectionUtils.isNotEmpty(historicTaskInstances)){
			for(HistoricTaskInstance taskInstance : historicTaskInstances){
				logger.info("一条代办：{}", JSON.toJSONString(taskInstance.toString()));
				Map<String, Object> childMap = assembleData(taskInstance);
				queryList.add(childMap);
			}
		}
		map.put("total", total);
		map.put("list", queryList);
		return map;
	}

	private Map<String, Object> assembleData(HistoricTaskInstance taskInstance){
		Map<String, Object> map = new HashMap<>();
		//放入流程变量
		List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().
				processInstanceId(taskInstance.getProcessInstanceId()).list();
		for(HistoricVariableInstance variable : list){
			map.put(variable.getVariableName(), variable.getValue());
		}
		//任务ID和当前节点
		map.put("taskId", taskInstance.getId());
		map.put("currentNode", taskInstance.getName());
		//放入流程时间
		HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery().
				processInstanceId(taskInstance.getProcessInstanceId()).singleResult();
		map.put("startTime", processInstance.getStartTime());
		map.put("endTime", processInstance.getEndTime());
		//放入业务Id
		String key = processInstance.getBusinessKey();
		String id = key.substring(key.lastIndexOf(".") + 1, key.length());
		map.put("id", id);
		return map;
	}

	@Override
	public List<String> checkUserAuth(List<String> assigneeList, String processType) {
		logger.info("WorkFlowServiceImpl-->checkUserAuth-->参数：{}", JSON.toJSONString(assigneeList) +"+"+ JSON.toJSONString(processType));
		List<String> assignList = new ArrayList<>();
		for(String assignee : assigneeList){
			List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery().
					variableValueEquals(assignee).variableValueEquals("processType", processType).list();
			if(CollectionUtils.isNotEmpty(list)){
				assignList.add(assignee);
			}
		}
		return assignList;
	}

	@Override
	public InputStream queryProcessImage(String id, String processType) {
		String taskByBusi = getTaskByBusi(id, processType);
		if(StringUtils.isEmpty(taskByBusi)){
			return null;
		}
		InputStream inputStream = queryProcessImage(taskByBusi);
		return inputStream;
	}

	private String getTaskByBusi(String id, String processType) {
		List<String> companyType = new ArrayList<>();
		companyType.add("CHILD.");
		companyType.add("TOTAL.");
		List<String> process = new ArrayList<>();
		process.add("PRO.");
		process.add("PRO2.");
		List<String> businessKeys = new ArrayList<>();
		for(String str1 : companyType){
			for(String str2 : process){
				String businessKey = str1+processType+"."+str2+id;
				businessKeys.add(businessKey);
			}
		}
		for(String businessKey : businessKeys){
			List<HistoricProcessInstance> instances = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
			if(CollectionUtils.isEmpty(instances)){
				continue;
			}
			HistoricProcessInstance processInstance = instances.get(instances.size()-1);

			if(processInstance == null){
				continue;
			}
			List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstance.getId()).list();
			if(CollectionUtils.isEmpty(list)){
				continue;
			}
			return list.get(0).getId();
		}
		return null;
	}

	@Override
	public List<TaskProcessRecordPo> queryCommentHis(String id, String processType) {
		logger.info("WorkFlowServiceImpl-->queryCommentHis参数：ID:{}, processType:{}",id, processType);
		String taskByBusi = getTaskByBusi(id, processType);
		logger.info("业务id:{}对应的流程id:{}", id, taskByBusi);
		if(StringUtils.isEmpty(taskByBusi)){
			return null;
		}
		List<TaskProcessRecordPo> taskProcessRecordPos = queryCommentHis(taskByBusi);
		return taskProcessRecordPos;
	}
}