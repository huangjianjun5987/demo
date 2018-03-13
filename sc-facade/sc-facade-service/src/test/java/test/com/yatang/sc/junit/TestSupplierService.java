package test.com.yatang.sc.junit;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.yatang.sc.facade.domain.SpSearchBoxPo;
import com.yatang.sc.facade.domain.SupplierAuditInfoPo;
import com.yatang.sc.facade.domain.SupplierBankInfoPo;
import com.yatang.sc.facade.domain.SupplierBasicInfoPo;
import com.yatang.sc.facade.domain.SupplierEnterQueryParamPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.domain.SupplierOperTaxInfoPo;
import com.yatang.sc.facade.domain.SupplierlicenseInfoPo;
import com.yatang.sc.facade.service.SupplierService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/applicationContext*.xml" })
public class TestSupplierService {

	protected final Log		log	= LogFactory.getLog(this.getClass());

	@Autowired
	private SupplierService	service;



	@Before
	public void setUp() throws Exception {
	}



	@Test
	public void testQueryById() {
		SupplierInfoPo po = service.queryById("SP00001");
		System.out.println(po.toString());
	}



	@Test
	public void testQuerySettledRequestListPage() {
		SupplierEnterQueryParamPo supplierEnterQueryParamPo = new SupplierEnterQueryParamPo();
		supplierEnterQueryParamPo.setPageNum(1);
		supplierEnterQueryParamPo.setPageSize(5);
		PageInfo<SupplierInfoPo> pageInfo= service.querySettledRequestListPage(supplierEnterQueryParamPo);
		System.out.println(pageInfo.getList().size());
	}
	
	@Test
	public void testInsertSupplier() {
		SupplierInfoPo record = new SupplierInfoPo();
		record.setBasicId(3);
		record.setOperatTaxatId(3);
		record.setBankId(3);
		record.setLicenseId(3);
		record.setStatus(2);
		service.insertSupplier(record);
	}
	
	@Test
	public void testInsertBasicInfo() {
		SupplierBasicInfoPo record = new SupplierBasicInfoPo();
		record.setCompanyName("成都双流造纸有限公司");;
		record.setSpNo("SP002");
		service.insertBasicInfo(record);
	}
	@Test
	public void testInsertBankInfo() {
		SupplierBankInfoPo record = new SupplierBankInfoPo();
		record.setAccountName("田七");
		record.setOpenBank("农业银行");
		record.setBankAccount("62265523258512856");
		record.setBankLocProvince("001");
		record.setBankLocCity("00101");
		record.setBankLocCounty("00101001");
		record.setModifyId(4);
		record.setStatus(0);
		service.insertBankInfo(record);
	}
	@Test
	public void testInsertOperTaxInfo() {
		SupplierOperTaxInfoPo record = new SupplierOperTaxInfoPo();
		record.setCompanyDetailAddress("四川省成都市青白江区×××路××号");
		record.setQualityIdentification("62265523258512856");
		record.setCompanyLocProvince("001");
		record.setCompanyLocCity("00101");
		record.setCompanyLocCounty("00101001");
		record.setModifyId(1);
		record.setRegistrationCertificate("/image/xsdsf.jpg");
		record.setGeneralTaxpayerQualifiCerti("sdg234235");
		service.insertOperTaxInfo(record);
	}
	
	@Test
	public void testInsertLicenseInfo() {
		SupplierlicenseInfoPo record = new SupplierlicenseInfoPo();
		record.setCompanyName("成都双流造纸有限公司");
		record.setRegistLicenceNumber("52352346346436");;
		record.setLegalRepresentative("杜延金");;
		record.setLegalRepreCardNum("321522197505061123");;
		record.setLegalRepreCardPic1("/image/00101001.jpg");
		record.setLegalRepreCardPic2("/image/00101001.jpg");
		record.setLicenseLocProvince("001");
		record.setLicenseLocCity("00101");
		record.setLicenseLocCounty("00101001");
		record.setModifyId(1);
		record.setRegisteredCapital(new BigDecimal(5000));
		record.setEstablishDate(new Date());
		record.setPerpetualManagement(0);
		record.setRegistLicencePic("/image/xx.jpg");
		service.insertlicenseInfo(record);
	}
	

	/**
	 * 测试供应商入驻信息录入
	 */
	@Test
	public void testInsertSupplierSettlementInfo() {
		SupplierInfoPo record = new SupplierInfoPo();
		// 基本信息
		SupplierBasicInfoPo basicInfoPo = new SupplierBasicInfoPo();
		basicInfoPo.setCompanyName("成都杨氏双流造纸有限公司1");
		basicInfoPo.setSpNo("SP222");
		record.setSupplierBasicInfo(basicInfoPo);


		//公司经营及税务信息
		SupplierOperTaxInfoPo taxInfoPo = new SupplierOperTaxInfoPo();
		taxInfoPo.setCompanyDetailAddress("四川省成都市青白江区×××路××号1");
		taxInfoPo.setQualityIdentification("62265523258512856");
		taxInfoPo.setCompanyLocProvince("001");
		taxInfoPo.setCompanyLocCity("00101");
		taxInfoPo.setCompanyLocCounty("00101001");
//		taxInfoPo.setModifyId(1);
		taxInfoPo.setRegistrationCertificate("/image/xsdsf.jpg");
		taxInfoPo.setGeneralTaxpayerQualifiCerti("sdg234235");
		record.setSupplierOperTaxInfo(taxInfoPo);

	   //银行信息
		SupplierBankInfoPo bankInfoPo = new SupplierBankInfoPo();
		bankInfoPo.setAccountName("杨爽1");
		bankInfoPo.setOpenBank("农业银行1");
		bankInfoPo.setBankAccount("62265523258512877");
		bankInfoPo.setBankLocProvince("0023");
		bankInfoPo.setBankLocCity("00103");
		bankInfoPo.setBankLocCounty("00101003");
		bankInfoPo.setStatus(0);
		record.setSupplierBankInfo(bankInfoPo);

		//公司营业执照信息(副本)
		SupplierlicenseInfoPo supplierlicenseInfoPo = new SupplierlicenseInfoPo();
		supplierlicenseInfoPo.setCompanyName("成都双流杨氏造纸有限公司1");
		supplierlicenseInfoPo.setRegistLicenceNumber("52352346346436");
		supplierlicenseInfoPo.setLegalRepresentative("杜延金1");
		supplierlicenseInfoPo.setLegalRepreCardNum("32152219750506113231");
		supplierlicenseInfoPo.setLegalRepreCardPic1("/image/00101001.jpg");
		supplierlicenseInfoPo.setLegalRepreCardPic2("/image/00101001.jpg");
		supplierlicenseInfoPo.setLicenseLocProvince("101");
		supplierlicenseInfoPo.setLicenseLocCity("10101");
		supplierlicenseInfoPo.setLicenseLocCounty("10101001");
		supplierlicenseInfoPo.setRegisteredCapital(new BigDecimal(5001F));
		supplierlicenseInfoPo.setEstablishDate(new Date());
		supplierlicenseInfoPo.setPerpetualManagement(0);
		supplierlicenseInfoPo.setStatus(0);
		supplierlicenseInfoPo.setRegistLicencePic("/image/xx.jpg");
		record.setSupplierlicenseInfo(supplierlicenseInfoPo);
		service.insertSupplierInfo(record);
	}
    /**
     * 测试审核录入的信息
     */
    @Test
    public void testInsertAutionInfo() {
        SupplierAuditInfoPo supplierAuditInfoPo = new SupplierAuditInfoPo();
		supplierAuditInfoPo.setId("SP00001");
		supplierAuditInfoPo.setGuaranteeMoney(new BigDecimal(500));
		supplierAuditInfoPo.setSettlementAccountType(1);
		supplierAuditInfoPo.setRebateRate(BigDecimal.valueOf(5.2));
		supplierAuditInfoPo.setStatus(100);
		supplierAuditInfoPo.setStoreContractDate(500);
		supplierAuditInfoPo.setSettlementPeriod(12);
		Boolean aBoolean = service.insertSupplierAuditInfo(supplierAuditInfoPo);
		System.out.println(aBoolean);
	}
    
    /**
     * 测试获取编码值
     */
    @Test
    public void testGetSupplierNo() {
//    	String no = service.getSupplierNo("SP");
//    	System.out.println(no);
    }
    
    /**
     * 测试获取编码值
     */
    @Test
    public void testSupplierSearchBox() {
    	PageInfo<SpSearchBoxPo> pageInfo = service.supplierSearchBox("1000", 1, 5);
    	for (SpSearchBoxPo bean : pageInfo.getList()) {
    		System.out.println(bean.toString());
		}
    }

}
