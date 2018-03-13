package test.dubbo;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.google.common.collect.Lists;
import com.yatang.sc.juban.dto.im.JubanInventoryQueryCriteriaDto;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryCriteriaDto;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryDto;
import com.yatang.sc.kidd.dto.product.KiddInventoryQueryResponseDto;
import com.yatang.sc.kidd.dto.product.ProductSynchronizeDto;
import com.yatang.sc.kidd.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-consumer.xml"})
public class TestProduct {

    @Resource(name = "kiddProductService")
    private ProductService productService;

    @Test
    public void testProductSynchronize() {
        System.out.println("--- Consume Started ---");

        ProductSynchronizeDto dto = new ProductSynchronizeDto();
        dto.setItemCode("prod619");
        dto.setItemName("测试商品619");
        dto.setWarehouseCode(TestConstants.GLINK_WAREHOUSE_CODE_OF_PRODUCT);
        dto.setOwnerCode(TestConstants.GLINK_OWNER_CODE_OF_PRODUCT);
        dto.setBarCode("79123456789");
        dto.setItemType("ZC");
        dto.setShelfLife(365);
        dto.setRejectLifecycle(30);
        dto.setLockupLifecycle(30);
        dto.setAdventLifecycle(30);
        dto.setActionType("1");
        dto.setSkuProperty("");
        dto.setLength(10);
        dto.setWidth(5);
        dto.setHeight(20);
        dto.setGrossWeight(2);
        dto.setPcs("1*1*1");
        dto.setStockUnit("ƿ");
        dto.setBrandCode("dpdm");
        dto.setBrandName("测试");
        dto.setOriginAddress("测试");
        dto.setCategoryId("测试");
        dto.setCategoryName("测试");
        dto.setIsShelfLifeMgmt("Y");
        dto.setIsFragile("N");
        dto.setIsValid("N");
        dto.setUpdateTime(new Date());
        dto.setCreateTime(new Date());
        dto.setNetWeight(1);
        dto.setBatchCode("858585");
        dto.setPicUrl("pao.jpg");

        Response<String> response = productService.synchronizeProduct(dto);
        System.out.println(JSON.toJSONString(response));
        System.out.println("--- Consume Ended ---");
    }

    @Test
    public void testInventoryQueryForJuban() {
        System.out.println("--- Consume Started ---");

        KiddInventoryQueryDto dto = new KiddInventoryQueryDto();

        KiddInventoryQueryCriteriaDto criteria = new KiddInventoryQueryCriteriaDto();
        criteria.setItemCode("prod619");
        criteria.setWarehouseCode(TestConstants.JUBAN_WAREHOUSE_CODE);
        criteria.setOwnerCode(TestConstants.JUBAN_OWNER_CODE);

        dto.setCriteriaList(Lists.newArrayList(criteria));

        Response<KiddInventoryQueryResponseDto> response = productService.inventoryQuery(dto);
        System.out.println(JSON.toJSONString(response));
        System.out.println("--- Consume Ended ---");
    }

}
