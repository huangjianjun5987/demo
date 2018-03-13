package com.yatang.sc.bpm.dubboservice;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.busi.common.resp.Response;
import com.yatang.sc.bpm.common.PageResult;
import com.yatang.sc.bpm.dto.TaskProcessRecordDto;
import com.yatang.sc.bpm.dto.WorkFlowDto;

/**
 * @描述: 工作流dubbo接口
 * @作者: huangjianjun
 * @创建时间: 2017年11月16日-下午2:31:48 .
 */
public interface WorkFlowDubboService {
		
		/**
		 * @Description: 部署流程定义 
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:04
		 * @param paramByte
		 * @param filename
		 */
		Response<Boolean> saveNewDeploye(byte[] paramByte, String filename);
	
		/**
		 * @Description: 查询部署对象信息，对应表(act_re_deployment)
		 * 				  和流程定义的信息，对应表（act_re_procdef）
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 */
		Response<Map<String,Object>> findDeployAndProcessDefList();
		
	
		/**
		 * @Description: 使用部署对象ID，删除流程定义
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 * @param deploymentId 流程部署ID
		 */
		Response<Boolean> deleteProcessDefinitionByDeploymentId(String deploymentId);
	
		/**
		 * @Description: 启动流程实例
		 * @author huangjianjun
		 * @date 2017年11月24日下午6:44:48
		 * @param id 单号ID
		 * @param type 类型(0:采购单,1:采购退货单,2:商品采购定价单,3:商品销售定价单)
		 * @param branchCompanyId 分公司ID
		 * @param userId 提交人
		 */
		Response<Boolean> saveStartProcess(Long id,Integer type,Integer branchCompanyId,String userId);
	
		/**
		 * @Description: 使用任务ID，获取当前任务节点中对应的Form key中的连接的值
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 * @param taskId 任务ID
		 */
		Response<String> findTaskFormKeyByTaskId(String taskId);
	
		/**
		 * @Description: 二：已知任务ID，查询ProcessDefinitionEntiy对象，
		 * 					从而获取当前任务完成之后的连线名称，并放置到List<String>集合中
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 * @param taskId 任务ID
		 */
		Response<List<String>> findOutComeListByTaskId(String taskId);
	
		/**
		 * @Description: 审核任务
		 * @author huangjianjun
		 * @date 2017年12月5日下午2:42:47
		 * @param WorkFlowDto
		 */
		Response<Boolean> audit(WorkFlowDto bean);
	
		/**
		 * @Description: 获取批注信息，传递的是当前任务ID，获取历史任务ID对应的批注 
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 * @param taskId 任务ID
		 */
		Response<List<Comment>> findCommentByTaskId(String taskId);
	
		/**
		 * @Description: 使用单号ID，查询历史批注信息 
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 * @param taskId 任务ID
		 */
		Response<List<Comment>> findCommentByLeaveBillId(Long id);
	
		/**
		 * @Description: 1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 */
		Response<ProcessDefinition> findProcessDefinitionByTaskId(String taskId);
	
		/**
		 * @Description:二：查看当前活动，获取当期活动对应的坐标x,y,width,height，
		 * 				将4个值存放到Map<String,Object>中 map集合的key：表示坐标x,y,width,height
		 * 				map集合的value：表示坐标对应的值
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 */
		Response<Map<String, Object>> findCoordingByTask(String taskId);
		
		/**
		 * @Description: 使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task> 
		 * @author huangjianjun
		 * @date 2017年11月15日上午8:25:13
		 */
		Response<List<Task>> findTaskListByName(String name);
		
		
		/**
		 * @Description: 查看流程图
		 * @author huangjianjun
		 * @date 2017年11月24日下午6:54:39
		 */
		Response<String> findImageToBase64(String deploymentId,String imageName);

		/**
		 *@描述:查询流程审核记录
		 *@作者:tangqi
		 *@时间:2017/11/28 14:49
		 */
		Response<List<TaskProcessRecordDto>> queryCommentHis(String taskId);

		/**
		 *@描述:查询流程审核记录
		 *@作者:tangqi
		 *@时间:2017/11/28 14:49
		 */
		Response<List<TaskProcessRecordDto>> queryCommentHis(String id, String processType);

		/**
		 *@描述:获取高亮流程图
		 *@作者:tangqi
		 *@时间:2017/11/29 9:07
		 */
		Response<String> queryProcessImage(String taskId) throws IOException;

		/**
		 *@描述:获取高亮流程图
		 *@作者:tangqi
		 *@时间:2017/12/14 11:00
		 */
		Response<String> queryProcessImage(String id, String processType) throws IOException;

		/**
		 *@描述:用户获取流程任务结果集
		 *@作者:tangqi
		 *@时间:2017/12/1 15:31
		 */
		Response<PageResult<Map<String, Object>>> queryProcessMsgList(List<String> assignee, Map<String, Object> queryParam);

		/**
		 *@描述:检查用户有没有获取代办流程的权限
		 *@作者:tangqi
		 *@时间:2017/12/5 10:38
		 */
		Response<List<String>> checkUserAuth(List<String> assigneeList, String processType);

}
