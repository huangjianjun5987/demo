package test.com.yatang.sc.facade;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.facade.dto.prod.ProdPurchaseInfoDto;
import com.yatang.sc.facade.dto.prod.ProdPurchaseModifyParamDto;
import com.yatang.sc.facade.dubboservice.ProdPurchaseWriteDubboService;
import com.yatang.sc.facade.enums.ProdAuditStatusType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 * @描述: 采购关系 dubbo测试
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/12/6 13:55
 * @版本: v1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/dubbo/applicationContext-provider.xml"})
public class TestProdPurchaseWriteDubboService {


    @Autowired
    private ProdPurchaseWriteDubboService writeDubboService;


    /**
     * 新增采购关系
     */
    @Test
    public void testAddPurchase() {

        ProdPurchaseInfoDto prodPurchaseInfoDto = new ProdPurchaseInfoDto();
        prodPurchaseInfoDto.setBranchCompanyId("10000");
        prodPurchaseInfoDto.setDistributeWarehouseId(1L);
        prodPurchaseInfoDto.setInternationalCode("6948195860136");
        prodPurchaseInfoDto.setProductId("60000117016");
        prodPurchaseInfoDto.setPurchaseInsideNumber(1);
        prodPurchaseInfoDto.setPurchasePrice(new BigDecimal(String.valueOf(3.45)));
        prodPurchaseInfoDto.setSpAdrId("9");
        prodPurchaseInfoDto.setSpId("xprov009");
        prodPurchaseInfoDto.setSupplierType(1);//非主供应商
        prodPurchaseInfoDto.setCreateUserId("10018");
        System.out.println(JSON.toJSONString(writeDubboService.addProdPurchase(prodPurchaseInfoDto)));
    }


    /**
     * 更新采购关系
     */
    @Test
    public void testUpdatePurchase() {

        ProdPurchaseInfoDto prodPurchaseInfoDto = new ProdPurchaseInfoDto();
        prodPurchaseInfoDto.setId(1789L);
        prodPurchaseInfoDto.setBranchCompanyId("10000");
        prodPurchaseInfoDto.setDistributeWarehouseId(1L);
        prodPurchaseInfoDto.setInternationalCode("6948195860136");
        prodPurchaseInfoDto.setProductId("60000117016");
        prodPurchaseInfoDto.setPurchaseInsideNumber(1);
        prodPurchaseInfoDto.setNewestPrice(new BigDecimal(String.valueOf(3.41)));
        prodPurchaseInfoDto.setSpAdrId("9");
        prodPurchaseInfoDto.setCreateUserId("add_aa");
        prodPurchaseInfoDto.setSpId("xprov009");
        prodPurchaseInfoDto.setSupplierType(0);//非主供应商
        System.out.println(JSON.toJSONString(writeDubboService.updateProdPurchase(prodPurchaseInfoDto)));
    }


    /**
     * 通过id删除采购关系
     */
    @Test
    public void testDeleteProdPurchaseById() {

        ProdPurchaseModifyParamDto prodPurchaseModifyParamDto = new ProdPurchaseModifyParamDto();
        prodPurchaseModifyParamDto.setId(1789L);
        prodPurchaseModifyParamDto.setDeleteStatus(1);
        prodPurchaseModifyParamDto.setModifyUserId("delete_user_id");
        prodPurchaseModifyParamDto.setProductId("60000117016");
        writeDubboService.deleteProdPurchaseById(prodPurchaseModifyParamDto);
    }


    /**
     * 更新供应商状态
     */
    @Test
    public void testChangeSupplierType() {
        ProdPurchaseModifyParamDto prodPurchaseModifyParamDto = new ProdPurchaseModifyParamDto();
        prodPurchaseModifyParamDto.setId(1789L);
        prodPurchaseModifyParamDto.setModifyUserId("change_supplier_type_user_id");
        prodPurchaseModifyParamDto.setSupplierType(0);
        writeDubboService.changeSupplierType(prodPurchaseModifyParamDto);
    }

    //

    /**
     * 更新商品的启用禁用状态
     */
    @Test
    public void testChangeProPurchaseStatus() {
        ProdPurchaseModifyParamDto prodPurchaseModifyParamDto = new ProdPurchaseModifyParamDto();
        prodPurchaseModifyParamDto.setId(1789L);
        prodPurchaseModifyParamDto.setModifyUserId("change_status_id");
        prodPurchaseModifyParamDto.setStatus(0);
        prodPurchaseModifyParamDto.setProductId("60000117016");
        writeDubboService.changeProPurchaseStatus(prodPurchaseModifyParamDto);
    }


    /**
     * 通过工作流审批更新采购关系状态
     */
    @Test
    public void testAuditProdPurchase() {
        writeDubboService.updateProdPurchaseAuditStatus(1789l, true, "10018");
    }

    /**
     * todo 批量更新
     */
    @Test
    public void testBatchChangeProPurchaseStatus() {
//        ProdPurchaseModifyParamDto prodPurchaseModifyParamDto = new ProdPurchaseModifyParamDto();
//        prodPurchaseModifyParamDto.setId(1789L);
//        prodPurchaseModifyParamDto.setModifyUserId("change_status_id");
//        prodPurchaseModifyParamDto.setStatus(0);
//        prodPurchaseModifyParamDto.setProductId("60000117016");
//        writeDubboService.changeProPurchaseStatus(prodPurchaseModifyParamDto);
    }


}
