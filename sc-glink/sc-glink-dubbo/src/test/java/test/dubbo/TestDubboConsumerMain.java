package test.dubbo;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.glink.dto.CancelAllOrdersDto;
import com.yatang.sc.glink.dto.CancelAllOrdersRepDto;
import com.yatang.sc.glink.dto.CancelOrdersDto;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import com.yatang.sc.kidd.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-consumer.xml"})
public class TestDubboConsumerMain {

   /* @Autowired
    private EntryOrderService entryOrderService;*/

    @Autowired
    private ProductService productService;

    @Test
    public void test() {
        System.out.println("--- Consume Start ---");
        CancelAllOrdersDto cancelAllOrdersDto = new CancelAllOrdersDto();
        CancelOrdersDto cancelOrdersDto = new CancelOrdersDto();
        List<CancelOrdersDto> cancelOrdersList = new ArrayList<CancelOrdersDto>();

        cancelAllOrdersDto.setType(2);


        cancelOrdersDto.setOrderCode("YT003");
        cancelOrdersDto.setOrderType("CGRK");
        cancelOrdersDto.setOrgCode("10089");
        cancelOrdersDto.setProjectCode("YTXC-CD");

        cancelOrdersList.add(cancelOrdersDto);
        cancelAllOrdersDto.setOrders(cancelOrdersList);

       // GlinkResponseDto<CancelAllOrdersRepDto> response = entryOrderService.close(cancelAllOrdersDto);

       // System.out.println(response.getData().getTotal());

        //System.out.println(JSON.toJSONString(response));
        System.out.println("--- Consume End ---");

    }

//    @Test
//    public void testCommodity() {
//        System.out.println("--- Consume Started ---");
////        CommodityDto params=new CommodityDto();
////        params.setType("3");
////        List<productSynchronizeDto> items = new ArrayList<productSynchronizeDto>();
//        ProductSynchronizeDto productSynchronizeDto = new ProductSynchronizeDto();
//        productSynchronizeDto.setItemCode("prod618");
////        productSynchronizeDto.setItemCodeWms("prod618WMS");
//        productSynchronizeDto.setItemName("Assam");
//        productSynchronizeDto.setOwnerCode("yatang");
//        productSynchronizeDto.setWarehouseCode("WWL");
//        productSynchronizeDto.setBarCode("79123456789");
//        productSynchronizeDto.setItemType("ZC");
//        productSynchronizeDto.setShelfLife(365);
//        productSynchronizeDto.setRejectLifecycle(30);
//        productSynchronizeDto.setLockupLifecycle(30);
//        productSynchronizeDto.setAdventLifecycle(30);
//        productSynchronizeDto.setActionType("1");
//        productSynchronizeDto.setSkuProperty("");
//        productSynchronizeDto.setLength(10);
//        productSynchronizeDto.setWidth(5);
//        productSynchronizeDto.setHeight(20);
//        productSynchronizeDto.setGrossWeight(2);
////        productSynchronizeDto.setPcs("200ml*20"); TODO
//        productSynchronizeDto.setStockUnit("ƿ");
//        productSynchronizeDto.setBrandCode("dpdm");
//        productSynchronizeDto.setBrandName("测试");
//        productSynchronizeDto.setOriginAddress("测试");
//        productSynchronizeDto.setCategoryId("测试");
//        productSynchronizeDto.setCategoryName("测试");
//        productSynchronizeDto.setIsShelfLifeMgmt("Y");
//        productSynchronizeDto.setIsSNMgmt("N");
//        productSynchronizeDto.setIsBatchMgmt("N");
//        productSynchronizeDto.setIsFragile("N");
//        productSynchronizeDto.setIsValid("N");
//        productSynchronizeDto.setIsSku("N");
//        productSynchronizeDto.setIsHazardous("N");
////        productSynchronizeDto.setUpdateTimeERP(new Date());
////        productSynchronizeDto.setCreateTimeERP(new Date());
//        productSynchronizeDto.setSupplierCode("123");
//        productSynchronizeDto.setSupplierName("测试");
//        productSynchronizeDto.setSafetyStock(10000000);
//        productSynchronizeDto.setColor("red");
//        productSynchronizeDto.setSize("100");
//        productSynchronizeDto.setNetWeight(1);
//        productSynchronizeDto.setVolume(250);
//        productSynchronizeDto.setRemark("qwertyui");
//        productSynchronizeDto.setPricingCategory("测试");
//        productSynchronizeDto.setPurchasePrice(2);
//        productSynchronizeDto.setCostPrice(1);
//        productSynchronizeDto.setTagPrice(5);
//        productSynchronizeDto.setRetailPrice(3);
//        productSynchronizeDto.setPackCode("986586");
//        productSynchronizeDto.setSeasonCode("252");
//        productSynchronizeDto.setSeasonName("测试");
//        productSynchronizeDto.setApprovalNumber("9696969");
//        productSynchronizeDto.setTitle("测试");
////        productSynchronizeDto.setPackageMaterial("测试");
//        productSynchronizeDto.setShortName("ww");
//        productSynchronizeDto.setProductDate(new Date());
//        productSynchronizeDto.setExpireDate(new Date());
//        productSynchronizeDto.setBatchRemark("beizhushsa");
//        productSynchronizeDto.setBatchCode("858585");
//        productSynchronizeDto.setPicUrl("pao.jpg");
//        productSynchronizeDto.setEnglishName("name");
////        items.add(productSynchronizeDto);
////        params.setItems(items);
//        Response<String> response = productService.synchronizeProduct(productSynchronizeDto);
//        System.out.println(JSON.toJSONString(response));
//        System.out.println("--- Consume Ended ---");
//    }
}
