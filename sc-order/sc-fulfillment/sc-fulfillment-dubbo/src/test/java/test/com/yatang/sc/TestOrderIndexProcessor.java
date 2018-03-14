package test.com.yatang.sc;

import com.busi.common.resp.Response;
import com.yatang.sc.inventory.dto.AvcostConditionDto;
import com.yatang.sc.inventory.dto.AvcostResultDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryQueryDubboService;
import com.yatang.sc.order.processor.OrderIndexMsgProcessor;
import com.yatang.sc.service.OrderIndexHelper;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestOrderIndexProcessor{

    @Autowired
    private OrderIndexMsgProcessor orderIndexMsgProcessor;

    @Autowired
    private ItemLocInventoryQueryDubboService itemLocInventoryQueryDubboService;

    @Autowired
    private OrderIndexHelper orderIndexHelper;

    @Test
    public void orderIndexProcessorTest(){
        String msg =  "{\"orderId\":\"100001708088030\"}";
        orderIndexMsgProcessor.process(msg);
    }

    @Test
    public void testQueryBatchItemLocInventory(){
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String,String> avCostMap = new HashMap<String,String>();
        List<AvcostConditionDto> avcostConditionDtos = new ArrayList<AvcostConditionDto>();
        AvcostConditionDto avcostConditionDto = new AvcostConditionDto();
        avcostConditionDto.setLoc("YT901");
        avcostConditionDto.setItemId("102998");
        avcostConditionDtos.add(avcostConditionDto);
        Response<List<AvcostResultDto>> reponse = itemLocInventoryQueryDubboService.queryBatchItemLocInventory(avcostConditionDtos);
        if(reponse != null && reponse.isSuccess() && !CollectionUtils.isEmpty(reponse.getResultObject())){
            for (AvcostResultDto avcostResultDto : reponse.getResultObject()){
                //同一个订单上逻辑仓库编码相同，直接使用商品id作为key
                avCostMap.put(avcostResultDto.getItemId(),avcostResultDto.getAvCost()!=null?df.format(avcostResultDto.getAvCost()):null);
            }
        }
    }

    @Test
    public void testSendOrderIndexMessage(){

        orderIndexHelper.sendOrderIndexMessage("100001708088030");

    }
}
