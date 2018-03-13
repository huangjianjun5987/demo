package test.com.yatang.sc.inventory;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.inventory.domain.CfAreaWarehouse;
import com.yatang.sc.inventory.service.AreaWarehouseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestAreWarehouseService {

    @Autowired
    AreaWarehouseService areaWarehouseService;

    @Test
    public void testQueryAreawarehouseByProvince(){

        CfAreaWarehouse cfAreaWarehouse = areaWarehouseService.queryAreawarehouseByProvince("四川");
        System.out.println(JSON.toJSONString(cfAreaWarehouse));
    }
}
