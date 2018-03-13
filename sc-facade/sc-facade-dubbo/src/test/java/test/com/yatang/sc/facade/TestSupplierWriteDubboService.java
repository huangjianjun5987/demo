package test.com.yatang.sc.facade;

import java.math.BigDecimal;
import java.util.Date;

import com.busi.common.resp.Response;
import com.yatang.sc.facade.dto.supplier.SupplierSaleRegionDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yatang.sc.facade.dto.supplier.SupplierBankInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierBasicInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierOperTaxInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierlicenseInfoDto;
import com.yatang.sc.facade.dubboservice.SupplierWriteDubboService;

/**
 * @描述: 供应商只读服务测试类
 * @作者: huangjianjun
 * @创建时间: 2017年3月31日-下午8:36:20 .
 * @版本: 1.0 .
 * @param <T>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:test/applicationContext-test.xml" })
public class TestSupplierWriteDubboService {
		@Autowired
		private SupplierWriteDubboService service;
	
	
		@Test
		public void testInsertSupplier() {
			SupplierInfoDto record = new SupplierInfoDto();
			record.setBasicId(1);
			record.setOperatTaxatId(1);
			record.setBankId(1);
			record.setLicenseId(1);
			record.setSaleRegionId(2);
			record.setStatus(1);
			record.setCreateUser("admin");
			record.setModifyUser("admin");
			service.insertSupplier(record);
		}
		
		@Test
		public void testInsertBasicInfo() {
			SupplierBasicInfoDto record = new SupplierBasicInfoDto();
			record.setCompanyName("伊利食品有限公司");;
			record.setSpNo("10005");
			record.setGrade(1);
			record.setSettledTime(new Date());
			service.insertBasicInfo(record);
		}
		
		@Test
		public void testInsertBankInfo() {
			SupplierBankInfoDto record = new SupplierBankInfoDto();
			record.setAccountName("LVl");
			record.setOpenBank("哈尔滨银行");
			record.setBankAccount("62266723258522856");
			record.setBankLocProvinceCode("002");
			record.setBankLocProvince("湖南省");
			record.setBankLocCityCode("00202");
			record.setBankLocCity("长沙市");
			record.setBankLocCountyCode("00202002");
			record.setBankLocCounty("岳麓区");
			record.setInvoiceHead("伊利食品有限公司");
//			record.setModifyId(4);
			service.insertBankInfo(record);
		}
		
		@Test
		public void testInsertOperTaxInfo() {
			SupplierOperTaxInfoDto record = new SupplierOperTaxInfoDto();
			record.setCompanyDetailAddress("湖南省长沙市岳麓区×××路××号");
			record.setQualityIdentification("56756823258522856");
			record.setCompanyLocProvinceCode("002");
			record.setCompanyLocProvince("湖南省");
			record.setCompanyLocCityCode("00202");
			record.setCompanyLocCity("长沙市");
			record.setCompanyLocCountyCode("00202002");
			record.setCompanyLocCounty("岳麓区");
			record.setModifyId(2);
			record.setRegistrationCertificate("/image/xsdsf.jpg");
			record.setGeneralTaxpayerQualifiCerti("sdg234235");
			service.insertOperTaxInfo(record);
		}
		
		@Test
		public void testInsertLicenseInfo() {
			SupplierlicenseInfoDto record = new SupplierlicenseInfoDto();
			record.setCompanyName("伊利食品有限公司");
			record.setRegistLicenceNumber("52352346346436");;
			record.setLegalRepresentative("杜延金");;
			record.setLegalRepreCardNum("322522297505061123");;
			record.setLegalRepreCardPic1("/image/00101001.jpg");
			record.setLegalRepreCardPic2("/image/00101001.jpg");
			record.setLicenseLocProvince("001");
			record.setLicenseLocCity("00101");
			record.setLicenseLocCounty("00101001");
			record.setModifyId(1);
			record.setRegisteredCapital(new BigDecimal(5000));
			record.setEstablishDate("1466265600");
			record.setPerpetualManagement(0);
			record.setRegistLicencePic("/image/xx.jpg");
			service.insertlicenseInfo(record);
		}
		

	@Test
	/**
	 * 测试供应商状态的修改
	 * */
	public void testUpdateSupplierStatus(){
		SupplierInfoDto record = new SupplierInfoDto();
		record.setId("SP00007");
		record.setStatus(3);
		service.updateSupplier(record);
	}




//	/**
//	 * 审核招商入驻联系人修改后内容
//	 */
//	@Test
//	public void testApproveSupplierSettledCont() {
//		SupplierSettledContInfoDto record = new SupplierSettledContInfoDto();
//		record.setId(25);
//		record.setSpId("xprov127");
//		record.setStatus(1);
//		record.setFailedReason("xxx111");
//		service.updateSettledContApproval(record,26);
//	}
//
//	@Test
//	public void testApproveEmerCont() {
//		SupplierEmerContInfoDto record = new SupplierEmerContInfoDto();
//		record.setId(25);
//		record.setSpId("xprov127");
//		record.setStatus(1);
//		record.setFailedReason("xxx111");
//		service.updateEmerContApproval(record,26);
//	}
//
//	@Test
//	public void testApproveOrgCodeInfo() {
//		SupplierOrgCodeInfoDto record = new SupplierOrgCodeInfoDto();
//		record.setId(99);
//		record.setSpId("xprov127");
//		record.setStatus(1);
//		service.updateOrgCodeApproval(record,101);
//	}

	@Test
	public void testApproveLicenseInfo() {
		SupplierlicenseInfoDto record = new SupplierlicenseInfoDto();
		record.setId(49);
		record.setSpId("xprov127");
		record.setStatus(1);
		//record.setFailedReason("xxx111");
		service.updateLicenseApproval(record,50);
	}

	@Test
	public void testApproveBankInfo() {
		SupplierBankInfoDto record = new SupplierBankInfoDto();
		record.setId(24);
		record.setSpId("xprov127");
		record.setStatus(1);
		//record.setFailedReason("xxx111");
		service.updateBankApproval(record,27);
	}

	@Test
	public void testApproveTaxInfo() {
		SupplierOperTaxInfoDto record = new SupplierOperTaxInfoDto();
		record.setId(39);
		record.setSpId("xprov127");
		record.setStatus(1);
		//record.setFailedReason("xxx111");
		service.updateSupplierTaxApproval(record,40);
	}

	/**
	 * 审核供应商
	 */
	@Test
	public void testApprove(){
		SupplierInfoDto record = new SupplierInfoDto();
		record.setId("xprov138");
		record.setStatus(3);
		record.setFailedReason("xxxxxxxx");
		service.updateSupplier(record);
	}

	@Test
	public void testUpdate(){
		SupplierInfoDto infoDto = new SupplierInfoDto();

		// 已审核的供应商修改后保存草稿
		String name = "测试7";
		infoDto.setCommitType(1);
		infoDto.setId("xprov981");
		infoDto.setStatus(2);

		SupplierBasicInfoDto basicInfoDto = new SupplierBasicInfoDto();
		basicInfoDto.setId(744);
		basicInfoDto.setCompanyName("test03");
		basicInfoDto.setSpNo("10490");
		basicInfoDto.setGrade(1);
		basicInfoDto.setSettledTime(new Date());
		basicInfoDto.setStatus(1);
		infoDto.setSupplierBasicInfo(basicInfoDto);

		SupplierOperTaxInfoDto taxInfoDto = new SupplierOperTaxInfoDto();
		taxInfoDto.setId(746);
		taxInfoDto.setCompanyLocProvince("四川");
		taxInfoDto.setCompanyLocProvinceCode("510000");
		taxInfoDto.setCompanyLocCity("成都");
		taxInfoDto.setCompanyLocCityCode("510100");
		taxInfoDto.setCompanyLocCounty("双流区");
		taxInfoDto.setCompanyLocCountyCode("510116");
		taxInfoDto.setCompanyDetailAddress("十陵镇来龙村20组362号");
		taxInfoDto.setRegistrationCertificate("/group1/M00/01/36/rB4KPFmT_wGAPEOMAAAl9GLuGY4320.png");
		taxInfoDto.setRegCerExpiringDate("1815580800");
		taxInfoDto.setQualityIdentification("62265523258512856");
		taxInfoDto.setQuaIdeExpiringDate("1878739200");
		taxInfoDto.setFoodBusinessLicense("/group1/M00/01/37/rB4KPVmVPJOACBdpAAvqH_kipG8360.png");
		taxInfoDto.setBusinessLicenseExpiringDate("1878739200");
		taxInfoDto.setGeneralTaxpayerQualifiCerti("/group2/M00/00/24/rB4KPFmVPJ2Aen_dAAkVVGMybN8458.png");
		taxInfoDto.setTaxpayerCertExpiringDate("2068041600");
		taxInfoDto.setStatus(1);
		infoDto.setSupplierOperTaxInfo(taxInfoDto);

		SupplierBankInfoDto bankInfoDto = new SupplierBankInfoDto();
		bankInfoDto.setId(744);
		bankInfoDto.setAccountName("testBank");
		bankInfoDto.setOpenBank("农业银行天府五街支行");
		bankInfoDto.setBankAccount("62265523258512888");
		bankInfoDto.setBankAccountLicense("/image/asdfg.jpg");
		bankInfoDto.setBankLocProvince("四川");
		bankInfoDto.setBankLocCity("眉山");
		bankInfoDto.setBankLocCounty("仁寿县");
		bankInfoDto.setBankLocProvinceCode("510000");
		bankInfoDto.setBankLocCityCode("511400");
		bankInfoDto.setBankLocCountyCode("511421");
		bankInfoDto.setInvoiceHead("testBank");
		bankInfoDto.setStatus(1);
		infoDto.setSupplierBankInfo(bankInfoDto);

		SupplierlicenseInfoDto licenseDto = new SupplierlicenseInfoDto();
		licenseDto.setId(744);
		licenseDto.setCompanyName("TestLicense");
		licenseDto.setRegistLicenceNumber("52352346346456");
		licenseDto.setLegalRepresentative("杜延金12");
		licenseDto.setLegalRepreCardNum("32152219750506113231");
		licenseDto.setLegalRepreCardPic1("/group1/M00/01/72/rB4KPFncMP2ASLAYAAFm0KzNDKg584.png");
		licenseDto.setLegalRepreCardPic2("/group1/M00/01/72/rB4KPFncMP2ASLAYAAFm0KzNDKg588.png");
		licenseDto.setLicenseLocProvince("四川");
		licenseDto.setLicenseLocCity("眉山");
		licenseDto.setLicenseLocCounty("仁寿县");
		licenseDto.setLicenseLocProvinceCode("510000");
		licenseDto.setLicenseLocCityCode("510000");
		licenseDto.setLicenseLocCountyCode("511421");
		licenseDto.setLicenseAddress("广东省深圳市宝安区");
		licenseDto.setEstablishDate("1495527992727");
		licenseDto.setStartDate("1496223381100");
		licenseDto.setEndDate("1496223381100");
		licenseDto.setPerpetualManagement(0);
		licenseDto.setRegisteredCapital(BigDecimal.valueOf(48512));
		licenseDto.setBusinessScope("gfdgfd");
		licenseDto.setRegistLicencePic("/group1/M00/01/72/rB4KPFncMP2ASLAYAAFm0KzNDKg589.png");
		licenseDto.setGuaranteeMoney(BigDecimal.valueOf(1223));
		licenseDto.setStatus(1);
		infoDto.setSupplierlicenseInfo(licenseDto);

		SupplierSaleRegionDto regionDto = new SupplierSaleRegionDto();
		regionDto.setId(147);
		regionDto.setJson("[{\"code\":\"31\",\"regionName\":\"华东地区\",\"regions\":[{\"code\":\"310000\",\"regionName\":\"上海\",\"regions\":[{\"code\":\"310100\",\"regionName\":\"上海\"}]}]}]");
		infoDto.setSaleRegionInfo(regionDto);

		Response<String> response = service.updateSupplierInfo(infoDto, name);
	}

}
