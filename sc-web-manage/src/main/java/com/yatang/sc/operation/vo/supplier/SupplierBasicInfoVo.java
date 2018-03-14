package com.yatang.sc.operation.vo.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @描述: 供应商基本信息.
 * @作者: huangjianjun
 * @创建时间: 2017年5月13日-上午10:59:57 .
 * @版本: 1.0 .
 * @param <T>
 */
@Getter
@Setter
public class SupplierBasicInfoVo implements Serializable {

	private static final long	serialVersionUID	= 852857990139209011L;

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Integer				id;											// 编号

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	@Length(max=150, groups = {GroupOne.class, GroupTwo.class}, message = "{msg.length.message}")
	private String				companyName;								// 公司名称

	@NotBlank(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private String				spNo;										// 供应商编号

	/** 供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商 */
	@NotNull(groups = {GroupOne.class, GroupTwo.class}, message = "{msg.notEmpty.message}")
	private Integer				grade;

	/** 入驻时间 */
	private Date 				settledTime;

	@NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
	private Integer				status;

	/**
	 * 修改后数据的id
	 * */
	private Integer 			modifyId;

	@JsonIgnore
	private SupplierBasicInfoVo modification;

	public void setSettledTime(String settledTime){
		if (settledTime.length() == 10) {
			settledTime = settledTime.concat("000");
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(settledTime)));
			Date settledDate = format.parse(format1);
			this.settledTime = settledDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}