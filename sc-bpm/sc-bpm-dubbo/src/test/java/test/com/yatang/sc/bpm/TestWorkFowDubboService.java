package test.com.yatang.sc.bpm;

import com.busi.common.resp.Response;
import com.yatang.sc.bpm.common.PageResult;
import com.yatang.sc.bpm.dto.TaskProcessRecordDto;
import com.yatang.sc.bpm.dubboservice.WorkFlowDubboService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangqi
 * @create 2017-12-01 19:56
 * @desc
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestWorkFowDubboService {
    @Autowired
    WorkFlowDubboService workFlowDubboService;

    @Test
    public void testResultMap(){
        List<String> assignees = new ArrayList<>();
        String assignee = "100028_382";
        assignees.add(assignee);
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("pageNum", 1);
        queryParam.put("pageSize", 100);
        queryParam.put("status", 0);
        queryParam.put("processType", "CGTH");
        Response<PageResult<Map<String, Object>>> response = workFlowDubboService.queryProcessMsgList(assignees, queryParam);
        PageResult<Map<String, Object>> resultObject = response.getResultObject();
        List<Map<String, Object>> data = resultObject.getData();
        System.out.print(data.toString());
    }

    @Test
    public void testQueryCommentHisByBusi(){
        String id = "41";
        String processType = "CGTH";
        Response<List<TaskProcessRecordDto>> listResponse = workFlowDubboService.queryCommentHis(id, processType);
        List<TaskProcessRecordDto> resultObject = listResponse.getResultObject();
        System.out.println("打印结果");
        for(TaskProcessRecordDto recordDto : resultObject){
            System.out.println(recordDto.toString());
        }

    }

    @Test
    public void testProcessImageByBusi() throws IOException {
        String id = "41";
        String processType = "CGTH";
        Response<String> stringResponse = workFlowDubboService.queryProcessImage(id, processType);
        String resultObject = stringResponse.getResultObject();
        Assert.assertNotNull(resultObject);
        System.out.println(resultObject);
    }
    
    @Test
    public void testStartProcess() throws IOException {
    	Response<Boolean> res = workFlowDubboService.saveStartProcess(1834L, 2, 10000, "10018");
        System.out.println(res.getResultObject());
    }
}
