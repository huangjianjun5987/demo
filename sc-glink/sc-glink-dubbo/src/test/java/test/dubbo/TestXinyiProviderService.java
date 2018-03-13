package test.dubbo;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.xinyi.dto.XinyiConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.busi.kidd.KiddException;
import com.busi.kidd.serialize.xml.XmlDataBean;
import com.busi.kidd.serialize.xml.XmlDataSerialization;
import com.yatang.sc.kidd.dto.ProviderRequestDto;
import com.yatang.sc.kidd.service.KiddFacadeServiceImpl;
import com.yatang.sc.xinyi.dto.orderNotice.XinyiOrderNoticeRequestInfoDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiPurchaseOrderConfirmRequestDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderConfirmRequestDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext-test.xml"})
public class TestXinyiProviderService {

    @Resource(name ="kiddFacadeService")
    private KiddFacadeService kiddFacadeService;
    @Resource(name ="xmlSerialization")
    private XmlDataSerialization xmlDataSerialization;

    @Test
    public void testSaleOrderConfirm() throws KiddException {
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
        xmlDataSerialization.deserialize(dataBean);
        SaleOrderConfirmRequestDto noticeRequestInfoDto = (SaleOrderConfirmRequestDto) dataBean.getData();

        ProviderRequestDto providerDto = new ProviderRequestDto();
        providerDto.setPayload(noticeRequestInfoDto);
        kiddFacadeService.send(providerDto);

    }


    @Test
    public void testAccept() throws KiddException {
        String method = "taobao.qimen.orderprocess.report";
        String xml = "<request><order><orderCode>100042017101200005</orderCode><orderId>100042017101200005</orderId><orderType>PTCK</orderType><warehouseCode>WLL</warehouseCode></order><process><processStatus>ACCEPT</processStatus><operatorCode></operatorCode><operatorName>reven</operatorName><operateTime>2017-10-12 10:13:06</operateTime><operateInfo>保存退供单数据成功!</operateInfo><remark></remark></process></request>";
        XmlDataBean dataBean = new XmlDataBean();
        dataBean.setRootAlias("request");
        dataBean.setXmlStr(xml);
        dataBean.setDataClass(XinyiConstants.provders.get(method));
        xmlDataSerialization.deserialize(dataBean);

        ProviderRequestDto providerDto = new ProviderRequestDto();
        providerDto.setApiMethod(method);
        providerDto.setPayload(dataBean.getData());
        System.out.println(JSON.toJSON(providerDto));
//        kiddFacadeService.send(providerDto);
    }

    /**
     * 测试入库单确认接口
     * @throws KiddException
     */
    @Test
    public void testEntryOrder() throws KiddException {
    String xml="<request>\n" +
            "  <entryOrder>\n" +
            "    <entryOrderCode>17101100010</entryOrderCode>\n" +
            "    <ownerCode>20042</ownerCode>\n" +
            "    <warehouseCode>WLL</warehouseCode>\n" +
            "    <entryOrderId>17101100010</entryOrderId>\n" +
            "    <entryOrderType>CGRK</entryOrderType>\n" +
            "    <outBizCode>6_3248</outBizCode>\n" +
            "    <confirmType>0</confirmType>\n" +
            "    <status>FULFILLED</status>\n" +
            "    <operateTime>2017-10-11 00:00:00</operateTime>\n" +
            "    <remark/>\n" +
            "  </entryOrder>\n" +
            "  <orderLines>\n" +
            "    <orderLine>\n" +
            "      <batchs>\n" +
            "        <batch>\n" +
            "          <batchCode/>\n" +
            "          <expireDate>2017-10-22</expireDate>\n" +
            "          <produceCode>Z020202001</produceCode>\n" +
            "          <inventoryType>ZP</inventoryType>\n" +
            "          <actualQty>1000</actualQty>\n" +
            "          <productDate>2017-10-10</productDate>\n" +
            "        </batch>\n" +
            "      </batchs>\n" +
            "      <outBizCode>6_3248</outBizCode>\n" +
            "      <orderLineNo>273</orderLineNo>\n" +
            "      <ownerCode>20042</ownerCode>\n" +
            "      <itemCode>1001866</itemCode>\n" +
            "      <itemId>1001866</itemId>\n" +
            "      <batchCode/>\n" +
            "      <produceCode>Z020202001</produceCode>\n" +
            "      <actualQty>1000</actualQty>\n" +
            "    </orderLine>\n" +
            "  </orderLines>\n" +
            "</request>";
        XmlDataBean dataBean = new XmlDataBean();
        dataBean.setRootAlias("request");
        dataBean.setXmlStr(xml);
        dataBean.setDataClass(XinyiPurchaseOrderConfirmRequestDto.class);
        xmlDataSerialization.deserialize(dataBean);
        XinyiPurchaseOrderConfirmRequestDto xinyiPurchaseOrderConfirmRequestDto = (XinyiPurchaseOrderConfirmRequestDto) dataBean.getData();

        ProviderRequestDto providerDto = new ProviderRequestDto();
        providerDto.setPayload(xinyiPurchaseOrderConfirmRequestDto);
        kiddFacadeService.send(providerDto);
    }

}
