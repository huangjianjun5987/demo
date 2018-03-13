package org.activiti.designer.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext*.xml" })
public class ProcessTestMyProcess {

//	private String filename = "E:/\\DevSoft/\\WorkSpace-sc/\\Activiti/\\MyProcess.bpmn";

//	@Autowired
//	private IworkFlowService workService;
	
//	@Rule
//	public ActivitiRule activitiRule = new ActivitiRule();
//
//	@Test
//	public void startProcess() throws Exception {
//		RepositoryService repositoryService = activitiRule.getRepositoryService();
//		repositoryService.createDeployment().addInputStream("myProcess.bpmn20.xml",
//				new FileInputStream(filename)).deploy();
//		RuntimeService runtimeService = activitiRule.getRuntimeService();
//		Map<String, Object> variableMap = new HashMap<String, Object>();
//		variableMap.put("name", "Activiti");
//		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess", variableMap);
////		assertNotNull(processInstance.getId());
//		System.out.println("id " + processInstance.getId() + " "
//				+ processInstance.getProcessDefinitionId());
//	}
	
	 /**
	  * @Description:使用代码创建工作流需要的23张表 
	  * @author huangjianjun
	  * @date 2017年11月17日下午2:35:34
	  */
    @Test  
    public void createTable(){  
        //流程引擎ProcessEngine对象，所有操作都离不开引擎对象  
        ProcessEngineConfiguration processEngineConfiguration =   
                ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();  
        //三个配置  
        //1.先删除表，再创建表：processEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP="create-drop"  
        //2.不能自动创建表，需要表存在：processEngineConfiguration.DB_SCHEMA_UPDATE_FALSE="false"  
        //3.如果表存在，就自动创建表：processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE="true"  
        processEngineConfiguration.setDatabaseSchema(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);  
        //获取工作流的核心对象，ProcessEngine对象  
        ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();  
        System.out.println("processEngine:"+processEngine+"Create Success!!");  
    }   
    
    
    @Test  
    public void testSelectTaskByAssignee(){  
//    	workService.findTaskListByAssignee("10000_382");
    }   
	
}