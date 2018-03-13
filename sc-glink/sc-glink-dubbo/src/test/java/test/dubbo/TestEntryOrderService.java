package test.dubbo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.glink.dto.CancelAllOrdersDto;
import com.yatang.sc.glink.dto.CancelOrdersDto;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.glink.dto.OrderLinesDto;
import com.yatang.sc.glink.dto.SenderInfoDto;
import com.yatang.sc.glink.dto.entryOrder.EntryOrderDto;
import com.yatang.sc.glink.dto.entryOrder.EntryOrderRepDto;
import com.yatang.sc.glink.dto.entryOrder.EntryOrderRepResultDto;
import com.yatang.sc.glink.dto.entryOrder.OrderLinesRepDto;
import com.yatang.sc.glink.dto.entryOrder.SelectEntryOrderDto;
import com.yatang.sc.glink.service.kidd.GLinkPurchaseProxyService;

/**
 * create by chensiqiang
 * time:2017/8/4 15:45
 * description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring/applicationContext-dubbo.xml"})
public class TestEntryOrderService {

    @Autowired
    private GLinkPurchaseProxyService gLinkPurchaseProxyService;

    @Test
    public void testAdd() throws ParseException {
        System.out.println("--- Consume Started ---");
        EntryOrderDto params = new EntryOrderDto();
        SenderInfoDto senderInfoDto = new SenderInfoDto();
        OrderLinesDto orderLinesDto = new OrderLinesDto();
        List<OrderLinesDto> orderList = new ArrayList<OrderLinesDto>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        params.setWareHouseCode("10089");
        params.setOwnerCode("YTXC");
        params.setEntryOrderCode("YT0004");
        params.setOwnerOrderCode("YT0004");
        params.setOrderType("CGRK");
        params.setExpectStartTime(sdf.parse("2017-08-05 11:11:00"));
        params.setExpectEndTime(sdf.parse("2017-08-12 11:11:00"));
        params.setSupplierName("雅堂小超供应商");
        params.setSupplierCode("YTS-001");
        params.setTotalOrderLines(1);
        params.setCreateTimeERP(sdf.parse("2017-08-02 11:11:00"));
        params.setUpdateTimeERP(sdf.parse("2017-08-02 11:11:00"));
        params.setOperateTime(sdf.parse("2017-08-02 14:11:00"));

        senderInfoDto.setSenderCompany("大大食品");
        senderInfoDto.setSenderName("刘**");
        senderInfoDto.setSenderMobile("13542523621");
        senderInfoDto.setSenderEmail("22626161@qq.com");
        senderInfoDto.setSenderProvince("四川省");
        senderInfoDto.setSenderCity("成都市");
        senderInfoDto.setSenderArea("高新区");
        senderInfoDto.setSenderDetailAddress("成都科学城D区");

        orderLinesDto.setOrderLineNo("N00002");
        orderLinesDto.setItemCode("prod619");
        orderLinesDto.setInventoryType("ZP");
        orderLinesDto.setItemName("Assam");
        orderLinesDto.setPcs("500ML*1");
        orderLinesDto.setIsGift("非赠品");
        orderLinesDto.setPlanQty(25);
        orderLinesDto.setStockUnit("瓶");

        orderList.add(orderLinesDto);

        params.setOrderLines(orderList);
        params.setSenderInfoDto(senderInfoDto);


        GlinkResponseDto<String> response=gLinkPurchaseProxyService.create(params);
        System.out.println(JSON.toJSONString(response));
        System.out.println("--- Consume End ---");
    }

    @Test
    public void testSelect() throws ParseException {
        System.out.println("--- Consume Started ---");
        SelectEntryOrderDto params = new SelectEntryOrderDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        params.setWareHouseCode("10089");
        params.setOwnerCode("YTXC");
        params.setStartTime(sdf.parse("2017-08-01 00:00:00"));
        params.setStartTime(sdf.parse("2017-08-31 23:59:59"));
        params.setEntryOrderCode("YT002");
        params.setOrderType("CGRK");

        GlinkResponseDto<EntryOrderRepDto> response = gLinkPurchaseProxyService.select(params);
        EntryOrderRepDto rep = response.getData();
        for (EntryOrderRepResultDto reps : rep.getResult()){
               for (OrderLinesRepDto ress : reps.getOrderLines()){
                    System.out.println(ress.getAcualQty());
               }
        }
        System.out.println(JSON.toJSONString(response));
        System.out.println("--- Consume End ---");
    }

    @Test
    public void testClose() throws ParseException {
        System.out.println("--- Consume Started ---");
        CancelAllOrdersDto params = new CancelAllOrdersDto();
        List<CancelOrdersDto> orderList = new ArrayList<CancelOrdersDto>();
        CancelOrdersDto cancelOrdersDto = new CancelOrdersDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        params.setType(2);

        cancelOrdersDto.setOrgCode("YTXC");
        cancelOrdersDto.setOrderType("CGRK");
        cancelOrdersDto.setOrderCode("YT0004");
        //cancelOrdersDto.setProjectCode("YTXC-CD");

        orderList.add(cancelOrdersDto);
        params.setOrders(orderList);

       // GlinkResponseDto<CancelAllOrdersRepDto> response = entryOrderService.close(params);
        //CancelAllOrdersRepDto rep = response.getData();
        //System.out.println(JSON.toJSONString(response));
        System.out.println("--- Consume End ---");
    }
}
