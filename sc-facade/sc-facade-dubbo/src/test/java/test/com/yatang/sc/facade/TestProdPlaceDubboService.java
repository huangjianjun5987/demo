package test.com.yatang.sc.facade;

import com.busi.common.datatable.PageResult;
import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceAddDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceUpdateDto;
import com.yatang.sc.facade.dto.ProdSpAdrSearchQueryDto;
import com.yatang.sc.facade.dto.QueryRequestDto;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceQueryDubboService;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceWriteDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tangqi on 2018/1/11 15:26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/dubbo/applicationContext-provider.xml" })
public class TestProdPlaceDubboService {

    @Autowired
    private ProdPlaceQueryDubboService prodPlaceQueryDubboService;

    @Autowired
    private ProdPlaceWriteDubboService prodPlaceWriteDubboService;

    @Test
    public void addProdPlaceDistinct(){
        ProdPlaceAddDto addDto = new ProdPlaceAddDto();
        List<String> productIds = new ArrayList<>();
        productIds.add("60000117016");
        productIds.add("60001769798");
        productIds.add("xprod347510");
        addDto.setProductIds(productIds);
        addDto.setPlaceId("A001217");
        addDto.setPlaceType(2);
        addDto.setAdrSupId("4");
        addDto.setAdrSupCode("xxxx_adr");
        addDto.setAdrSupName("xx地点");
        addDto.setSupplierId("xprov003");
        addDto.setSupplierCode("xxxx_sup");
        addDto.setSupplierName("xx供应商");
        addDto.setCreateUserId("tangqi");
        addDto.setCreateDate(new Date());
        prodPlaceWriteDubboService.addProdPlace(addDto);
    }

    @Test
    public void queryPage(){
        String queryJson = "{\"$in_adrSupId\":[\"5\",\"6\",\"7\",\"8\"]}";
        QueryRequestDto dto = new QueryRequestDto();
        dto.setQueryJson(queryJson);
        Response<PageResult<List<ProdPlaceDto>>> pageResultResponse = prodPlaceQueryDubboService.queryPage(dto);
        PageResult<List<ProdPlaceDto>> resultObject = pageResultResponse.getResultObject();
        List<ProdPlaceDto> placeDtos = resultObject.getResultObject();
    }

    @Test
    public void queryDirectDeliveryProductTest() {
        // MongoDB sit
        String storeId = "A000541";
        List<String> productIds = new ArrayList<>();
        productIds.add("60000117016"); // 直送商品
        productIds.add("xprod347510"); // 直送商品
        productIds.add("xprod347511");
        productIds.add("xprod347512");
        productIds.add("60000887630");
        Response<List<ProdPlaceDto>> response = prodPlaceQueryDubboService.queryDirectDeliveryProduct(storeId, productIds);
    }

    @Test
    public void prodSpAdrSearchBoxTest() {
        ProdSpAdrSearchQueryDto queryDto = new ProdSpAdrSearchQueryDto();
        //分公司
        queryDto.setPlaceType(1);
        queryDto.setPlaceId("10003");

//        Response<com.yatang.sc.facade.common.PageResult<SpSearchBoxDto>> resultResponse = prodPlaceQueryDubboService.prodSpAdrSearchBox(queryDto);
    }


    @Test
    public void update(){
        ProdPlaceUpdateDto dto = new ProdPlaceUpdateDto();
        dto.setId("5a5eac9f8c9bebe5e836fd0c");
        dto.setLastModifyUserId("tangba");
        dto.setLastModifyDate(new Date());
        dto.setLogisticsModel(1);
        dto.setAdrSupId("8");
        dto.setSupplierId("A000346");
        Response update = prodPlaceWriteDubboService.update(dto);
    }

    @Test
    public void bulkDelete(){
        List<String> ids = new ArrayList<>();
        ids.add("5a5d53735a51bf25f8ede9fa");
        ids.add("5a5715097ed6b332809dd00e");
        Response response = prodPlaceWriteDubboService.bulkDelete(ids);
        System.out.println("批量删除结果："+response.getCode()+":"+response.getErrorMessage());
    }

    @Test
    public void distinct(){
        ProdPlaceAddDto addDto = new ProdPlaceAddDto();
        List<String> productIds = new ArrayList<>();
        productIds.add("60000117016");
        productIds.add("60001769798z");
        productIds.add("xprod347510z");
        addDto.setProductIds(productIds);
        addDto.setPlaceId("10000000");
        addDto.setPlaceType(2);
        addDto.setAdrSupId("4");
        addDto.setAdrSupCode("xxxx_adr");
        addDto.setAdrSupName("xx地点");
        addDto.setSupplierId("xprov003");
        addDto.setSupplierCode("xxxx_sup");
        addDto.setSupplierName("xx供应商");
        addDto.setCreateUserId("tangqi");
        addDto.setCreateDate(new Date());
        Response<PageResult<List<ProdPlaceDto>>> distinct = prodPlaceWriteDubboService.distinct(addDto);
        List<ProdPlaceDto> resultObject = distinct.getResultObject().getResultObject();
    }

}
