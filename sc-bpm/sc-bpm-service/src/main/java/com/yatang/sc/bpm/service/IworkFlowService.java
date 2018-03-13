package com.yatang.sc.bpm.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.yatang.sc.bpm.domain.TaskProcessRecordPo;
import com.yatang.sc.bpm.domain.WorkFlowPo;

public interface IworkFlowService {
	
	/**
	 * @Description: 部署流程定义 
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:04
	 */
	void saveNewDeploye(ZipInputStream zipInputStream, String filename);

	/**
	 * @Description: 查询部署对象信息，对应表（act_re_deployment）
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	List<Deployment> findDeploymentList();

	/**
	 * @Description: 查询流程定义的信息，对应表（act_re_procdef）
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	List<ProcessDefinition> findProcessDefinitionList();

	/**
	 * @Description: 使用部署对象ID和资源图片名称，获取图片的输入流
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	InputStream findImageInputStream(String deploymentId, String imageName);

	/**
	 * @Description: 使用部署对象ID，删除流程定义
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	void deleteProcessDefinitionByDeploymentId(String deploymentId);

	/**
	 * @Description: 启动流程实例
	 * @author huangjianjun
	 * @date 2017年11月27日下午2:45:41
	 * @param id 单号
	 * @param type 类型
	 * @param branchCompanyId 分公司ID
	 * @param userId 
	 */
	boolean saveStartProcess(Long id,Integer type,Integer branchCompanyId, String userId);

	/**
	 * @Description: 使用任务ID，获取当前任务节点中对应的Form key中的连接的值
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	String findTaskFormKeyByTaskId(String taskId);

	/**
	 * @Description: 二：已知任务ID，查询ProcessDefinitionEntiy对象，
	 * 					从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	List<String> findOutComeListByTaskId(String taskId);

	/**
	 * @Description:完成任务 
	 * @author huangjianjun
	 * @date 2017年11月29日下午6:25:14
	 */
	void saveSubmitTask(WorkFlowPo workflowpo);

	/**
	 * @Description: 获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注 
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	List<Comment> findCommentByTaskId(String taskId);


	/**
	 * @Description: 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	ProcessDefinition findProcessDefinitionByTaskId(String taskId);

	
	/**
	 * @Description: 使用当前assignee查询正在执行的任务表，获取当前任务的集合List<Task> 
	 * @author huangjianjun
	 * @date 2017年11月15日上午8:25:13
	 */
	List<Task> findTaskListByAssignee(String assignee);

	/**
	 *@描述:查询审批记录
	 *@作者:tangqi
	 *@时间:2017/11/28 14:44
	 */
	List<TaskProcessRecordPo> queryCommentHis(String taskId);

	/**
	 *@描述:查询审批记录
	 *@作者:tangqi
	 *@时间:2017/11/28 14:44
	 */
	List<TaskProcessRecordPo> queryCommentHis(String id, String processType);

	/**
	 *@描述:获取高亮流程图
	 *@作者:tangqi
	 *@时间:2017/11/29 9:03
	 */
	InputStream queryProcessImage(String taskId);

	/**
	 *@描述:获取高亮流程图
	 *@作者:tangqi
	 *@时间:2017/12/14 11:02
	 */
	InputStream queryProcessImage(String id, String processType);

	/**
	 *@描述:用户获取流程任务结果集
	 *@作者:tangqi
	 *@时间:2017/12/1 15:31
	 */
	Map<String, Object> queryProcessMsgList(List<String> assignee, Map<String, Object> queryParam);

	/**
	*@描述:检查用户有没有获取代办流程的权限
	*@作者:tangqi
	*@时间:2017/12/5 10:38
	*/
	List<String> checkUserAuth(List<String> assigneeList, String processType);
}
