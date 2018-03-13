package test.com.yatang.sc.facade;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.facade.common.PageResult;
import com.yatang.sc.facade.dto.prod.ProdPurchaseExtDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.dto.prod.ProdPurchasePriceAuditDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseQueryParamDto;
import com.yatang.sc.facade.dubboservice.ProdPriceChangeQueryDubboService;
import com.yatang.sc.facade.dubboservice.ProdPurchaseQueryDubboService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @描述: 采购关系 dubbo测试
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 13:55
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
@Slf4j
public class TestProdPurchaseQueryDubboService {


    @Autowired
    private ProdPurchaseQueryDubboService queryDubboService;




    /**
     * 根据采购关系主键查询商品采购关系主键
     */
    @Test
    public void testGetProdPurchaseById() {
        Response<ProdPurchaseExtDto> prodPurchaseById = queryDubboService.getProdPurchaseById(1789L);
        System.out.println(prodPurchaseById);

    }


    /**
     * 根据采购关系主键查询商品采购关系主键
     */
    @Test
    public void testQueryProdPurchaseExtByCondition() {

        ProdPurchaseQueryParamDto purchasePriceQueryParamDto = new ProdPurchaseQueryParamDto();

        purchasePriceQueryParamDto.setProductId("60000117016");
        purchasePriceQueryParamDto.setBranchCompanyId("10000");
        purchasePriceQueryParamDto.setPageNum(1);
        purchasePriceQueryParamDto.setPageSize(20);
        purchasePriceQueryParamDto.setAuditStatus(1);
        Response<PageResult<ProdPurchaseExtDto>> pageResultResponse = queryDubboService.queryProdPurchaseExtByCondition(purchasePriceQueryParamDto);
        log.info("查询采购关系:{}", JSON.toJSONString(pageResultResponse));
    }


    /**
     * 根据条件查询商品采购关系
     */
    @Test
    public void testGetProdPurchaseByParam() {

        ProdPurchaseQueryParamDto purchasePriceQueryParamDto = new ProdPurchaseQueryParamDto();

     /*   purchasePriceQueryParamDto.setProductId("60000117016");
        purchasePriceQueryParamDto.setBranchCompanyId("10000");
        purchasePriceQueryParamDto.setPageNum(1);
        purchasePriceQueryParamDto.setPageSize(20);*/
        Response<ProdPurchaseInfoDto> prodPurchaseByParam = queryDubboService.getProdPurchaseByParam(purchasePriceQueryParamDto);
        log.info("查询采购关系:{}", JSON.toJSONString(prodPurchaseByParam));
    }


    /**
     * 根据条件查询商品采购关系List
     */
    @Test
    public void testGetProdPurchaseListByParam() {

        ProdPurchaseQueryParamDto purchasePriceQueryParamDto = new ProdPurchaseQueryParamDto();

        purchasePriceQueryParamDto.setProductId("60000117016");
        purchasePriceQueryParamDto.setBranchCompanyId("10000");
        Response<List<ProdPurchaseInfoDto>> listResponse = queryDubboService.queryProdPurchaseListByParam(purchasePriceQueryParamDto);
        log.info("查询采购关系:{}", JSON.toJSONString(listResponse));
    }


    @Test
    public void testGetProdPurchasePriceAuditDtoById() {

        Response<ProdPurchasePriceAuditDto> prodPurchasePriceAuditDtoResponse = queryDubboService.getProdPurchasePriceAuditDtoById(1775L);
        log.info("查询采购关系:{}", JSON.toJSONString(prodPurchasePriceAuditDtoResponse));

    }


}
