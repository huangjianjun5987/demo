package com.yatang.sc.facade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.busi.common.utils.BeanConvertUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.dao.SpAdrBasicDao;
import com.yatang.sc.facade.dao.SpAdrContactDao;
import com.yatang.sc.facade.dao.SpAdrDeliveryDao;
import com.yatang.sc.facade.dao.SupplierAdrInfoDao;
import com.yatang.sc.facade.dao.SupplierBankInfoDao;
import com.yatang.sc.facade.dao.SupplierBasicInfoDao;
import com.yatang.sc.facade.dao.SupplierInfoDao;
import com.yatang.sc.facade.dao.SupplierOperTaxInfoDao;
import com.yatang.sc.facade.dao.SupplierSaleRegionDao;
import com.yatang.sc.facade.dao.SupplierlicenseInfoDao;
import com.yatang.sc.facade.domain.AuditFailedReasonPo;
import com.yatang.sc.facade.domain.MultiQuerySupplierPo;
import com.yatang.sc.facade.domain.SpAdrBasicPo;
import com.yatang.sc.facade.domain.SpAdrContactPo;
import com.yatang.sc.facade.domain.SpAdrDeliveryPo;
import com.yatang.sc.facade.domain.SpSearchBoxPo;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.domain.SupplierAuditInfoPo;
import com.yatang.sc.facade.domain.SupplierAuditsPo;
import com.yatang.sc.facade.domain.SupplierBankInfoPo;
import com.yatang.sc.facade.domain.SupplierBasicInfoPo;
import com.yatang.sc.facade.domain.SupplierEnterQueryParamPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.domain.SupplierInfoQueryListPo;
import com.yatang.sc.facade.domain.SupplierInfoQueryParamPo;
import com.yatang.sc.facade.domain.SupplierOperTaxInfoPo;
import com.yatang.sc.facade.domain.SupplierSaleRegionPo;
import com.yatang.sc.facade.domain.SupplierlicenseInfoPo;
import com.yatang.sc.facade.enums.CommonEnum;
import com.yatang.sc.facade.service.SupplierService;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
	@Autowired
	private SupplierInfoDao			mainDao;

	@Autowired
	private SupplierBasicInfoDao	basicDao;

	@Autowired
	private SupplierBankInfoDao		bankDao;

	@Autowired
	private SupplierOperTaxInfoDao	operTaxDao;

	@Autowired
	private SupplierlicenseInfoDao	licenseDao;

	@Autowired
	private SupplierSaleRegionDao	saleRegionDao;

	@Autowired
	private SupplierAdrInfoDao		adrInfoDao;

	@Autowired
	private SpAdrBasicDao			spAdrBasicDao;

	@Autowired
	private SpAdrContactDao			spAdrContactDao;

	@Autowired
	private SpAdrDeliveryDao		spAdrDeliveryDao;



	@Override
	public SupplierInfoPo queryById(String id) {
		return mainDao.queryById(id);
	}



	@Override
	public SupplierInfoPo queryMainById(String id) {
		return mainDao.queryMainById(id);
	}



	@Override
	public PageInfo<SupplierInfoPo> querySettledRequestListPage(SupplierEnterQueryParamPo supplierEnterQueryParamPo) {
		PageHelper.startPage(supplierEnterQueryParamPo.getPageNum(), supplierEnterQueryParamPo.getPageSize());
		List<SupplierInfoPo> list = mainDao.listByParamSortSettledRequestTime(supplierEnterQueryParamPo);
		PageInfo<SupplierInfoPo> pageInfo = new PageInfo<SupplierInfoPo>(list);
		return pageInfo;
	}



	@Override
	public PageInfo<SupplierInfoPo> querySettledListPage(MultiQuerySupplierPo record) {
		PageHelper.startPage(record.getPageNum(), record.getPageSize());
		List<SupplierInfoPo> list = mainDao.listByParamSortSettledTime(record);
		PageInfo<SupplierInfoPo> pageInfo = new PageInfo<SupplierInfoPo>(list);
		return pageInfo;
	}



	@Override
	public PageInfo<SupplierInfoQueryListPo> querySettledList(SupplierInfoQueryParamPo param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<SupplierInfoQueryListPo> list = mainDao.listByParam(param);

		// 修改后处理
		for (SupplierInfoQueryListPo po : list) {
			if (po.getStatus() == 0 || po.getStatus() == 1 || po.getStatus() == 4) {
				if (po.getProviderType() == 1) {
					SupplierBasicInfoPo basicInfo = basicDao.queryById(po.getBasicInfoId());
					if (basicInfo != null && basicInfo.getModifyId() != null) {
						basicInfo = basicDao.queryById(basicInfo.getModifyId());
						po.setProviderName(basicInfo.getCompanyName());
						po.setGrade(basicInfo.getGrade());
					}

					if (po.getLicenseInfoId() != null) {
						SupplierlicenseInfoPo licenseInfo = licenseDao.queryById(po.getLicenseInfoId());
						if (licenseInfo != null && licenseInfo.getModifyId() != null) {
							licenseInfo = licenseDao.queryById(licenseInfo.getModifyId());
							po.setRegistLicenceNumber(licenseInfo.getRegistLicenceNumber());
						}
					}
				} else {
					SpAdrBasicPo basicInfo = spAdrBasicDao.selectByPrimaryKey(po.getBasicInfoId());
					if (basicInfo != null && basicInfo.getModifyId() != null) {
						basicInfo = spAdrBasicDao.selectByPrimaryKey(basicInfo.getModifyId());
						po.setProviderName(basicInfo.getProviderName());
						po.setGrade(basicInfo.getGrade());
					}

					// 这里处理的是供应商地点所属供应商的供应商营业执照号处理
					if (po.getLicenseInfoId() != null) {
						SupplierlicenseInfoPo licenseInfo = licenseDao.queryById(po.getLicenseInfoId());
						if (licenseInfo != null && licenseInfo.getModifyId() != null) {
							licenseInfo = licenseDao.queryById(licenseInfo.getModifyId());
							po.setRegistLicenceNumber(licenseInfo.getRegistLicenceNumber());
						}
					}
				}
			}
		}

		return new PageInfo<>(list);
	}



	@Override
	public Boolean updateSupplier(SupplierInfoPo record) {
		return mainDao.updateByPrimaryKeySelective(record) >= 1;
	}



	@Override
	public Boolean insertBasicInfo(SupplierBasicInfoPo po) {
		return basicDao.insertSelective(po) >= 1;
	}



	@Override
	public Boolean insertOperTaxInfo(SupplierOperTaxInfoPo po) {
		return operTaxDao.insertSelective(po) >= 1;
	}



	@Override
	public Boolean deleteOperTaxInfo(Integer id) {
		return operTaxDao.deleteByPrimaryKey(id) >= 1;
	}



	@Override
	public Boolean insertBankInfo(SupplierBankInfoPo po) {
		return bankDao.insertSelective(po) >= 1;
	}



	@Override
	public Boolean deleteBankInfo(Integer id) {
		return bankDao.deleteByPrimaryKey(id) >= 1;
	}



	@Override
	public Boolean insertlicenseInfo(SupplierlicenseInfoPo po) {
		return licenseDao.insertSelective(po) >= 1;
	}



	@Override
	public Boolean deletelicenseInfo(Integer id) {
		return licenseDao.deleteByPrimaryKey(id) >= 1;
	}



	@Override
	public Boolean updateSupplierTaxApproval(SupplierOperTaxInfoPo po, Integer newId) {
		Boolean flag = false;
		int oldId = po.getId();
		String spId = po.getSpId();
		po.setId(newId);// 更新修改后的表的状态
		flag = operTaxDao.updateByPrimaryKeySelective(po) >= 1;
		if (!flag) {
			return flag;
		}
		SupplierInfoPo sppinfo = mainDao.queryById(spId);
		if (po.getStatus() == 2) {// 通过，删除修改前的数据
			sppinfo.setSupplierOperTaxInfo(po);// 放入最新的对象提供后面查询使用
			// 如果审核通过后，更新供应商主表对应关联的子表ID
			deleteOperTaxInfo(oldId);
			sppinfo.setOperatTaxatId(newId);
			flag = mainDao.updateByPrimaryKeySelective(sppinfo) >= 1;
		} else {// 不通过
			po.setId(oldId);
			po.setStatus(po.getStatus());
			flag = operTaxDao.updateByPrimaryKeySelective(po) >= 1;
		}
		return findStatus(sppinfo);
	}



	@Override
	public Boolean updateBankApproval(SupplierBankInfoPo po, Integer newId) {
		Boolean flag = false;
		int oldId = po.getId();
		po.setId(newId);// 更新修改后的表的状态
		flag = bankDao.updateByPrimaryKeySelective(po) >= 1;
		if (!flag) {
			return flag;
		}
		SupplierInfoPo sppinfo = mainDao.queryById(po.getSpId());
		if (po.getStatus() == 2) {// 通过，删除修改前的数据
			sppinfo.setSupplierBankInfo(po);// 放入最新的对象提供后面查询使用
			// 如果审核通过后，更新供应商主表对应关联的子表ID
			deleteBankInfo(oldId);
			sppinfo.setBankId(newId);
			flag = mainDao.updateByPrimaryKeySelective(sppinfo) >= 1;
		} else {// 不通过
			po.setId(oldId);
			po.setStatus(po.getStatus());
			flag = bankDao.updateByPrimaryKeySelective(po) >= 1;
		}
		return findStatus(sppinfo);
	}



	@Override
	public Boolean updateLicenseInfoApproval(SupplierlicenseInfoPo po, Integer newId) {
		Boolean flag = false;
		int oldId = po.getId();
		po.setId(newId);// 更新修改后的表的状态
		flag = licenseDao.updateByPrimaryKeySelective(po) >= 1;
		if (!flag) {
			return flag;
		}
		SupplierInfoPo sppinfo = mainDao.queryById(po.getSpId());
		if (po.getStatus() == 2) {// 通过，删除修改前的数据
			sppinfo.setSupplierlicenseInfo(po);// 放入最新的对象提供后面查询使用
			// 如果审核通过后，更新供应商主表对应关联的子表ID
			deletelicenseInfo(oldId);
			sppinfo.setLicenseId(newId);
			flag = mainDao.updateByPrimaryKeySelective(sppinfo) >= 1;
		} else {// 不通过
			po.setId(oldId);
			po.setStatus(po.getStatus());
			flag = licenseDao.updateByPrimaryKeySelective(po) >= 1;
		}
		return findStatus(sppinfo);
	}



	/**
	 * @Description: 新增供应商
	 * @author tankejia
	 * @date 2017/7/19- 14:20
	 * @param supplierInfoPo
	 */
	@Override
	public String insertSupplierInfo(SupplierInfoPo supplierInfoPo) {
		// 前面七个实体需要返回主键
		// 1.供应商基本信息录入
		SupplierBasicInfoPo supplierBasicInfo = supplierInfoPo.getSupplierBasicInfo();
		Boolean flag1 = basicDao.insertSelective(supplierBasicInfo) >= 1;
		// 2.公司经营及税务信息录入
		SupplierOperTaxInfoPo supplierOperTaxInfo = supplierInfoPo.getSupplierOperTaxInfo();
		supplierOperTaxInfo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ZERO.getCode());
		Boolean flag2 = operTaxDao.insertSelective(supplierOperTaxInfo) >= 1;
		// 3. 银行信息返回主键
		SupplierBankInfoPo supplierBankInfo = supplierInfoPo.getSupplierBankInfo();
		supplierBankInfo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ZERO.getCode());
		Boolean flag3 = bankDao.insertSelective(supplierBankInfo) >= 1;
		// 4.公司营业执照信息
		SupplierlicenseInfoPo supplierlicenseInfo = supplierInfoPo.getSupplierlicenseInfo();
		supplierlicenseInfo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ZERO.getCode());
		Boolean flag4 = licenseDao.insertSelective(supplierlicenseInfo) >= 1;
		// 5.供应商辐射城市
		SupplierSaleRegionPo supplierSaleRegionsPo = supplierInfoPo.getSaleRegionInfo();
		Boolean flag5 = saleRegionDao.insertSelective(supplierSaleRegionsPo) >= 1;

		// 主表录入
		supplierInfoPo.setBasicId(supplierBasicInfo.getId());
		supplierInfoPo.setOperatTaxatId(supplierOperTaxInfo.getId());
		supplierInfoPo.setBankId(supplierBankInfo.getId());
		supplierInfoPo.setLicenseId(supplierlicenseInfo.getId());
		supplierInfoPo.setSaleRegionId(supplierSaleRegionsPo.getId());

		if (supplierInfoPo.getCommitType() != null) {
			if (CommonEnum.SUPPLIER_SUBMIT_ZERO.getCode().equals(supplierInfoPo.getCommitType())) {
				// 保存草稿
				supplierInfoPo.setStatus(CommonEnum.SUPPLIER_INFO_ZERO.getCode());
			} else {
				// 提交
				supplierInfoPo.setStatus(CommonEnum.SUPPLIER_INFO_ONE.getCode());
			}
		}
		Boolean flag6 = mainDao.insertSelective(supplierInfoPo) >= 1;
		String mainId = null;
		if (flag1 && flag2 && flag3 && flag4 && flag5 && flag6) {
			mainId = supplierInfoPo.getId();
		}
		return mainId;
	}



	/**
	 * 供应商最终审核信息录入
	 *
	 * @param supplierAuditInfoPo
	 * @return
	 */
	@Override
	public Boolean insertSupplierAuditInfo(SupplierAuditInfoPo supplierAuditInfoPo) {
		if (supplierAuditInfoPo.getGuaranteeMoney() != null && supplierAuditInfoPo.getSettlementAccountType() != null
				&& supplierAuditInfoPo.getRebateRate() != null && supplierAuditInfoPo.getSettlementPeriod() != null) {
			if (supplierAuditInfoPo.getRebateRate().compareTo(new BigDecimal(0)) == -1
					|| supplierAuditInfoPo.getRebateRate().compareTo(new BigDecimal(100)) == 1) {
				throw new RuntimeException();
			}
		}
		// 插入供应商主表信息
		SupplierInfoPo supplierInfoPo = new SupplierInfoPo();
		supplierInfoPo.setId(supplierAuditInfoPo.getId());
		supplierInfoPo.setFailedReason(supplierAuditInfoPo.getFailedReason());
		supplierInfoPo.setStatus(supplierAuditInfoPo.getStatus());
		supplierInfoPo.setAuditTime(new Date());
		// supplierInfoPo.setAuditUserId(supplierAuditInfoPo.getAuditUserId());
		// 插入或修改审核失败的原因
		int i = mainDao.updateByPrimaryKeySelective(supplierInfoPo);
		return i >= 1;
	}

	// @Override
	// public PageInfo<Map<String, Object>> supplierSaleRegionList(Map<String,
	// Object> paramMap) {
	// int page = MapUtils.getInteger(paramMap, MAP_PAGE_NUM.getCodeStr(),
	// Integer.valueOf(DEFAULT_PAGE_NUM.getCodeStr()));
	// int size = MapUtils.getInteger(paramMap, MAP_PAGE_SIZE.getCodeStr(),
	// Integer.valueOf(DEFAULT_PAGE_SIZE.getCodeStr()));
	// PageHelper.startPage(page, size);
	// return new PageInfo<>(supplierSaleRegionDao.supplierList(paramMap));
	// }



	// @Override
	// public List<SupplierSaleRegionPo> supplierSaleRegions(String spNo, String
	// parentCode) {
	// return supplierSaleRegionDao.listByParam(spNo, parentCode);
	// }

	@Override
	public Boolean insertSupplier(SupplierInfoPo record) {
		return mainDao.insertSelective(record) >= 1;
	}



	// @Override
	// public void setSaleRegions(List<SupplierSaleRegionPo> regions) {
	// for (SupplierSaleRegionPo region : regions) {
	// supplierSaleRegionDao.insertSelective(region);
	// }
	// }

	@Override
	public List<SupplierlicenseInfoPo> checkSupplierLicenseInfoByRegistLicenceNo(SupplierlicenseInfoPo po) {
		return licenseDao.checkSupplierLicenseInfoByRegistLicenceNo(po);
	}



	@Override
	public List<SupplierBasicInfoPo> checkSupplierBasicInfo(SupplierBasicInfoPo basicPo) {
		return basicDao.checkSupplierBasicInfo(basicPo);
	}



	@Override
	public List<SupplierBankInfoPo> checkSupplierBankInfoByBankAccount(SupplierBankInfoPo po) {
		return bankDao.checkSupplierBankInfoByBankAccount(po);
	}



	/**
	 * 根据传入的供应商ID查询失败原因
	 *
	 * @param id
	 * @return
	 */
	@Override
	public AuditFailedReasonPo findAuditFailedReason(String id) {
		return mainDao.findAuditFailedReason(id);
	}



	/**
	 * @Description: 修改供应商
	 * @author tankejia
	 * @date 2017/7/19- 14:20
	 * @param supplierInfoPo
	 */
	@Override
	public boolean updateSupplierInfo(SupplierInfoPo supplierInfoPo) {
		// 供应商基本信息处理
		modifySupplierBasic(supplierInfoPo);
		// 供应商辐射城市处理
		modifySupplierSaleRegion(supplierInfoPo);
		// 供应商银行信息处理
		modifySupplierBank(supplierInfoPo);
		// 供应商经营及证照信息处理
		modifySupplierOperTax(supplierInfoPo);
		// 供应商营业执照信息处理
		modifySupplierLicense(supplierInfoPo);
		return modifySupplierMain(supplierInfoPo);
	}


	/**
	 * @Description: 修改供应商之基本信息
	 * @author tankejia
	 * @date 2017/12/29- 15:25
	 */
	private boolean modifySupplierBasic(SupplierInfoPo supplierInfoPo){
		boolean basicFlag = false;
		if (supplierInfoPo.getSupplierBasicInfo() != null) {
			SupplierBasicInfoPo basicInfoPo = supplierInfoPo.getSupplierBasicInfo();
			Integer originalBasicInfoId = basicInfoPo.getId();
			// 第一次修改（新增一条数据，并将新增这条数据的id设置为原数据的modifyId）
			if (CommonEnum.SUPPLIER_SUBLIST_ZERO.getCode().equals(basicInfoPo.getStatus()) && supplierInfoPo.getStatus() != 0) {
				basicInfoPo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ONE.getCode());
				basicFlag = basicDao.insertSelective(basicInfoPo) >= 1;
				SupplierBasicInfoPo originalBasicPo = new SupplierBasicInfoPo();
				originalBasicPo.setId(originalBasicInfoId);
				originalBasicPo.setModifyId(basicInfoPo.getId());
				// 修改原记录ModifyId
				if (basicFlag) {
					basicFlag = basicDao.updateByPrimaryKeySelective(originalBasicPo) >= 1;
				}
			} else { // 多次修改直接修改第一次修改时新增的记录
				basicFlag = basicDao.updateByPrimaryKeySelective(basicInfoPo) >= 1;
			}
		}
		return basicFlag;
	}


	/**
	 * @Description: 修改供应商之辐射城市
	 * @author tankejia
	 * @date 2017/12/29- 15:25
	 */
	private boolean modifySupplierSaleRegion(SupplierInfoPo supplierInfoPo){
		boolean saleRegionFlag = false;
		if (supplierInfoPo.getSaleRegionInfo() != null) {
			SupplierSaleRegionPo saleRegionPo = supplierInfoPo.getSaleRegionInfo();
			saleRegionFlag = saleRegionDao.updateByPrimaryKeySelective(saleRegionPo) >= 1;
		}
		return saleRegionFlag;
	}


	/**
	 * @Description: 修改供应商之银行信息
	 * @author tankejia
	 * @date 2017/12/29- 15:25
	 */
	private boolean modifySupplierBank(SupplierInfoPo supplierInfoPo){
		boolean bankFlag = false;
		if (supplierInfoPo.getSupplierBankInfo() != null) {
			SupplierBankInfoPo bankInfoPo = supplierInfoPo.getSupplierBankInfo();
			Integer originalBankInfoPoId = bankInfoPo.getId();
			// 修改逻辑同基本信息修改
			if (CommonEnum.SUPPLIER_SUBLIST_ZERO.getCode().equals(bankInfoPo.getStatus()) && supplierInfoPo.getStatus() != 0) {
				bankInfoPo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ONE.getCode());
				bankFlag = bankDao.insertSelective(bankInfoPo) >= 1;
				SupplierBankInfoPo originalBankInfoPo = new SupplierBankInfoPo();
				originalBankInfoPo.setId(originalBankInfoPoId);
				originalBankInfoPo.setModifyId(bankInfoPo.getId());
				if (bankFlag) {
					bankFlag = bankDao.updateByPrimaryKeySelective(originalBankInfoPo) >= 1;
				}
			} else {
				bankFlag = bankDao.updateByPrimaryKeySelective(bankInfoPo) >= 1;
			}
		}
		return bankFlag;
	}


	/**
	 * @Description: 修改供应商之经营及证照信息
	 * @author tankejia
	 * @date 2017/12/29- 15:25
	 */
	private boolean modifySupplierOperTax(SupplierInfoPo supplierInfoPo){
		boolean operTaxFlag = false;
		if (supplierInfoPo.getSupplierOperTaxInfo() != null) {
			SupplierOperTaxInfoPo operTaxInfoPo = supplierInfoPo.getSupplierOperTaxInfo();
			Integer originalOperTaxInfoPoId = operTaxInfoPo.getId();
			// 修改逻辑同基本信息修改
			if (CommonEnum.SUPPLIER_SUBLIST_ZERO.getCode().equals(operTaxInfoPo.getStatus()) && supplierInfoPo.getStatus() != 0) {
				operTaxInfoPo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ONE.getCode());
				operTaxFlag = operTaxDao.insertSelective(operTaxInfoPo) >= 1;
				SupplierOperTaxInfoPo originalOperTaxInfoPo = new SupplierOperTaxInfoPo();
				originalOperTaxInfoPo.setId(originalOperTaxInfoPoId);
				originalOperTaxInfoPo.setModifyId(operTaxInfoPo.getId());
				if (operTaxFlag) {
					operTaxFlag = operTaxDao.updateByPrimaryKeySelective(originalOperTaxInfoPo) >= 1;
				}
			} else {
				operTaxFlag = operTaxDao.updateByPrimaryKeySelective(operTaxInfoPo) >= 1;
			}
		}
		return operTaxFlag;
	}


	/**
	 * @Description: 修改供应商之营业执照信息
	 * @author tankejia
	 * @date 2017/12/29- 15:25
	 */
	private boolean modifySupplierLicense(SupplierInfoPo supplierInfoPo){
		boolean licenseFlag = false;
		if (supplierInfoPo.getSupplierlicenseInfo() != null) {
			SupplierlicenseInfoPo licenseInfoPo = supplierInfoPo.getSupplierlicenseInfo();
			Integer originalLicenseInfoPoId = licenseInfoPo.getId();
			// 修改逻辑同基本信息修改
			if (CommonEnum.SUPPLIER_SUBLIST_ZERO.getCode().equals(licenseInfoPo.getStatus()) && supplierInfoPo.getStatus() != 0) {
				licenseInfoPo.setStatus(CommonEnum.SUPPLIER_SUBLIST_ONE.getCode());
				licenseFlag = licenseDao.insertSelective(licenseInfoPo) >= 1;
				SupplierlicenseInfoPo originalLicenseInfoPo = new SupplierlicenseInfoPo();
				originalLicenseInfoPo.setId(originalLicenseInfoPoId);
				originalLicenseInfoPo.setModifyId(licenseInfoPo.getId());
				if (licenseFlag) {
					licenseFlag = licenseDao.updateByPrimaryKeySelective(originalLicenseInfoPo) >= 1;
				}
			} else {
				licenseFlag = licenseDao.updateByPrimaryKeySelective(licenseInfoPo) >= 1;
			}
		}
		return licenseFlag;
	}


	/**
	 * @Description: 修改供应商之主表（supplierInfo）信息
	 * @author tankejia
	 * @date 2017/12/29- 15:25
	 */
	private boolean modifySupplierMain(SupplierInfoPo supplierInfoPo) {
		// 修改后保存为草稿
		if (CommonEnum.SUPPLIER_SUBMIT_ZERO.getCode().equals(supplierInfoPo.getCommitType())) {
			return modifyToDraft(supplierInfoPo);
		} else { // 修改后提交
			return modifyToSubmit(supplierInfoPo);
		}
	}


	private boolean modifyToDraft(SupplierInfoPo supplierInfoPo) {
		boolean draftFlag;
		// 前端传过来的状态跟数据库实际的可能不一致因此需要重新查询一次
		SupplierInfoPo po = mainDao.queryById(supplierInfoPo.getId());
		// 假如供应商状态为制单或修改中状态，供应商状态不变
		if (CommonEnum.SUPPLIER_INFO_ZERO.getCode().equals(po.getStatus())
				|| CommonEnum.SUPPLIER_INFO_FOUR.getCode().equals(po.getStatus())) {
			po.setModifyUser(supplierInfoPo.getModifyUser());
			draftFlag = mainDao.updateByPrimaryKeySelective(po) >= 1;
		} else {
			// 供应商状态变成修改中
			po.setStatus(CommonEnum.SUPPLIER_INFO_FOUR.getCode());
			po.setModifyUser(supplierInfoPo.getModifyUser());
			draftFlag = mainDao.updateByPrimaryKeySelective(po) >= 1;
			if (draftFlag) {
				modifySpAdrStatus(supplierInfoPo);
			}
		}
		return draftFlag;
	}


	private boolean modifyToSubmit(SupplierInfoPo supplierInfoPo) {
		SupplierInfoPo po = mainDao.queryById(supplierInfoPo.getId());
		boolean submitFlag;
		po.setStatus(CommonEnum.SUPPLIER_INFO_ONE.getCode());
		po.setModifyUser(supplierInfoPo.getModifyUser());
		submitFlag = mainDao.updateByPrimaryKeySelective(po) >= 1;
		if (submitFlag) {
			modifySpAdrStatus(supplierInfoPo);
		}
		return submitFlag;
	}

	// 已审核的供应商地点状态变为修改中
	private void modifySpAdrStatus(SupplierInfoPo supplierInfoPo) {
		SupplierAdrInfoPo adrInfoPo = new SupplierAdrInfoPo();
		adrInfoPo.setParentId(supplierInfoPo.getId());
		adrInfoPo.setStatus(CommonEnum.SUPPLIER_INFO_FOUR.getCode());
		adrInfoPo.setModifyUser(supplierInfoPo.getModifyUser());
		adrInfoDao.updateByParentId(adrInfoPo);
	}



	public boolean findStatus(SupplierInfoPo sppinfo) {
		List<Integer> statues = Lists.newArrayList(sppinfo.getSupplierBankInfo().getStatus(),
				sppinfo.getSupplierlicenseInfo().getStatus(), sppinfo.getSupplierOperTaxInfo().getStatus());
		if (statues.contains(0)) {
			sppinfo.setStatus(0);
		}
		if (statues.contains(1)) {
			sppinfo.setStatus(6);
		} else {
			sppinfo.setStatus(2);
		}
		return mainDao.updateByPrimaryKeySelective(sppinfo) >= 1;
	}



	@Override
	public void insertSaleRegion(SupplierSaleRegionPo po) {
		saleRegionDao.insertSelective(po);
	}



	/**
	 * @Description: 查询供应商地点信息
	 * @author kangdong
	 * @date 2017年7月21日 下午12:10:25
	 * @param id
	 */

	@Override
	public SupplierAdrInfoPo queryProviderPlace(Integer id) {
		SupplierAdrInfoPo supplierAdrInfoPo = adrInfoDao.selectByPrimaryKey(id);
		Set<SpAdrDeliveryPo> spAdrDeliveryPo = spAdrDeliveryDao.selectDeliveryList(id);
		supplierAdrInfoPo.setSpAdrDeliverys(spAdrDeliveryPo);
		return supplierAdrInfoPo;
	}



	/**
	 * @Description: 根据地点ID查询供应商地点基本信息
	 * @author kangdong
	 * @date 2017年7月21日 晚上20:10:25
	 * @param id
	 */
	@Override
	public SpAdrBasicPo queryByAdrInfoId(Integer id) {
		SupplierAdrInfoPo supplierAdrInfoPo = adrInfoDao.selectByPrimaryKey(id);
		SpAdrBasicPo spAdrBasicPo = supplierAdrInfoPo.getSpAdrBasic();
		return spAdrBasicPo;
	}



	/**
	 * @Description: 查询供应商修改前修改后的信息
	 * @author kangdong
	 * @date 2017年7月19日 下午10:10:35
	 * @param spId
	 */
	@Override
	public ArrayList queryBeforeAndAfter(String spId) {
		SupplierInfoPo beforePo = mainDao.queryById(spId);
		ArrayList list = new ArrayList();
		StringBuffer categoryIndex = new StringBuffer();
		if (beforePo != null) {
			SupplierInfoPo beforeInfo = BeanConvertUtils.convert(beforePo, SupplierInfoPo.class);
			SupplierInfoPo afterPo = mainDao.queryById(spId);
			SupplierInfoPo afterInfo = BeanConvertUtils.convert(afterPo, SupplierInfoPo.class);
			Integer basicModifyId = beforeInfo.getSupplierBasicInfo().getModifyId() != null
					? beforeInfo.getSupplierBasicInfo().getModifyId() : null;
			Integer operTaxModifyId = beforeInfo.getSupplierOperTaxInfo().getModifyId() != null
					? beforeInfo.getSupplierOperTaxInfo().getModifyId() : null;
			Integer bankModifyId = beforeInfo.getSupplierBankInfo().getModifyId() != null
					? beforeInfo.getSupplierBankInfo().getModifyId() : null;
			Integer licenseModifyId = beforeInfo.getSupplierlicenseInfo().getModifyId() != null
					? beforeInfo.getSupplierlicenseInfo().getModifyId() : null;
			if (basicModifyId != null) {
				afterInfo.setSupplierBasicInfo(basicDao.queryById(basicModifyId));
			}
			if (operTaxModifyId != null) {
				afterInfo.setSupplierOperTaxInfo(operTaxDao.queryById(operTaxModifyId));
			}
			if (bankModifyId != null) {
				afterInfo.setSupplierBankInfo(bankDao.queryById(bankModifyId));
			}
			if (licenseModifyId != null) {
				afterInfo.setSupplierlicenseInfo(licenseDao.queryById(licenseModifyId));
			}
			if (beforeInfo instanceof SupplierInfoPo && afterInfo instanceof SupplierInfoPo) {
				beforeAndAfter(list, beforeInfo, afterInfo,categoryIndex, 0);
			}
		} else {
			SupplierAdrInfoPo adrBeforePo = adrInfoDao.selectByPrimaryKey(Integer.parseInt(spId));
			if (adrBeforePo != null) {
				SupplierAdrInfoPo beforeInfo = BeanConvertUtils.convert(adrBeforePo, SupplierAdrInfoPo.class);
				SupplierAdrInfoPo adrAfterPo = adrInfoDao.selectByPrimaryKey(Integer.parseInt(spId));
				SupplierAdrInfoPo afterInfo = BeanConvertUtils.convert(adrAfterPo, SupplierAdrInfoPo.class);
				Integer adrBasic = beforeInfo.getSpAdrBasic().getModifyId();
				Integer adrContact = beforeInfo.getSpAdrContact().getModifyId();
				if (adrBasic != null) {
					afterInfo.setSpAdrBasic(spAdrBasicDao.selectByPrimaryKey(adrBasic));
				}
				if (adrContact != null) {
					afterInfo.setSpAdrContact(spAdrContactDao.selectByPrimaryKey(adrContact));
				}
				if (beforeInfo instanceof SupplierAdrInfoPo && afterInfo instanceof SupplierAdrInfoPo) {
					beforeAndAfter(list, beforeInfo, afterInfo,categoryIndex, 0);
				}
			}
		}
		return list;
	}

	/**
	 * @Description:需要判断的字段类型
	 * @author kangdong
	 * @date 2017/7/20- 10:30
	 */
	private static final List<Object> types = Lists.<Object> newArrayList(String.class, Integer.class, Date.class,
			BigDecimal.class);



	/**
	 * @Description:存入供应商修改前修改后的信息
	 * @author kangdong
	 * @date 2017/7/20- 10:30
	 */
	public void beforeAndAfter(ArrayList list, Object beforeInfo, Object afterInfo, StringBuffer categoryIndex, int counts) {
		counts += 1;
		try {

			Class clazz = beforeInfo.getClass();
			// 获取实体类的所有属性，返回Field数组
			Field[] fields = beforeInfo.getClass().getDeclaredFields();
			for (Field field : fields) {

				String name = field.getName();
				if (Objects.equals(name, "serialVersionUID") || Objects.equals(name, "modification")
						|| Objects.equals(name, "isValue") || Objects.equals(name, "spAdrDeliverys")|| Objects.equals(name, "saleRegionInfo")) {
					continue;
				}
				PropertyDescriptor pd = new PropertyDescriptor(name, clazz);
				Method getMethod = pd.getReadMethod();
				// 调用getter方法获取属性值
				Object o1 = getMethod.invoke(beforeInfo);
				Object o2 = getMethod.invoke(afterInfo);
				if (!types.contains(field.getType())) {
					if (o2 == null) {
						continue;
					}
					if (counts == 1) {
						categoryIndex = new StringBuffer();
					} else {
						categoryIndex.append(".");
					}
					categoryIndex.append(name);
					beforeAndAfter(list, o1, o2 ,categoryIndex, counts);
				}
				if (!Objects.equals(o1, o2) && types.contains(field.getType())) {
					Map category = new JSONObject();
					category.put("categoryIndex", categoryIndex.toString() + "." + name);
					category.put("before",o1);
					category.put("after",o2);
					list.add(category);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}



	@Override
	public PageInfo<SpSearchBoxPo> supplierSearchBox(String condition, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SpSearchBoxPo> list = basicDao.supplierSearchBox(condition);
		PageInfo<SpSearchBoxPo> pageInfo = new PageInfo<SpSearchBoxPo>(list);
		return pageInfo;
	}



	/**
	 * @Description:审核供应商修改的信息
	 * @author kangdong
	 * @param supplierAuditsPo
	 * @return 2017年8月1日 上午10:10:06
	 */
	@Override
	public Boolean auditSupplierEditInfo(SupplierAuditsPo supplierAuditsPo){
		SupplierInfoPo supplierInfoPo = mainDao.queryMainById(supplierAuditsPo.getId());
		Boolean flag = false;
		if(supplierInfoPo != null) {
			SupplierInfoPo po = new SupplierInfoPo();
			po.setId(supplierAuditsPo.getId());
			po.setAuditUserId(supplierAuditsPo.getAuditUserId());
			po.setStatus(BooleanUtils.isTrue(supplierAuditsPo.getPass()) ? 2 : 3);
			po.setAuditTime(new Date());

			this.handleSupplierBasicInfo(supplierAuditsPo,po);
			this.handleSupplierBankInfo(supplierAuditsPo,po);
			this.handleSupplierOperatTaxatInfo(supplierAuditsPo,po);
			this.handleSupplierLicenseInfo(supplierAuditsPo,po);

			if (!supplierAuditsPo.getPass()) {//供应商修改审核（不通过）
				List<SupplierAdrInfoPo> supplierAdrInfoPo = adrInfoDao.querySupplierAddInfoListByParentId(supplierAuditsPo.getId());
				// 供应商地点状态处于“已审核”中，审核供应商不通过时，地点状态才变为“已拒绝”.
				for (SupplierAdrInfoPo adr : supplierAdrInfoPo) {
					if (adr.getStatus() == 2) {// 地点处于已审核状态
						SupplierAdrInfoPo supplierAdrInfo = new SupplierAdrInfoPo();
						supplierAdrInfo.setId(adr.getId());
						supplierAdrInfo.setAuditUserId(supplierAuditsPo.getAuditUserId());
						supplierAdrInfo.setStatus(3);
						supplierAdrInfo.setFailedReason(supplierAuditsPo.getFailedReason());
						supplierAdrInfo.setAuditTime(new Date());
						adrInfoDao.updateByPrimaryKeySelective(supplierAdrInfo);
					}
				}
				po.setFailedReason(supplierAuditsPo.getFailedReason());
			}
			flag = mainDao.updateByPrimaryKeySelective(po) > 0;
		}else {//供应商地点修改审核
			SupplierAdrInfoPo adrPo = adrInfoDao.selectByPrimaryKey(Integer.valueOf(supplierAuditsPo.getId()));
			SupplierInfoPo supplier = mainDao.queryById(adrPo.getParentId());
			adrPo.setAuditUserId(supplierAuditsPo.getAuditUserId());
			adrPo.setAuditTime(new Date());

			this.handleSupplierAdrBasicInfo(supplierAuditsPo,adrPo,supplier);
			this.handleSupplierAdrContactInfo(supplierAuditsPo,adrPo,supplier);

			//供应商状态为“已拒绝”时，供应商地点修改审核只能审核不通过。
			if(supplierAuditsPo.getPass() && supplier.getStatus()!=3) {
				adrPo.setStatus(2);
			}else {
				adrPo.setStatus(3);
				adrPo.setFailedReason(supplierAuditsPo.getFailedReason());
			}
			flag =  adrInfoDao.updateByPrimaryKeySelective(adrPo)>0;
		}
		return flag;
	}

	/**
	 * 处理供应商基本信息
	 * @param supplierAuditsPo
	 * @param po
	 */
	private void handleSupplierBasicInfo(SupplierAuditsPo supplierAuditsPo, SupplierInfoPo po) {
		if (supplierAuditsPo.getBasicId() != null) {
			SupplierBasicInfoPo basicInfoPo = basicDao.queryById(supplierAuditsPo.getBasicId());
			if (supplierAuditsPo.getPass()) {//供应商修改审核
				SupplierBasicInfoPo basicInfo = new SupplierBasicInfoPo();
				basicInfo.setId(basicInfoPo.getModifyId());
				basicInfo.setStatus(0);
				basicDao.updateByPrimaryKeySelective(basicInfo);//设置修改后的数据的状态
				basicDao.deleteByPrimaryKey(supplierAuditsPo.getBasicId());//删除修改前的数据
				po.setBasicId(basicInfoPo.getModifyId());//把修改后的ID设置到主表

				List<SupplierAdrInfoPo> supplierAdrInfoPoes = adrInfoDao.querySupplierAddInfoListByParentId(po.getId());
				if (BooleanUtils.isTrue(supplierAuditsPo.getPass()) && isSpNameChanged(basicInfo, supplierAdrInfoPoes)) {
					SupplierBasicInfoPo supplierBasicInfoPo = basicDao.queryById(basicInfoPo.getModifyId());
					changeSpAdrName(supplierBasicInfoPo.getCompanyName(), supplierAdrInfoPoes);
				}
			}else {
				basicDao.deleteByPrimaryKey(basicInfoPo.getModifyId());//删除修改后的数据（新数据）
			}
		}
	}

	/**
	 * 处理银行信息
	 * @param supplierAuditsPo
	 * @param po
	 */
	private void handleSupplierBankInfo(SupplierAuditsPo supplierAuditsPo, SupplierInfoPo po) {
		if (supplierAuditsPo.getBankId() != null) {
			SupplierBankInfoPo bankInfoPo = bankDao.queryById(supplierAuditsPo.getBankId());
			if (supplierAuditsPo.getPass()) {//供应商修改审核
				SupplierBankInfoPo bankInfo = new SupplierBankInfoPo();
				bankInfo.setId(bankInfoPo.getModifyId());
				bankInfo.setStatus(0);
				bankDao.updateByPrimaryKeySelective(bankInfo);
				bankDao.deleteByPrimaryKey(supplierAuditsPo.getBankId());
				po.setBankId(bankInfoPo.getModifyId());
			}else {
				bankDao.deleteByPrimaryKey(bankInfoPo.getModifyId());
			}
		}
	}

	/**
	 * 处理供应商经营及税务信息.
	 * @param supplierAuditsPo
	 * @param po
	 */
	private void handleSupplierOperatTaxatInfo(SupplierAuditsPo supplierAuditsPo, SupplierInfoPo po) {
		if (supplierAuditsPo.getOperatTaxatId() != null) {
			SupplierOperTaxInfoPo supplierOperTaxInfoPo = operTaxDao.queryById(supplierAuditsPo.getOperatTaxatId());
			if (supplierAuditsPo.getPass()) {//供应商修改审核
				if (supplierAuditsPo.getOperatTaxatId() != null) {
					SupplierOperTaxInfoPo supplierOperTaxInfo = new SupplierOperTaxInfoPo();
					supplierOperTaxInfo.setId(supplierOperTaxInfoPo.getModifyId());
					supplierOperTaxInfo.setStatus(0);
					operTaxDao.updateByPrimaryKeySelective(supplierOperTaxInfo);
					operTaxDao.deleteByPrimaryKey(supplierAuditsPo.getOperatTaxatId());
					po.setOperatTaxatId(supplierOperTaxInfoPo.getModifyId());
				}
			}else {
				operTaxDao.deleteByPrimaryKey(supplierOperTaxInfoPo.getModifyId());
			}
		}
	}


	/**
	 * 处理供应商营业执照(副本)信息.
	 * @param supplierAuditsPo
	 * @param po
	 */
	private void handleSupplierLicenseInfo(SupplierAuditsPo supplierAuditsPo, SupplierInfoPo po) {
		if (supplierAuditsPo.getLicenseId() != null) {
			SupplierlicenseInfoPo supplierlicenseInfoPo = licenseDao.queryById(supplierAuditsPo.getLicenseId());
			if (supplierAuditsPo.getPass()) {//供应商修改审核
				SupplierlicenseInfoPo supplierlicenseInfo = new SupplierlicenseInfoPo();
				supplierlicenseInfo.setId(supplierlicenseInfoPo.getModifyId());
				supplierlicenseInfo.setStatus(0);
				licenseDao.updateByPrimaryKeySelective(supplierlicenseInfo);
				licenseDao.deleteByPrimaryKey(supplierAuditsPo.getLicenseId());
				po.setLicenseId(supplierlicenseInfoPo.getModifyId());
			}else {
				licenseDao.deleteByPrimaryKey(supplierlicenseInfoPo.getModifyId());
			}
		}
	}

	/**
	 * 处理供应商地点基本信息
	 * @param supplierAuditsPo
	 * @param adrPo
	 * @param supplier
	 */
	private void handleSupplierAdrBasicInfo(SupplierAuditsPo supplierAuditsPo, SupplierAdrInfoPo adrPo, SupplierInfoPo supplier) {
		if(supplierAuditsPo.getAdrBasicId()!=null){
			SpAdrBasicPo basicInfoPo = spAdrBasicDao.selectByPrimaryKey(supplierAuditsPo.getAdrBasicId());
			//供应商状态为“已拒绝”时，供应商地点修改审核只能审核不通过。
			if(supplierAuditsPo.getPass() && supplier.getStatus()!=3) {
				//adrPo.setStatus(2);
				SpAdrBasicPo basicInfo = new SpAdrBasicPo();
				basicInfo.setId(basicInfoPo.getModifyId());
				basicInfo.setStatus(0);
				basicInfo.setAuditPerson(supplierAuditsPo.getAuditPerson());
				basicInfo.setAuditDate(new Date());
				spAdrBasicDao.updateByPrimaryKeySelective(basicInfo);//设置修改后的数据的状态
				spAdrBasicDao.deleteByPrimaryKey(supplierAuditsPo.getAdrBasicId());//删除修改前的数据
				adrPo.setBasicId(basicInfoPo.getModifyId());//把修改后的ID设置到主表
			}else {
				spAdrBasicDao.deleteByPrimaryKey(basicInfoPo.getModifyId());
			}
		}
	}

	/**
	 * 供应商地点联系信息
	 * @param supplierAuditsPo
	 * @param adrPo
	 * @param supplier
	 */
	private void handleSupplierAdrContactInfo(SupplierAuditsPo supplierAuditsPo, SupplierAdrInfoPo adrPo, SupplierInfoPo supplier) {
		if(supplierAuditsPo.getContId()!=null) {
			SpAdrContactPo contactInfoPo = spAdrContactDao.selectByPrimaryKey(supplierAuditsPo.getContId());
			//供应商状态为“已拒绝”时，供应商地点修改审核只能审核不通过。
			if (supplierAuditsPo.getPass() && supplier.getStatus() != 3) {
				//adrPo.setStatus(2);
				SpAdrContactPo contactPo = new SpAdrContactPo();
				contactPo.setId(contactInfoPo.getModifyId());
				contactPo.setStatus(0);
				spAdrContactDao.updateByPrimaryKeySelective(contactPo);
				spAdrContactDao.deleteByPrimaryKey(supplierAuditsPo.getContId());
				adrPo.setContId(contactInfoPo.getModifyId());
			}else {
				spAdrContactDao.deleteByPrimaryKey(contactInfoPo.getModifyId());
			}
		}
	}



	private void changeSpAdrName(String newSpName, List<SupplierAdrInfoPo> supplierAdrInfoPoes) {

		for(SupplierAdrInfoPo supplierAdrInfoPo : supplierAdrInfoPoes ){
			SpAdrBasicPo spAdrBasicPo = spAdrBasicDao.selectByPrimaryKey(supplierAdrInfoPo.getBasicId());
			String[] parts = spAdrBasicPo.getProviderName().split("-");
			if(parts.length < 2){
				continue;
			}
			String newSpAdrName = parts[0].trim() + "-" + newSpName.trim();

			SpAdrBasicPo update = new SpAdrBasicPo();
			update.setProviderName(newSpAdrName);
			update.setId(supplierAdrInfoPo.getBasicId());
			spAdrBasicDao.updateByPrimaryKeySelective(update);
		}
	}

	private boolean isSpNameChanged(SupplierBasicInfoPo basicInfo, List<SupplierAdrInfoPo> supplierAdrInfoPoes) {
		SupplierBasicInfoPo supplierBasicInfoPo = basicDao.queryById(basicInfo.getId());
		if(null == supplierBasicInfoPo){
			return false;
		}
		if(CollectionUtils.isEmpty(supplierAdrInfoPoes)){
			return false;
		}
		String providerName = spAdrBasicDao.selectByPrimaryKey(supplierAdrInfoPoes.get(0).getBasicId()).getProviderName();
		if(StringUtils.isEmpty(providerName)){
			return false;
		}
		String[] segments = providerName.split("-");
		if(segments.length < 2){
			return false;
		}
		String spName = segments[1].trim();
		if(spName.equals(supplierBasicInfoPo.getCompanyName().trim())){
			return false;
		}
		return true;
	}

	@Override
	public boolean auditSupplier(SupplierInfoPo supplierInfoPo) {
		mainDao.updateByPrimaryKeySelective(supplierInfoPo);
		// 如果供应商被拒绝了，供应商地点也会被拒绝
		if (3 == supplierInfoPo.getStatus()) {
			// 查询所有的供应商地点
			List<SupplierAdrInfoPo> supplierAdrInfoList = adrInfoDao
					.querySupplierAddInfoListByParentId(supplierInfoPo.getId());
			for (SupplierAdrInfoPo supplierAdrInfoPo : supplierAdrInfoList) {
				// 如果供应商地点状态为已审核，改为已拒绝
				if (2 == supplierAdrInfoPo.getStatus()) {
					supplierAdrInfoPo.setStatus(supplierInfoPo.getStatus());
					adrInfoDao.updateSupplierAdrByParentId(supplierAdrInfoPo);
				}
			}

		}
		return true;
	}

	@Override
	public PageInfo<SupplierInfoQueryListPo> queryExportManageList(SupplierInfoQueryParamPo param) {
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		List<SupplierInfoQueryListPo> list = mainDao.queryExportManageList(param);

		for (SupplierInfoQueryListPo po : list) {
			if(null != po.getLicenseInfoId()){
				SupplierlicenseInfoPo supplierlicenseInfoPo = licenseDao.queryById(po.getLicenseInfoId());
				if(supplierlicenseInfoPo != null){
					supplierlicenseInfoPo.setRegistLicenceNumber(supplierlicenseInfoPo.getRegistLicenceNumber());
				}
			}
		}

		return new PageInfo<>(list);
	}

	/**
	 * @Description: 根据spNo查询供应商
	 * @author kangdong
	 * @date 2017年12月7日 下午16:10:25
	 * @param spNo
	 */
	@Override
	public SupplierInfoPo queryBySpNo(String spNo){
		return mainDao.queryBySpNo(spNo);
	}
}
