package com.yatang.sc.operation.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.BeanUtils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yatang.sc.facade.dto.DepositRecordDto;
import com.yatang.sc.facade.enums.PayChannel;
import com.yatang.sc.facade.enums.PaymentType;
import com.yatang.sc.operation.util.export.ExportField;
import com.yatang.sc.operation.util.export.ExportModel;

/**
 * @描述: 供应商保证金记录Excel模型
 * @作者: yipeng
 * @创建时间: 2017年05月27日11:43:09
 * @版本: 1.0 .
 */
@Getter
@Setter
@ExportModel(fileName = "供应商保证金记录列表")
public class DepositRecordExcelModel implements java.io.Serializable {

	private static final long	serialVersionUID	= 8225569887462020700L;
	
	@ExportField(colName = "记录ID", index = 0)
	private Integer				id;
	@ExportField(colName = "供应商编号", index = 1)
	private String				spNo;
	@ExportField(colName = "供应商名称", index = 2)
	private String				companyName;
	@ExportField(colName = "流水号", index = 3)
	private String				tradeNo;
	@ExportField(colName = "支付渠道", index = 4)
	private String				payChannel;
	@ExportField(colName = "缴纳类型", index = 5)
	private String				paymentType;
	@ExportField(colName = "支付账号", index = 6)
	private String				payAccount;
	@ExportField(colName = "本次缴纳金额", index = 7)
	private BigDecimal			amount;
	@ExportField(colName = "缴纳前余额", index = 8)
	private BigDecimal			beforeAmount;
	@ExportField(colName = "缴纳后余额", index = 9)
	private BigDecimal			afterAmount;
	@ExportField(colName = "缴纳时间", index = 10)
	private Date				createdTime;



	public static List<DepositRecordExcelModel> of(List<DepositRecordDto> list) {
		return Lists.transform(list, new Function<DepositRecordDto, DepositRecordExcelModel>() {
			@Override
			public DepositRecordExcelModel apply(DepositRecordDto dto) {
				DepositRecordExcelModel excelModel = new DepositRecordExcelModel();
				BeanUtils.copyProperties(dto, excelModel);
				excelModel.setPayChannel(PayChannel.valueOf(dto.getPayChannel()).getDescription());
				excelModel.setPaymentType(PaymentType.valueOf(dto.getPaymentType()).getDescription());

				return excelModel;
			}
		});
	}
}
