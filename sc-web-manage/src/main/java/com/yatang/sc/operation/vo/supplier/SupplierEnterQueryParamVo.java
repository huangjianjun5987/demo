package com.yatang.sc.operation.vo.supplier;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yatang.sc.operation.common.BaseVo;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述:供应商入驻查询参数接收类
 * @类名:SupplierEnterQueryParamVo
 * @作者: lvheping
 * @创建时间: 2017/6/12 13:57
 * @版本: v1.0
 */
@Getter
@Setter
public class SupplierEnterQueryParamVo extends BaseVo implements Serializable {
	private static final long	serialVersionUID	= 3579092622021932383L;
	private String				companyName;								// 公司名称
	private String				spNo;										// 供应商编号
	private String				spRegNo;									// 注册号
	private String				name;										// 联系人
	private String				phone;										// 联系手机
	private Integer				status;										// 状态0：待审批,
	private Date				minSettledRequestTime;						// 最小申请入驻时间
	private Date				maxSettledRequestTime;						// 最大申请入驻时间



	public void setMinSettledRequestTime(String minSettledRequestTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(minSettledRequestTime)));
			Date minDate = format.parse(format1);
			this.minSettledRequestTime = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}



	public void setMaxSettledRequestTime(String maxSettledRequestTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(maxSettledRequestTime)));
			Date minDate = format.parse(format1);
			this.maxSettledRequestTime = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
