package test.dubbo;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.kidd.serialize.xml.XmlDataBean;
import com.busi.kidd.serialize.xml.XmlDataSerialization;
import com.yatang.sc.kidd.dto.ProviderRequestDto;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelOrdersDto;
import com.yatang.sc.kidd.dto.purchase.KiddEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundOrderLinesDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseRefundStockoutCreateDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnConfirmDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseReturnReceiverInfoDto;
import com.yatang.sc.kidd.dto.purchase.kiddPurchaseRefundConfirmResultDto;
import com.yatang.sc.kidd.service.KiddOrderCancelService;
import com.yatang.sc.kidd.service.KiddPurchaseReturnService;
import com.yatang.sc.kidd.service.KiddPurchaseService;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述:入库单操作测试类
 * @类名:TestKiddPurchaseOrderService
 * @作者: lvheping
 * @创建时间: 2017/9/27 11:23
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestKiddPurchaseOrderService {
    @Resource(name = "kiddPurchaseService")
    private KiddPurchaseService kiddPurchaseService;
    @Resource(name = "kiddOrderCancelService")
    private KiddOrderCancelService kiddOrderCancelService;
    @Resource(name ="xmlSerialization")
    private XmlDataSerialization xmlDataSerialization;

    @Autowired
    private KiddPurchaseReturnService kiddPurchaseReturnService;

    /**
     * 新增入库单
     */
    @Test
    public void create() throws ParseException {
        KiddEntryOrderDto kiddEntryOrderDto = new KiddEntryOrderDto();
        KiddSenderInfoDto senderInfoDto = new KiddSenderInfoDto();
        List<KiddOrderLinesDto> orderLines = new ArrayList<>();
        KiddOrderLinesDto kiddOrderLinesDto = new KiddOrderLinesDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        kiddEntryOrderDto.setWareHouseCode("WLL");
        kiddEntryOrderDto.setOwnerCode("yatang");
        kiddEntryOrderDto.setEntryOrderCode("YT0005");
        kiddEntryOrderDto.setOwnerOrderCode("YT0005");


        kiddEntryOrderDto.setWareHouseCode("WLL");
        kiddEntryOrderDto.setOwnerCode("yatang");
        kiddEntryOrderDto.setEntryOrderCode("YT0005");
        kiddEntryOrderDto.setOwnerOrderCode("YT0005");


        kiddEntryOrderDto.setOrderType("CGRK");
        kiddEntryOrderDto.setExpectStartTime(sdf.parse("2017-09-27 11:11:00"));
        kiddEntryOrderDto.setExpectEndTime(sdf.parse("2017-10-12 11:11:00"));
        kiddEntryOrderDto.setSupplierName("雅堂小超供应商");
        kiddEntryOrderDto.setSupplierCode("YTS-001");
        kiddEntryOrderDto.setTotalOrderLines(1);
        kiddEntryOrderDto.setCreateTimeERP(sdf.parse("2017-09-27 11:11:00"));
        kiddEntryOrderDto.setUpdateTimeERP(sdf.parse("2017-09-27 11:11:00"));
        kiddEntryOrderDto.setOperateTime(sdf.parse("2017-09-27 14:11:00"));

        senderInfoDto.setSenderCompany("大大食品");
        senderInfoDto.setSenderName("刘**");
        senderInfoDto.setSenderMobile("13542523621");
        senderInfoDto.setSenderEmail("22626161@qq.com");
        senderInfoDto.setSenderProvince("四川省");
        senderInfoDto.setSenderCity("成都市");
        senderInfoDto.setSenderArea("高新区");
        senderInfoDto.setSenderDetailAddress("成都科学城D区");

        kiddOrderLinesDto.setOrderLineNo("N00002");
        kiddOrderLinesDto.setItemCode("prod619");
        kiddOrderLinesDto.setInventoryType("ZP");
        kiddOrderLinesDto.setItemName("Assam");
        kiddOrderLinesDto.setPcs("500ML*1");
        kiddOrderLinesDto.setIsGift("非赠品");
        kiddOrderLinesDto.setPlanQty(25);
        kiddOrderLinesDto.setStockUnit("瓶");
        orderLines.add(kiddOrderLinesDto);
        kiddEntryOrderDto.setSenderInfoDto(senderInfoDto);
        kiddEntryOrderDto.setOrderLines(orderLines);
        Response<String> response = kiddPurchaseService.add(kiddEntryOrderDto);
        System.out.println(response.getErrorMessage());
        System.out.println(response.getResultObject());
    }


    /**
     * 新增入库单(桔瓣)
     */
    @Test
    public void testJuBanCreatePurchaseOrder() throws ParseException {
        KiddEntryOrderDto kiddEntryOrderDto = new KiddEntryOrderDto();
        KiddSenderInfoDto senderInfoDto = new KiddSenderInfoDto();
        List<KiddOrderLinesDto> orderLines = new ArrayList<>();
        KiddOrderLinesDto kiddOrderLinesDto = new KiddOrderLinesDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        kiddEntryOrderDto.setWareHouseCode("JB001");
        kiddEntryOrderDto.setOwnerCode("JB01");
        kiddEntryOrderDto.setEntryOrderCode("YTJB00017");
        kiddEntryOrderDto.setOwnerOrderCode("YTJB00017");


        kiddEntryOrderDto.setOrderType("CGRK");
        kiddEntryOrderDto.setExpectStartTime(sdf.parse("2017-09-27 11:11:00"));
        kiddEntryOrderDto.setExpectEndTime(sdf.parse("2017-10-12 11:11:00"));
        kiddEntryOrderDto.setSupplierName("雅堂小超供应商");
        kiddEntryOrderDto.setSupplierCode("YTS-001");
        kiddEntryOrderDto.setTotalOrderLines(1);
        kiddEntryOrderDto.setCreateTimeERP(sdf.parse("2017-09-27 11:11:00"));
        kiddEntryOrderDto.setUpdateTimeERP(sdf.parse("2017-09-27 11:11:00"));
        kiddEntryOrderDto.setOperateTime(sdf.parse("2017-09-27 14:11:00"));

        senderInfoDto.setSenderCompany("大大食品");
        senderInfoDto.setSenderName("刘**");
        senderInfoDto.setSenderMobile("13542523621");
        senderInfoDto.setSenderEmail("22626161@qq.com");
        senderInfoDto.setSenderProvince("四川省");
        senderInfoDto.setSenderCity("成都市");
        senderInfoDto.setSenderArea("高新区");
        senderInfoDto.setSenderDetailAddress("成都科学城D区");

        kiddOrderLinesDto.setOrderLineNo("N00002");
        kiddOrderLinesDto.setItemCode("prod619");
        kiddOrderLinesDto.setInventoryType("ZP");
        kiddOrderLinesDto.setItemName("Assam");
        kiddOrderLinesDto.setPcs("500ML*1");
        kiddOrderLinesDto.setIsGift("非赠品");
        kiddOrderLinesDto.setPlanQty(25);
        kiddOrderLinesDto.setStockUnit("瓶");
        orderLines.add(kiddOrderLinesDto);
        kiddEntryOrderDto.setSenderInfoDto(senderInfoDto);
        kiddEntryOrderDto.setOrderLines(orderLines);
        Response<String> response = kiddPurchaseService.add(kiddEntryOrderDto);
        System.out.println(response.getErrorMessage());
        System.out.println(response.getResultObject());
    }


    @Test
    //取消
    public void cancelTest() throws ParseException, IOException {
        System.out.println("--- Consume Started ---");
        KiddCancelAllOrdersDto params = new KiddCancelAllOrdersDto();
        params.setType(2);
        params.setOrderType("CGTH");
        List<KiddCancelOrdersDto> cancelOrdersDtoList = new ArrayList<KiddCancelOrdersDto>();
        KiddCancelOrdersDto cancelOrders = new KiddCancelOrdersDto();
        cancelOrders.setOrderCode("YT0005");
        cancelOrders.setOrgCode("YTXC");
        cancelOrders.setOrderType("CGTH");
        cancelOrders.setWarehouseCode("WLL");
        cancelOrdersDtoList.add(cancelOrders);

//        KiddCancelOrdersDto cancelOrders2 = new KiddCancelOrdersDto();
//        cancelOrders2.setOrderCode("YT0004");
//        cancelOrders2.setOrgCode("YTXC");
//        cancelOrders2.setOrderType("CGRK");
//        cancelOrders2.setWarehouseCode("WLL");
//        cancelOrdersDtoList.add(cancelOrders2);
        params.setOrders(cancelOrdersDtoList);
        Response<String> cancel = kiddOrderCancelService.cancel(params);
        System.out.println(JSON.toJSONString(cancel));
        System.out.println("--- Consume End ---");
    }


    /**
     * 桔瓣新增采购退货单
     */
    @Test
    public void testJubanCreatePurchaseReturnOrder() throws Exception {

        KiddPurchaseRefundStockoutCreateDto kiddPurchaseRefundStockoutCreateDto = new KiddPurchaseRefundStockoutCreateDto();




        //采购退货dto
        KiddPurchaseReturnDto kiddPurchaseReturnDto = new KiddPurchaseReturnDto();

        kiddPurchaseReturnDto.setDeliveryOrderCode("YTJTB00011");//退货单号
        kiddPurchaseReturnDto.setOrderType("CGTH");
        kiddPurchaseReturnDto.setWarehouseCode("JB001");
        kiddPurchaseReturnDto.setOwnerCode("JB01");



        kiddPurchaseReturnDto .setCreateTime("2017-09-27 11:11:00");


        //收件人信息
        KiddPurchaseReturnReceiverInfoDto kiddPurchaseReturnReceiverInfoDto = new KiddPurchaseReturnReceiverInfoDto();

        kiddPurchaseReturnReceiverInfoDto.setCompany("ys_company");
        kiddPurchaseReturnReceiverInfoDto.setName("ys");
        kiddPurchaseReturnReceiverInfoDto.setMobile("15123247217");
        kiddPurchaseReturnReceiverInfoDto.setCity("重庆");
        kiddPurchaseReturnReceiverInfoDto.setProvince("重庆");
        kiddPurchaseReturnReceiverInfoDto.setEmail("262677858@qq.com");
        kiddPurchaseReturnReceiverInfoDto.setArea("xxxx地点");
        kiddPurchaseReturnReceiverInfoDto.setDetailAddress("xxxx详情");

        kiddPurchaseReturnDto.setReceiverInfo(kiddPurchaseReturnReceiverInfoDto);


        kiddPurchaseRefundStockoutCreateDto.setDeliveryOrder(kiddPurchaseReturnDto);
        kiddPurchaseRefundStockoutCreateDto.setOrder(kiddPurchaseReturnDto);

        List<KiddPurchaseRefundOrderLinesDto> kiddPurchaseRefundOrderLinesDtos = new ArrayList<>();
        //订单
        KiddPurchaseRefundOrderLinesDto kiddPurchaseRefundOrderLinesDto = new KiddPurchaseRefundOrderLinesDto();

        kiddPurchaseRefundOrderLinesDto.setOrderLineNo("1");
        kiddPurchaseRefundOrderLinesDto.setOwnerCode("JB01");
        kiddPurchaseRefundOrderLinesDto.setPlanQty(11);
        kiddPurchaseRefundOrderLinesDto.setItemCode("prod619");
        kiddPurchaseRefundOrderLinesDto.setItemName("测试商品");


        kiddPurchaseRefundOrderLinesDtos.add(kiddPurchaseRefundOrderLinesDto);
        kiddPurchaseRefundStockoutCreateDto.setOrderLines(kiddPurchaseRefundOrderLinesDtos);



        kiddPurchaseReturnService.createPurchaseRefund(kiddPurchaseRefundStockoutCreateDto);

    }





    /**
     * (际链)新增采购退货单
     */
    @Test
    public void testGLinkCreatePurchaseReturnOrder() {

        KiddPurchaseRefundStockoutCreateDto kiddPurchaseRefundStockoutCreateDto = new KiddPurchaseRefundStockoutCreateDto();




        //采购退货dto
        KiddPurchaseReturnDto kiddPurchaseReturnDto = new KiddPurchaseReturnDto();

        kiddPurchaseReturnDto.setDeliveryOrderCode("YTJTB000112");//退货单号
        kiddPurchaseReturnDto.setOrderType("CGTH");
        kiddPurchaseReturnDto.setWarehouseCode("YT901");
        kiddPurchaseReturnDto.setOwnerCode("YTXC");



        //收件人信息
        KiddPurchaseReturnReceiverInfoDto kiddPurchaseReturnReceiverInfoDto = new KiddPurchaseReturnReceiverInfoDto();

        kiddPurchaseReturnReceiverInfoDto.setCompany("ys_company");
        kiddPurchaseReturnReceiverInfoDto.setName("ys");
        kiddPurchaseReturnReceiverInfoDto.setMobile("15123247217");
        kiddPurchaseReturnReceiverInfoDto.setCity("重庆");
        kiddPurchaseReturnReceiverInfoDto.setProvince("重庆");
        kiddPurchaseReturnReceiverInfoDto.setEmail("262677858@qq.com");
        kiddPurchaseReturnReceiverInfoDto.setArea("xxxx地点");
        kiddPurchaseReturnReceiverInfoDto.setDetailAddress("xxxx详情");

        kiddPurchaseReturnDto.setReceiverInfo(kiddPurchaseReturnReceiverInfoDto);


        kiddPurchaseRefundStockoutCreateDto.setDeliveryOrder(kiddPurchaseReturnDto);
        kiddPurchaseRefundStockoutCreateDto.setOrder(kiddPurchaseReturnDto);

        List<KiddPurchaseRefundOrderLinesDto> kiddPurchaseRefundOrderLinesDtos = new ArrayList<>();
        //订单
        KiddPurchaseRefundOrderLinesDto kiddPurchaseRefundOrderLinesDto = new KiddPurchaseRefundOrderLinesDto();

        kiddPurchaseRefundOrderLinesDto.setOrderLineNo("1");
        kiddPurchaseRefundOrderLinesDto.setOwnerCode("JB01");
        kiddPurchaseRefundOrderLinesDto.setPlanQty(11);
        kiddPurchaseRefundOrderLinesDto.setItemCode("prod619");
        kiddPurchaseRefundOrderLinesDto.setItemName("测试商品");


        kiddPurchaseRefundOrderLinesDtos.add(kiddPurchaseRefundOrderLinesDto);
        kiddPurchaseRefundStockoutCreateDto.setOrderLines(kiddPurchaseRefundOrderLinesDtos);




        kiddPurchaseReturnService.createPurchaseRefund(kiddPurchaseRefundStockoutCreateDto);

    }


    /**
     * 桔瓣新增确认采购退货
     */
    public void testJubanConfirmPurchaseRefund() {
        String xml = "<request>\n" +
                "  <deliveryOrder>\n" +
                "    <deliveryOrderCode>OC2017100500097</deliveryOrderCode>\n" +
                "    <deliveryOrderId>OC2017100500097</deliveryOrderId>\n" +
                "    <warehouseCode>WLL</warehouseCode>\n" +
                "    <orderConfirmTime>2017-09-27 10:44:41</orderConfirmTime>\n" +
                "    <orderType>PTCK</orderType>\n" +
                "    <status>DELIVERED</status>\n" +
                "    <outBizCode>RTWLL170927001001</outBizCode>\n" +
                "    <confirmType>0</confirmType>\n" +
                "    <logisticsName/>\n" +
                "    <expressCode/>\n" +
                "  </deliveryOrder>\n" +
                "  <packages>\n" +
                "    <package>\n" +
                "      <items>\n" +
                "        <item>\n" +
                "          <itemCode>prod619</itemCode>\n" +
                "          <itemId>prod619</itemId>\n" +
                "          <quantity>2</quantity>\n" +
                "        </item>\n" +
                "      </items>\n" +
                "    </package>\n" +
                "  </packages>\n" +
                "  <orderLines>\n" +
                "    <orderLine>\n" +
                "      <outBizCode>RTWLL170927001001</outBizCode>\n" +
                "      <orderLineNo>prod619</orderLineNo>\n" +
                "      <itemCode>prod619</itemCode>\n" +
                "      <itemId>prod619</itemId>\n" +
                "      <itemName/>\n" +
                "      <inventoryType>ZP</inventoryType>\n" +
                "      <actualQty>2</actualQty>\n" +
                "      <batchCode/>\n" +
                "      <produceCode>Z020202001</produceCode>\n" +
                "      <batchs>\n" +
                "        <batch>\n" +
                "          <batchCode/>\n" +
                "          <inventoryType>ZP</inventoryType>\n" +
                "          <actualQty>2</actualQty>\n" +
                "        </batch>\n" +
                "      </batchs>\n" +
                "    </orderLine>\n" +
                "  </orderLines>\n" +
                "</request>";
        XmlDataBean dataBean = new XmlDataBean();
        dataBean.setRootAlias("request");
        dataBean.setXmlStr(xml);
        dataBean.setDataClass(SaleOrderConfirmRequestDto.class);
       // xmlDataSerialization.deserialize(dataBean);
        SaleOrderConfirmRequestDto noticeRequestInfoDto = (SaleOrderConfirmRequestDto) dataBean.getData();

        ProviderRequestDto providerDto = new ProviderRequestDto();
        providerDto.setPayload(noticeRequestInfoDto);
       // kiddFacadeService.send(providerDto);

    }


}
