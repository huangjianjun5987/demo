package test.com.yatang.sc.inventory;

import com.yatang.sc.inventory.domain.TranData;
import com.yatang.sc.inventory.service.TranDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestTranDataService {

    @Autowired
    TranDataService tranDataService;

    @Test
    public void saveTranData(){
        TranData tranData = new TranData();
        tranData.setItem("item");
        tranData.setClasss("class");
        tranData.setDept("dept");
        tranData.setGroups("group");
        tranData.setLocation("location");
        tranData.setSubclass("subclass");
        tranData.setTotalCost(111d);
        tranData.setTotalRetail(333d);
        tranData.setTranCode("code");
        tranData.setRefNo1("refNo1");
        tranData.setUnits(1l);
        tranData.setTranDate(new Date());
        tranDataService.save(tranData);
    }
}
