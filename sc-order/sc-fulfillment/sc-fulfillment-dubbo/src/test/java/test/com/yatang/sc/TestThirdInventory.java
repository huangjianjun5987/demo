package test.com.yatang.sc;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.service.ThirdInventoryHelper;
//import com.yatang.xcsm.common.response.Response;
//import com.yatang.xcsm.remote.api.dubboxservice.OutViperOrderDubboxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestThirdInventory {

    @Autowired
    ThirdInventoryHelper thirdInventoryHelper;

//    @Autowired
//    OutViperOrderDubboxService outViperOrderDubboxService;

    @Test
    public void testSendXCSignNumber(){

        thirdInventoryHelper.sendXCSignNumber("100002017121200009");
    }

    @Test
    public void testThirdOrderDeliver(){

        Map<String,String> logistics = new HashMap<>();
        logistics.put("NO12456841603","test2");
//        Response<String> response = outViperOrderDubboxService.notifyLogisticsInfo("sitZ0015840005231",logistics);
//        System.out.println(JSON.toJSONString(response));
    }
}
