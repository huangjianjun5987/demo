package test.dubbo;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.glink.dto.saleOrder.SelectOrderCodeOKDto;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelOrdersDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderCargoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDeliveryInfoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderReceiverInfoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderRelateOrderDto;
import com.yatang.sc.kidd.service.KiddOrderCancelService;
import com.yatang.sc.kidd.service.KiddReturnOrderService;
import com.yatang.sc.kidd.service.KiddSaleOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-consumer.xml"})
public class TestKiddSaleOrderService {

 /*   @Autowired
    private SaleOrderService saleOrderService;*/

    @Autowired
    private KiddSaleOrderService kiddSaleOrderService;

    @Autowired
    private KiddOrderCancelService kiddOrderCancelService;

    @Autowired
    private KiddReturnOrderService kiddReturnOrderService;

    @Test
    //查询
    public void selectTest() throws ParseException, IOException {
        System.out.println("--- Consume Started ---");
        SelectOrderCodeOKDto params = new SelectOrderCodeOKDto();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = "2017-01-03 23:01:01";
        params.setStartTime(sdf.parse(str));
        str = "2017-09-03 23:01:01";
        params.setEndTime(sdf.parse(str));
        params.setOrgCode("YTXC");
        params.setOrderType("FHCK");
        params.setDeliveryOrderCode("OC201705050000");
       // GlinkResponseDto<SelectOrderCodeOKResultDto> response = saleOrderService.select(params);
      /*  System.out.println(response.getData().getPageNo());
        System.out.println(JSON.toJSONString(response));*/
        System.out.println("--- Consume End ---");
    }

    @Test
    //新增
    public void addTest() throws ParseException {
        System.out.println("--- Consume Started ---");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        KiddSaleOrderDto kiddSaleOrderDto = new KiddSaleOrderDto();//订单信息
        KiddSaleOrderDeliveryInfoDto deliveryInfoDto = new KiddSaleOrderDeliveryInfoDto();//物流相关信息

        KiddSaleOrderRelateOrderDto kiddSaleOrderRelateOrderDto = new KiddSaleOrderRelateOrderDto();//订单相关信息

        kiddSaleOrderRelateOrderDto.setOrderCode("OC2017100500080");
        deliveryInfoDto.setScOrderType("ZYYH");
        deliveryInfoDto.setDeliveryOrderCode("OC2017100500080");
        deliveryInfoDto.setWarehouseCode(TestConstants.GLINK_WAREHOUSE_CODE);
        deliveryInfoDto.setOwnerCode(TestConstants.GLINK_OWNER_CODE);
        deliveryInfoDto.setCreateTime(sdf.parse("2017-05-06 16:55:05"));

        //收货人信息

        KiddSaleOrderReceiverInfoDto receiverInfoDto = new KiddSaleOrderReceiverInfoDto();

        receiverInfoDto.setName("ys112");
        receiverInfoDto.setMobile("15123247205");
        receiverInfoDto.setProvince("四川");
        receiverInfoDto.setCity("阿坝藏族羌族自治州1");
        receiverInfoDto.setDetailAddress("汶川县111路111号1");
        deliveryInfoDto.setReceiverInfo(receiverInfoDto);
        kiddSaleOrderDto.setDeliveryOrder(deliveryInfoDto);

        List<KiddSaleOrderCargoDto> cargos = new ArrayList<>();
        KiddSaleOrderCargoDto cargo = new KiddSaleOrderCargoDto();
        cargo.setOrderLineNo("1");
        cargo.setItemCode("prod619");
        cargo.setItemName("威化酥饼干");
        cargo.setPlanQty(20);
        cargo.setInventoryType("ZP");
        cargos.add(cargo);
        KiddSaleOrderCargoDto cargo2 = new KiddSaleOrderCargoDto();
        cargo2.setOrderLineNo("2");
        cargo2.setItemCode("prod619");
        cargo2.setItemName("威化酥饼干");
        cargo2.setPlanQty(1);
        cargo2.setInventoryType("ZP");
        cargos.add(cargo2);
        kiddSaleOrderDto.setOrderLines(cargos);
        Response<String> response = kiddSaleOrderService.add(kiddSaleOrderDto);
        System.out.println(JSON.toJSONString(response));
        System.out.println("--- Consume End ---");
    }


    @Test
    //新增
    public void addXyTest() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("--- Consume Started ---");
 /*       KiddSaleOrderDto kiddSaleOrderDto = new KiddSaleOrderDto();
        KiddSaleOrderDeliveryInfoDto deliveryOrder = new KiddSaleOrderDeliveryInfoDto();
        //仓库信息
        deliveryOrder.setOrderType("PTCK");
        deliveryOrder.setDeliveryOrderCode("OC2017100500098");
        deliveryOrder.setWarehouseCode(TestConstants.XINYI_WAREHOUSE_CODE);
        deliveryOrder.setCreateTime(sdf.parse("2017-05-06 16:55:05"));


        KiddSaleOrderReceiverInfoDto receiverInfo = new KiddSaleOrderReceiverInfoDto();//收货人信息
        receiverInfo.setName("ys112");
        receiverInfo.setMobile("15123247205");
        receiverInfo.setProvince("四川");
        receiverInfo.setCity("阿坝藏族羌族自治州1");
        receiverInfo.setDetailAddress("汶川县111路111号1");
        deliveryOrder.setReceiverInfo(receiverInfo);

        kiddSaleOrderDto.setDeliveryOrder(deliveryOrder);

        //仓库信息
        KiddSaleOrderCargoDto cargo = new KiddSaleOrderCargoDto();

        cargo.setItemCode("prod619");
        cargo.setItemName("威化酥饼干");
        cargo.setPlanQty(2000);
        cargo.setOwnerCode(TestConstants.XINYI_OWNER_CODE);

        List<KiddSaleOrderCargoDto> cargoDtoList = new ArrayList<>();
        cargoDtoList.add(cargo);
        kiddSaleOrderDto.setOrderLines(cargoDtoList);*/

        KiddSaleOrderDto kiddSaleOrderDto = new KiddSaleOrderDto();//订单信息
        KiddSaleOrderDeliveryInfoDto deliveryInfoDto = new KiddSaleOrderDeliveryInfoDto();//物流相关信息

        KiddSaleOrderRelateOrderDto kiddSaleOrderRelateOrderDto = new KiddSaleOrderRelateOrderDto();//订单相关信息

        kiddSaleOrderRelateOrderDto.setOrderCode("OC2017100500076");
        deliveryInfoDto.setDeliveryOrderCode("OC2017100500076");
        deliveryInfoDto.setWarehouseCode(TestConstants.XINYI_WAREHOUSE_CODE);
        deliveryInfoDto.setCreateTime(sdf.parse("2017-05-06 16:55:05"));


        //收货人信息

        KiddSaleOrderReceiverInfoDto receiverInfoDto = new KiddSaleOrderReceiverInfoDto();


        receiverInfoDto.setName("ys112");
        receiverInfoDto.setMobile("15123247205");
        receiverInfoDto.setProvince("四川");
        receiverInfoDto.setCity("阿坝藏族羌族自治州1");
        receiverInfoDto.setDetailAddress("汶川县111路111号1");
        deliveryInfoDto.setReceiverInfo(receiverInfoDto);
        kiddSaleOrderDto.setDeliveryOrder(deliveryInfoDto);

        List<KiddSaleOrderCargoDto> cargoDtoList = new ArrayList<>();
        KiddSaleOrderCargoDto saleOrderCargoDto = new KiddSaleOrderCargoDto();
        saleOrderCargoDto.setItemCode("prod619");
        saleOrderCargoDto.setItemName("威化酥饼干");
        saleOrderCargoDto.setPlanQty(20);
        cargoDtoList.add(saleOrderCargoDto);
        kiddSaleOrderDto.setOrderLines(cargoDtoList);
        Response<String> add = kiddSaleOrderService.add(kiddSaleOrderDto);


//       // System.out.println(JSON.toJSONString(add));*/
        System.out.println("--- Consume End ---");
    }


    /**
     * 桔瓣新增销售订单测试
     *
     * @throws ParseException
     */
    @Test
    public void TestJubanAddSaleOrder() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("--- Consume Started ---");


        KiddSaleOrderDto kiddSaleOrderDto = new KiddSaleOrderDto();//订单信息
        KiddSaleOrderDeliveryInfoDto deliveryInfoDto = new KiddSaleOrderDeliveryInfoDto();//物流相关信息

        KiddSaleOrderRelateOrderDto kiddSaleOrderRelateOrderDto = new KiddSaleOrderRelateOrderDto();//订单相关信息

        kiddSaleOrderRelateOrderDto.setOrderCode("OC2017100500077");
        deliveryInfoDto.setDeliveryOrderCode("OC2017100500077");
        deliveryInfoDto.setWarehouseCode("JB001");
        deliveryInfoDto.setCreateTime(sdf.parse("2017-05-06 16:55:05"));
        //收货人信息
        KiddSaleOrderReceiverInfoDto receiverInfoDto = new KiddSaleOrderReceiverInfoDto();
        receiverInfoDto.setName("ys112");
        receiverInfoDto.setMobile("15123247205");
        receiverInfoDto.setProvince("四川");
        receiverInfoDto.setCity("阿坝藏族羌族自治州1");
        receiverInfoDto.setDetailAddress("汶川县111路111号1");
        deliveryInfoDto.setReceiverInfo(receiverInfoDto);
        kiddSaleOrderDto.setDeliveryOrder(deliveryInfoDto);

        List<KiddSaleOrderCargoDto> cargoDtoList = new ArrayList<>();
        KiddSaleOrderCargoDto saleOrderCargoDto = new KiddSaleOrderCargoDto();
        saleOrderCargoDto.setItemCode("prod619");
        saleOrderCargoDto.setItemName("威化酥饼干");
        saleOrderCargoDto.setPlanQty(20);
        cargoDtoList.add(saleOrderCargoDto);
        kiddSaleOrderDto.setOrderLines(cargoDtoList);
        Response<String> add = kiddSaleOrderService.add(kiddSaleOrderDto);


        System.out.println(JSON.toJSONString(add));
        System.out.println("--- Consume End ---");
    }
    /**
     * 桔瓣新增电商订单
     *
     * @throws ParseException
     */
    @Test
    public void TestJubanAddDSXDOrder() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("--- Consume Started ---");


        KiddSaleOrderDto kiddSaleOrderDto = new KiddSaleOrderDto();//订单信息
        KiddSaleOrderDeliveryInfoDto deliveryInfoDto = new KiddSaleOrderDeliveryInfoDto();//物流相关信息

        KiddSaleOrderRelateOrderDto kiddSaleOrderRelateOrderDto = new KiddSaleOrderRelateOrderDto();//订单相关信息

        kiddSaleOrderRelateOrderDto.setOrderCode("DSXD2017100500078");
        deliveryInfoDto.setDeliveryOrderCode("DSXD2017100500078");
        deliveryInfoDto.setWarehouseCode("JB001");
        deliveryInfoDto.setCreateTime(sdf.parse("2017-05-06 16:55:05"));

        deliveryInfoDto.setScOrderType("DSXS");
        deliveryInfoDto.setLogisticsCode("SF");
        //收货人信息

        KiddSaleOrderReceiverInfoDto receiverInfoDto = new KiddSaleOrderReceiverInfoDto();


        receiverInfoDto.setName("ys112");
        receiverInfoDto.setMobile("15123247205");
        receiverInfoDto.setProvince("四川");
        receiverInfoDto.setCity("成都");
        receiverInfoDto.setArea("郫县");
        receiverInfoDto.setDetailAddress("汶川县111路111号1");
        deliveryInfoDto.setReceiverInfo(receiverInfoDto);
        kiddSaleOrderDto.setDeliveryOrder(deliveryInfoDto);

        List<KiddSaleOrderCargoDto> cargoDtoList = new ArrayList<>();
        KiddSaleOrderCargoDto saleOrderCargoDto = new KiddSaleOrderCargoDto();


        saleOrderCargoDto.setItemCode("prod619");
        saleOrderCargoDto.setItemName("威化酥饼干");
        saleOrderCargoDto.setPlanQty(20);
        cargoDtoList.add(saleOrderCargoDto);
        kiddSaleOrderDto.setOrderLines(cargoDtoList);

        Response<String> add = kiddSaleOrderService.add(kiddSaleOrderDto);


        System.out.println(JSON.toJSONString(add));
        System.out.println("--- Consume End ---");
    }


    @Test
    //取消
    public void cancelTest() throws ParseException, IOException {
        System.out.println("--- Consume Started ---");
/*        CancelAllOrdersDto params = new CancelAllOrdersDto();
        params.setType(1);
        List<CancelOrdersDto> cancelOrdersDtoList = new ArrayList<CancelOrdersDto>();
        CancelOrdersDto cancelOrders = new CancelOrdersDto();
        cancelOrders.setOrderCode("OC2017100500070");
        cancelOrders.setOrgCode("YTXC");
        cancelOrders.setOrderType("FHCK");
        cancelOrdersDtoList.add(cancelOrders);

        CancelOrdersDto cancelOrders2 = new CancelOrdersDto();
        cancelOrders2.setOrderCode("OC2017100500070");
        cancelOrders2.setOrgCode("YTXC");
        cancelOrders2.setOrderType("FHCK");
        cancelOrdersDtoList.add(cancelOrders2);
        params.setOrders(cancelOrdersDtoList);*/


        KiddCancelAllOrdersDto kiddCancelAllOrdersDto=new KiddCancelAllOrdersDto();

        List<KiddCancelOrdersDto> cancelOrdersDtos = new ArrayList<>();
        KiddCancelOrdersDto kiddCancelOrdersDto = new KiddCancelOrdersDto();
        kiddCancelOrdersDto.setOrderCode("OC2017100500071");
        kiddCancelOrdersDto.setOrgCode(TestConstants.XINYI_OWNER_CODE);
        kiddCancelOrdersDto.setWarehouseCode(TestConstants.XINYI_WAREHOUSE_CODE);
        cancelOrdersDtos.add(kiddCancelOrdersDto);
        kiddCancelAllOrdersDto.setOrders(cancelOrdersDtos);


        kiddCancelAllOrdersDto.setOrderType("KIDDXSCK");//发货出库
        Response<String> cancel = kiddOrderCancelService.cancel(kiddCancelAllOrdersDto);
        System.out.println("心怡取消订单"+JSON.toJSONString(cancel));
        System.out.println("--- Consume End ---");
    }


    /**
     * 桔瓣取消销售订单
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testJubanCancel() throws ParseException, IOException {
        System.out.println("--- Consume Started ---");
/*        CancelAllOrdersDto params = new CancelAllOrdersDto();
        params.setType(1);
        List<CancelOrdersDto> cancelOrdersDtoList = new ArrayList<CancelOrdersDto>();
        CancelOrdersDto cancelOrders = new CancelOrdersDto();
        cancelOrders.setOrderCode("OC2017100500070");
        cancelOrders.setOrgCode("YTXC");
        cancelOrders.setOrderType("FHCK");
        cancelOrdersDtoList.add(cancelOrders);

        CancelOrdersDto cancelOrders2 = new CancelOrdersDto();
        cancelOrders2.setOrderCode("OC2017100500070");
        cancelOrders2.setOrgCode("YTXC");
        cancelOrders2.setOrderType("FHCK");
        cancelOrdersDtoList.add(cancelOrders2);
        params.setOrders(cancelOrdersDtoList);*/


        KiddCancelAllOrdersDto kiddCancelAllOrdersDto = new KiddCancelAllOrdersDto();

        List<KiddCancelOrdersDto> cancelOrdersDtos = new ArrayList<>();
        KiddCancelOrdersDto kiddCancelOrdersDto = new KiddCancelOrdersDto();
        kiddCancelOrdersDto.setOrderCode("OC20171005000781");
        kiddCancelOrdersDto.setOrgCode(TestConstants.JUBAN_OWNER_CODE);
        kiddCancelOrdersDto.setWarehouseCode(TestConstants.JUBAN_WAREHOUSE_CODE);
        cancelOrdersDtos.add(kiddCancelOrdersDto);
        kiddCancelAllOrdersDto.setOrders(cancelOrdersDtos);


        kiddCancelAllOrdersDto.setOrderType("KIDDXSCK");//发货出库
        Response<String> cancel = kiddOrderCancelService.cancel(kiddCancelAllOrdersDto);
        System.out.println("桔瓣" + JSON.toJSONString(cancel));
        System.out.println("--- Consume End ---");
    }

    /**
     * 新增销售退货
     *
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testJubanSaleOrderTReturn() throws ParseException, IOException {
        System.out.println("--- Consume Started ---");


        KiddReturnOrderDto kiddReturnOrderDto = new KiddReturnOrderDto();


        kiddReturnOrderDto.setWareHouseCode("JB001");
        kiddReturnOrderDto.setOwnerCode("JB01");
        kiddReturnOrderDto.setEntryOrderCode("OC20171005000779T01");
        kiddReturnOrderDto.setPreDeliveryOrderCode("OC20171005000779");
        kiddReturnOrderDto.setOrderType("THRK");//入库单业务类型：THRK=退货入库 HHRK=退货入库(必填）
        kiddReturnOrderDto.setLogisticsCode("OTHER");


        KiddSenderInfoDto senderInfo = new KiddSenderInfoDto();

        senderInfo.setSenderName("ytt");
        senderInfo.setSenderCompany("companyName");
        senderInfo.setSenderZipcode("ZADG");
        senderInfo.setSenderTel("15123247205");
        senderInfo.setSenderMobile("15123247205");
        senderInfo.setSenderEmail("247677857y@126.com");
        senderInfo.setSenderCountrycode("ddd");
        senderInfo.setSenderProvince("重新");
        senderInfo.setSenderCity("CITY");
        senderInfo.setSenderArea("area");
        senderInfo.setSenderTown("TOWN");
        senderInfo.setSenderDetailAddress("具体数据");


        kiddReturnOrderDto.setSenderInfo(senderInfo);

        List<KiddOrderLinesDto> orderLines = new ArrayList<>();

        KiddOrderLinesDto kiddOrderLinesDto = new KiddOrderLinesDto();

        kiddOrderLinesDto.setOrderLineNo("1");

        kiddOrderLinesDto.setReturnOrderLineNo("2");
        kiddOrderLinesDto.setItemCode("prod619");
        kiddOrderLinesDto.setPlanQty(12);

        orderLines.add(kiddOrderLinesDto);

        kiddReturnOrderDto.setOrderLines(orderLines);


        kiddReturnOrderService.create(kiddReturnOrderDto);
        System.out.println("--- Consume End ---");
    }


    /**
     * 新增销售换货
     *
     * @throws ParseException
     * @throws IOException
     */
    @Test
    public void testJubanSaleOrderHReturn() throws ParseException, IOException {
        System.out.println("--- Consume Started ---");


        KiddReturnOrderDto kiddReturnOrderDto = new KiddReturnOrderDto();


        kiddReturnOrderDto.setWareHouseCode("JB001");
        kiddReturnOrderDto.setOwnerCode("JB01");
        kiddReturnOrderDto.setEntryOrderCode("OC20171005000780H01");
        kiddReturnOrderDto.setPreDeliveryOrderCode("OC20171005000780");
        kiddReturnOrderDto.setOrderType("HHRK");//入库单业务类型：THRK=退货入库 HHRK=退货入库(必填）
        kiddReturnOrderDto.setLogisticsCode("OTHER");


        KiddSenderInfoDto senderInfo = new KiddSenderInfoDto();

        senderInfo.setSenderName("ytt");
        senderInfo.setSenderCompany("companyName");
        senderInfo.setSenderZipcode("ZADG");
        senderInfo.setSenderTel("15123247205");
        senderInfo.setSenderMobile("15123247205");
        senderInfo.setSenderEmail("247677857y@126.com");
        senderInfo.setSenderCountrycode("ddd");
        senderInfo.setSenderProvince("重新");
        senderInfo.setSenderCity("CITY");
        senderInfo.setSenderArea("area");
        senderInfo.setSenderTown("TOWN");
        senderInfo.setSenderDetailAddress("具体数据");


        kiddReturnOrderDto.setSenderInfo(senderInfo);

        List<KiddOrderLinesDto> orderLines = new ArrayList<>();

        KiddOrderLinesDto kiddOrderLinesDto = new KiddOrderLinesDto();

        kiddOrderLinesDto.setOrderLineNo("1");

        kiddOrderLinesDto.setReturnOrderLineNo("2");
        kiddOrderLinesDto.setItemCode("prod619");
        kiddOrderLinesDto.setPlanQty(12);

        orderLines.add(kiddOrderLinesDto);

        kiddReturnOrderDto.setOrderLines(orderLines);


        kiddReturnOrderService.create(kiddReturnOrderDto);
        System.out.println("--- Consume End ---");
    }

    public static void main(String[] args) {
        String content = "<?xml version=”1.0″ encoding=”utf-8″?><request>\n  <deliveryOrder>\n    <deliveryOrderCode>OC2017050500097</deliveryOrderCode>\n    <orderType>PTCK</orderType>\n    <warehouseCode>WWL</warehouseCode>\n    <createTime>2017-05-06 08:55:05.0 UTC</createTime>\n    <receiverInfo>\n      <name>ys</name>\n      <mobile>15123247205</mobile>\n      <province>四川</province>\n      <city>阿坝藏族羌族自治州1</city>\n      <detailAddress>汶川县111路111号1</detailAddress>\n    </receiverInfo>\n  </deliveryOrder>\n  <orderLines>\n    <orderLine>\n      <ownerCode>yatang</ownerCode>\n      <itemCode>123451</itemCode>\n      <itemName>威化酥饼干</itemName>\n      <planQty>2</planQty>\n    </orderLine>\n  </orderLines>\n</request>";

        System.out.println(content);


    }

}
