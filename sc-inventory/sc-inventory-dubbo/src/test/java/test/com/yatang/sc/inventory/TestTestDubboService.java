package test.com.yatang.sc.inventory;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.inventory.common.PageResult;
import com.yatang.sc.inventory.dto.*;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @描述: 库存
 * @作者: 向永红
 * @创建时间: 2017年3月31日-下午8:36:20 .
 * @版本: 1.0 .
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestTestDubboService {

    @Autowired
    ItemLocInventoryDubboService itemLocInventoryDubboService;

    @Test
    public void saleOut() {
        OrderInventoryDto orderInventoryDto = new OrderInventoryDto();

        itemLocInventoryDubboService.reserveOrder(orderInventoryDto,false);
    }

    @Test
    public void agreeSaleOut() {
        OrderInventoryDto orderInventoryDto = new OrderInventoryDto();

        itemLocInventoryDubboService.saleOutOrder(orderInventoryDto);
    }

    @Test
    public void bAgreeSaleOut() {

        OrderInventoryDto orderInventoryDto = new OrderInventoryDto();


        itemLocInventoryDubboService.orderArrived(orderInventoryDto);
    }

    @Test
    public void saleOutUndelivered() {

        OrderInventoryDto orderInventoryDto = new OrderInventoryDto();

        itemLocInventoryDubboService.orderUnArrive(orderInventoryDto);
    }

    @Test
    public void cancelSaleOrder() {

        OrderInventoryDto orderInventoryDto = new OrderInventoryDto();

        itemLocInventoryDubboService.cancelReserveOrder(orderInventoryDto);
    }


    @Test
    public void testpPurchaseStockIn() {
        ItemTranDto itemTranDto = new ItemTranDto();

        List<ItemTranDto> itemTranDtos = new ArrayList<ItemTranDto>();

        ItemLocSohDto itemLocSohDto = new ItemLocSohDto();
        itemLocSohDto.setProductCode("prodcutCode");
        itemLocSohDto.setItemId("xproddgxhhhxx12356");
//        itemLocSohDto.setItemId("10034516");
       // itemLocSohDto.setLoc("101338");
        itemLocSohDto.setLastUpdateId("123");

        double ava =5.17;
        //            double ava=10.34;
        itemLocSohDto.setUnitCost(ava);
        itemLocSohDto.setStockOnHand(10L);

        TTranDataDto tTranDataDto = new TTranDataDto();
        //  tTranDataDto.setVatRate("17");
        itemTranDto.setItemLocSoh(itemLocSohDto);
        //  itemTranDto.settTranData();
        itemTranDto.settTranData(tTranDataDto);
        itemTranDtos.add(itemTranDto);

        ItemTranDtoList data=new ItemTranDtoList();
        data.setLoc("1234");
        data.setItemTrans(itemTranDtos);
        itemLocInventoryDubboService.purchaseStockIn(data);
    }
    @Test
    public void queryItemInventoryListByParam() {
        ItemTranDto itemTranDto = new ItemTranDto();

        ItemInventoryQueryParamDto itemInventoryQueryParamDto = new ItemInventoryQueryParamDto();
        itemInventoryQueryParamDto.setProductId("10034516");
        itemInventoryQueryParamDto.setLogicWareHouseCode("1234");
        Response<List<ItemLocSohDto>> listResponse = itemLocInventoryDubboService.queryItemInventoryListByParam(itemInventoryQueryParamDto);
        System.out.println(listResponse.getResultObject());
    }


    /**
     * 分页查询
     */
    @Test
    public void queryItemInventoryListByPageQueryParam() {
        ItemTranDto itemTranDto = new ItemTranDto();

        ItemInventoryPageQueryParamDto itemInventoryQueryParamDto = new ItemInventoryPageQueryParamDto();
//        itemInventoryQueryParamDto.setProductId("10034516");
        itemInventoryQueryParamDto.setLogicWareHouseCode("1234");
        itemInventoryQueryParamDto.setPageSize(3);


        Response<PageResult<ItemLocSohDto>> resultResponse = itemLocInventoryDubboService.queryItemInventoryListByPageQueryParam(itemInventoryQueryParamDto);
        System.out.println(resultResponse.getResultObject());
    }


    @Test
    public void checkInventory(){

        ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
        itemInventoryDto.setProductId("60682");
        itemInventoryDto.setLoc("YT901");
        itemInventoryDto.setItemQty(20);
        Response<Boolean> rep = itemLocInventoryDubboService.checkInventory(itemInventoryDto);

        if(!rep.isSuccess()){
            System.out.println(rep.getErrorMessage());
        }
    }

    @Test
    public void test(){
        List<String> list = new ArrayList<String>();
        list.add("1003351");
        Response<Map<String,Long>>  repons = itemLocInventoryDubboService.queryAvailableInventoryForDS("重庆","重庆",list);
        System.out.println(JSON.toJSONString(repons));
    }



}
