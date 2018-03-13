package test.com.yatang.sc.inventory;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.inventory.dto.ItemTranDtoList;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xiangyonghong on 2017/7/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-consumer.xml"})
public class TestTranDataDubboService {

//    @Autowired
//    TranDataDubboService tranDataDubboService;

    @Autowired
    ItemLocInventoryDubboService itemLocInventoryDubboService;

    @Test
    public void saveTranData() {
      /*  TranDataDto tranDataDto = new TranDataDto();
        tranDataDto.setItem("item");
        tranDataDto.setClasss("class");
        tranDataDto.setDept("dept");
        tranDataDto.setGroup("group");
        tranDataDto.setLocation("location");
        tranDataDto.setSubclass("subclass");
        tranDataDto.setTotalCost(111d);
        tranDataDto.setTotalRetail(333d);
        tranDataDto.setTranCode("code");
        tranDataDto.setRefNo1("refNo1");
        tranDataDto.setUnits(1);
        tranDataDto.setTranDate(new Date());

        tranDataDubboService.addTranData(tranDataDto);*/
    }


    @Test
    public void testPurchaseStockIn() {
        String message = "{\n" +
                "            \"itemTrans\": [\n" +
                "            {\n" +
                "                \"itemLocSoh\": {\n" +
                "                \"itemId\": \"xprod347927\",\n" +
                "                        \"productCode\": \"1000393\",\n" +
                "                        \"stockOnHand\": 6,\n" +
                "                        \"unitCost\": 4.8\n" +
                "            },\n" +
                "                \"tTranData\": {\n" +
                "                \"dept\": \"xcate26\",\n" +
                "                        \"groups\": \"xcate4\",\n" +
                "                        \"item\": \"xprod347927\",\n" +
                "                        \"productCode\": \"1000393\",\n" +
                "                        \"refNo1\": \"18010800009\",\n" +
                "                        \"refNo2\": \"18010800005\",\n" +
                "                        \"subclass\": \"xcate827\",\n" +
                "                        \"units\": 6,\n" +
                "                        \"vatRate\": \"17.00\"\n" +
                "            }\n" +
                "            }\n" +
                "    ],\n" +
                "            \"loc\": \"YT901\"\n" +
                "        }";

        ItemTranDtoList list = JSON.parseObject(message, ItemTranDtoList.class);//获取调整单数据

        itemLocInventoryDubboService.purchaseStockIn(list);
        System.out.println(list);
    }

}
