package com.yatang.sc.facade.dubboservice.impl;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.SupplierAdrInfoPo;
import com.yatang.sc.facade.domain.SupplierAuditInfoPo;
import com.yatang.sc.facade.domain.SupplierAuditsPo;
import com.yatang.sc.facade.domain.SupplierBankInfoPo;
import com.yatang.sc.facade.domain.SupplierBasicInfoPo;
import com.yatang.sc.facade.domain.SupplierInfoPo;
import com.yatang.sc.facade.domain.SupplierOperTaxInfoPo;
import com.yatang.sc.facade.domain.SupplierSaleRegionPo;
import com.yatang.sc.facade.domain.SupplierlicenseInfoPo;
import com.yatang.sc.facade.dto.supplier.SupplierAdrAuditDto;
import com.yatang.sc.facade.dto.supplier.SupplierAdrInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierAuditDto;
import com.yatang.sc.facade.dto.supplier.SupplierAuditInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierAuditsDto;
import com.yatang.sc.facade.dto.supplier.SupplierBankInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierBasicInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierOperTaxInfoDto;
import com.yatang.sc.facade.dto.supplier.SupplierSaleRegionDto;
import com.yatang.sc.facade.dto.supplier.SupplierlicenseInfoDto;
import com.yatang.sc.facade.dubboservice.SupplierWriteDubboService;
import com.yatang.sc.facade.enums.CommonEnum;
import com.yatang.sc.facade.enums.PublicEnum;
import com.yatang.sc.facade.service.SupplierAdrService;
import com.yatang.sc.facade.service.SupplierService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("supplierWriteDubboService")
public class SupplierWriteDubboServiceImpl implements SupplierWriteDubboService {

	@Autowired
	private SupplierService		service;

	@Autowired
	private SupplierAdrService	supplierAdrService;



	@Override
	public Response<Boolean> insertSupplier(SupplierInfoDto record) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			SupplierInfoPo po = BeanConvertUtils.convert(record, SupplierInfoPo.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.insertSupplier(po));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> updateSupplier(SupplierInfoDto record) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			SupplierInfoPo po = BeanConvertUtils.convert(record, SupplierInfoPo.class);
			response.setSuccess(service.updateSupplier(po));
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(null);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}



	@Override
	public Response<Boolean> insertBasicInfo(SupplierBasicInfoDto record) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			SupplierBasicInfoPo po = BeanConvertUtils.convert(record, SupplierBasicInfoPo.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.insertBasicInfo(po));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> insertOperTaxInfo(SupplierOperTaxInfoDto record) {
		Response<Boolean> response = new Response<Boolean>();
		Boolean flag;
		try {
			SupplierOperTaxInfoPo po = BeanConvertUtils.convert(record, SupplierOperTaxInfoPo.class);
			flag = service.insertOperTaxInfo(po);
			if (flag) {
				response.setErrorMessage("操作成功");
			} else {
				response.setErrorMessage("操作失败");
			}
			response.setSuccess(flag);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> deleteOperTaxInfo(Integer id) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.deleteOperTaxInfo(id));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> insertBankInfo(SupplierBankInfoDto record) {
		Response<Boolean> response = new Response<Boolean>();
		Boolean flag;
		try {
			SupplierBankInfoPo po = BeanConvertUtils.convert(record, SupplierBankInfoPo.class);
			flag = service.insertBankInfo(po);
			if (flag) {
				response.setErrorMessage("操作成功");
			} else {
				response.setErrorMessage("操作失败");
			}
			response.setSuccess(flag);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> deleteBankInfo(Integer id) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.deleteBankInfo(id));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> insertlicenseInfo(SupplierlicenseInfoDto record) {
		Response<Boolean> response = new Response<Boolean>();
		Boolean flag;
		try {
			SupplierlicenseInfoPo po = BeanConvertUtils.convert(record, SupplierlicenseInfoPo.class);
			flag = service.insertlicenseInfo(po);
			if (flag) {
				response.setErrorMessage("操作成功");
			} else {
				response.setErrorMessage("操作失败");
			}
			response.setSuccess(flag);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> deletelicenseInfo(Integer id) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			response.setSuccess(service.deletelicenseInfo(id));
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * @Description: 新增供应商
	 * @author tankejia
	 * @date 2017/7/19- 14:20
	 * @param supplierInfoDto
	 */
	@Override
	public Response<String> insertSupplierInfo(SupplierInfoDto supplierInfoDto, String username) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<新增供应商>> insertSupplierInfo(SupplierInfoDto record, String username): record="
					+ JSON.toJSONString(supplierInfoDto) + "; username=" + username + "----------");
		}
		Response<String> response = new Response<>();
		try {
			// dto转为po
			SupplierInfoPo supplierInfoPo = BeanConvertUtils.convert(supplierInfoDto, SupplierInfoPo.class);
			supplierInfoPo.setCreateUser(username);
			String mainId = service.insertSupplierInfo(supplierInfoPo);
			if (mainId != null) {
				response.setSuccess(true);
				response.setResultObject(mainId);
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setSuccess(false);
				response.setErrorMessage(CommonsEnum.RESPONSE_402.getName());
			}
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * @Description: 修改供应商
	 * @author tankejia
	 * @date 2017/7/19- 14:20
	 * @param supplierInfoDto
	 */
	@Override
	public Response<String> updateSupplierInfo(SupplierInfoDto supplierInfoDto, String username) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<修改供应商>> updateSupplierInfo(SupplierInfoDto record, String username): record="
					+ JSON.toJSONString(supplierInfoDto) + "; username=" + username + "----------");
		}
		Response<String> response = new Response<>();
		try {
			// dto转为po
			SupplierInfoPo supplierInfoPo = BeanConvertUtils.convert(supplierInfoDto, SupplierInfoPo.class);
			supplierInfoPo.setModifyUser(username);
			response.setSuccess(service.updateSupplierInfo(supplierInfoPo));
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * @Description: 新增供应商地点
	 * @author tankejia
	 * @date 2017/7/19- 15:37
	 * @param supplierAdrInfoDto
	 */
	@Override
	public Response<Integer> insertSupplierAddInfo(SupplierAdrInfoDto supplierAdrInfoDto, String username) {
		if (log.isInfoEnabled()) {
			log.info("---------- <<新增供应商地点>> insertSupplierAddInfo(SupplierAdrInfoDto record, String username): record="
					+ JSON.toJSONString(supplierAdrInfoDto) + "; username=" + username + "----------");
		}
		Response<Integer> response = new Response<>();
		try {
			SupplierInfoPo supplierInfoPo = service.queryMainById(supplierAdrInfoDto.getParentId());
			if (supplierInfoPo != null) {
				// 供应商为已提交或者已审核才能创建供应商地点
				if (CommonEnum.SUPPLIER_INFO_ONE.getCode().equals(supplierInfoPo.getStatus())
						|| CommonEnum.SUPPLIER_INFO_TWO.getCode().equals(supplierInfoPo.getStatus())) {
					// dto转为po
					SupplierAdrInfoPo supplierAdrInfoPo = BeanConvertUtils.convert(supplierAdrInfoDto,
							SupplierAdrInfoPo.class);
					supplierAdrInfoPo.setCreateUser(username);
					Integer id = supplierAdrService.insertSupplierAddInfo(supplierAdrInfoPo);
					if (id != -1) {
						response.setResultObject(id);
						response.setSuccess(true);
						response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
					} else {
						response.setSuccess(false);
						response.setErrorMessage(CommonsEnum.RESPONSE_402.getName());
					}
				} else {
					response.setCode(CommonsEnum.RESPONSE_10033.getCode());
					response.setErrorMessage(CommonsEnum.RESPONSE_10033.getName());
					response.setResultObject(null);
					response.setSuccess(false);
				}
			}

		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * @Description: 修改供应商地点
	 * @author tankejia
	 * @date 2017/7/19- 15:37
	 * @param supplierAdrInfoDto
	 */
	@Override
	public Response<Boolean> updateSupplierAddInfo(SupplierAdrInfoDto supplierAdrInfoDto, String username) {
		Assert.notNull(supplierAdrInfoDto, "修改参数为空");
		if (log.isInfoEnabled()) {
			log.info("---------- <<修改供应商地点>> updateSupplierAddInfo(SupplierAdrInfoDto record, String username): record="
					+ JSON.toJSONString(supplierAdrInfoDto) + "; username=" + username + "----------");
		}
		Response<Boolean> response = new Response<>();
		try {
			// dto转为po
			SupplierAdrInfoPo supplierAdrInfoPo = BeanConvertUtils.convert(supplierAdrInfoDto, SupplierAdrInfoPo.class);
			supplierAdrInfoPo.setModifyUser(username);
			response.setSuccess(supplierAdrService.updateSupplierAddInfo(supplierAdrInfoPo));
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * 供应商最终审核信息录入
	 * 
	 * @param supplierAuditInfoDto
	 * @author
	 * @return
	 */
	@Override
	public Response<Boolean> insertSupplierAuditInfo(SupplierAuditInfoDto supplierAuditInfoDto) {

		SupplierAuditInfoPo supplierAuditInfoPo = BeanConvertUtils.convert(supplierAuditInfoDto,
				SupplierAuditInfoPo.class);
		Response<Boolean> response = new Response<>();
		try {
			Boolean bool = service.insertSupplierAuditInfo(supplierAuditInfoPo);
			response.setSuccess(bool);
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getCode());
			return response;
		} catch (Exception e) {
			response.setErrorMessage(CommonsEnum.RESPONSE_10022.getName());
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setSuccess(false);
			log.error(ExceptionUtils.getFullStackTrace(e));
			return response;
		}
	}



	/**
	 * 更新供应商公司经营税务信息修改审核
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public Response<Boolean> updateSupplierTaxApproval(SupplierOperTaxInfoDto record, Integer newId) {
		Response<Boolean> response = new Response<Boolean>();
		boolean flag = false;
		try {
			SupplierOperTaxInfoPo po = BeanConvertUtils.convert(record, SupplierOperTaxInfoPo.class);
			flag = service.updateSupplierTaxApproval(po, newId);
			if (flag) {
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setCode(CommonsEnum.RESPONSE_400.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			}
			response.setSuccess(flag);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(flag);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * 更新供应商银行信息修改资料审核
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public Response<Boolean> updateBankApproval(SupplierBankInfoDto record, Integer newId) {
		Response<Boolean> response = new Response<Boolean>();
		boolean flag = false;
		try {
			SupplierBankInfoPo po = BeanConvertUtils.convert(record, SupplierBankInfoPo.class);
			flag = service.updateBankApproval(po, newId);
			if (flag) {
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setCode(CommonsEnum.RESPONSE_400.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			}
			response.setSuccess(flag);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(flag);
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	/**
	 * 审核公司营业执照（副本）修改内容
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public Response<Boolean> updateLicenseApproval(SupplierlicenseInfoDto record, Integer newId) {
		Response<Boolean> response = new Response<Boolean>();
		boolean flag = false;
		try {
			SupplierlicenseInfoPo po = BeanConvertUtils.convert(record, SupplierlicenseInfoPo.class);
			flag = service.updateLicenseInfoApproval(po, newId);
			if (flag) {
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			} else {
				response.setCode(CommonsEnum.RESPONSE_400.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
			}
			response.setSuccess(flag);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
			response.setSuccess(flag);
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Void> insertSaleRegionInfo(SupplierSaleRegionDto record) {
		Response<Void> response = new Response<Void>();
		try {
			SupplierSaleRegionPo po = BeanConvertUtils.convert(record, SupplierSaleRegionPo.class);
			response.setCode(PublicEnum.SUCCESS_CODE.getCodeStr());
			service.insertSaleRegion(po);
			response.setSuccess(true);
			response.setResultObject(null);
		} catch (Exception e) {
			response.setCode(PublicEnum.ERROR_CODE.getCodeStr());
			response.setSuccess(false);
			response.setErrorMessage(PublicEnum.ERROR_MSG.getCodeStr());
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
		return response;
	}



	@Override
	public Response<Boolean> auditSupplier(SupplierAuditDto supplierAuditDto) {
		if (log.isInfoEnabled()) {
			log.info("SupplierWriteDubboServiceImpl.auditSupplier(SupplierAuditDto {})",
					ToStringBuilder.reflectionToString(supplierAuditDto));
		}
		Response<Boolean> response = new Response<>();
		try {
			SupplierInfoPo supplierInfoPo = service.queryById(supplierAuditDto.getId());
			if (null == supplierInfoPo) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				response.setResultObject(false);
				return response;
			}
			if (1 != supplierInfoPo.getStatus()) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage("拒绝修改");
				response.setResultObject(false);
				return response;
			}
			supplierInfoPo.setFailedReason(!supplierAuditDto.getPass() ? supplierAuditDto.getFailedReason() : null);
			supplierInfoPo.setStatus(supplierAuditDto.getPass() ? 2 : 3);
			supplierInfoPo.setAuditTime(new Date());
			supplierInfoPo.setAuditUserId(supplierAuditDto.getAuditUserId());

			service.auditSupplier(supplierInfoPo);

			response.setSuccess(true);
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
			response.setResultObject(true);
			return response;

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}

		return response;
	}



	@Override
	public Response<Boolean> auditSupplierAdr(SupplierAdrAuditDto supplierAdrAuditDto) {
		if (log.isInfoEnabled()) {
			log.info("SupplierWriteDubboServiceImpl.auditSupplierAdr(SupplierAdrAuditDto {})",
					ToStringBuilder.reflectionToString(supplierAdrAuditDto));
		}
		Response<Boolean> response = new Response<>();
		try {
			SupplierAdrInfoPo supplierAdrInfoPo = supplierAdrService.queryById(supplierAdrAuditDto.getId());
			if (null == supplierAdrInfoPo) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_10006.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_10006.getName());
				response.setResultObject(false);
				return response;
			}
			if (1 != supplierAdrInfoPo.getStatus()) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage("拒绝修改");
				response.setResultObject(false);
				return response;
			}
			SupplierInfoPo supplierInfoPo = service.queryById(supplierAdrInfoPo.getParentId());
			// 供应商是已拒绝状态，供应商地点不能修改为已审核状态
			if (2 != supplierInfoPo.getStatus() && 3 != supplierInfoPo.getStatus()) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage("拒绝修改");
				response.setResultObject(false);
				return response;
			}
			if (3 == supplierInfoPo.getStatus() && supplierAdrAuditDto.getPass()) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage("拒绝修改");
				response.setResultObject(false);
				return response;
			}
			supplierAdrInfoPo
					.setFailedReason(!supplierAdrAuditDto.getPass() ? supplierAdrAuditDto.getFailedReason() : null);
			supplierAdrInfoPo.setStatus(supplierAdrAuditDto.getPass() ? 2 : 3);
			supplierAdrInfoPo.setAuditTime(new Date());
			supplierAdrInfoPo.setAuditUserId(supplierAdrAuditDto.getAuditUserId());

			if (supplierAdrService.updateByPrimaryKeySelective(supplierAdrInfoPo)) {
				response.setSuccess(true);
				response.setCode(CommonsEnum.RESPONSE_200.getCode());
				response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
				response.setResultObject(true);
				return response;
			}
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}

		return response;
	}



	/**
	 * @Description:审核供应商修改的信息
	 * @author kangdong
	 * @param supplierAuditsDto
	 * @return 2017年7月21日 下午10:10:06
	 */
	@Override
	public Response<Boolean> auditSupplierEditInfo(SupplierAuditsDto supplierAuditsDto) {
		Response<Boolean> response = new Response<Boolean>();
		try {
			SupplierAuditsPo supplierAuditsPo = BeanConvertUtils.convert(supplierAuditsDto, SupplierAuditsPo.class);
			response.setSuccess(service.auditSupplierEditInfo(supplierAuditsPo));
			response.setCode(CommonsEnum.RESPONSE_200.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			response.setSuccess(false);
			response.setCode(CommonsEnum.RESPONSE_500.getCode());
			response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
		}
		return response;
	}
}
