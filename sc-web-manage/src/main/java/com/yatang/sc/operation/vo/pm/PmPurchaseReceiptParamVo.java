package com.yatang.sc.operation.vo.pm;

import com.yatang.sc.operation.common.BaseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 收货单列表查询参数vo
 * 
 * @author: yinyuxin
 * @version: 1.0, 2017年7月26日
 */
@Setter
@Getter
@ToString
public class PmPurchaseReceiptParamVo extends BaseVo implements Serializable {

	private static final long serialVersionUID = -3368600998636140288L;

	/**
	 * 采购单号
	 */
	private String purchaseOrderNo;

	/**
	 * 子公司id
	 */
	private String branchCompanyId;

	/**
	 * 收货单号
	 */
	private String purchaseReceiptNo;

	/**
	 * 收货起始日期
	 */
	private Date receivedTimeStart;

	/**
	 * 收货结束日期
	 */
	private Date receivedTimeEnd;

	/**
	 * 类型:0:普通采购单;1:赠品采购;2:促销釆购
	 */
	private Integer purchaseOrderType;

	/**
	 * 供应商编码
	 */
	private String spNo;

	/**
	 * 状态:0:待下发，1：已下发，2:已收货，3:已取消，4：异常
	 */
	private Integer status;

	/**
	 * 供应商地点编码
	 */
	private String spAdrNo;

	/**
	 * 地点类型:0:仓库;1:门店
	 */
	private Integer adrType;

	/**
	 * 地点类型编码
	 */
	private String adrTypeCode;

	/**
	 * 开始审核日期
	 */
	private Date startAuditTime;

	/**
	 * 结束审核日期
	 */
	private Date endAuditTime;

	/**
	 * 创建人id
	 */
	private String createUserId;

	/**
	 * 修改人id
	 */
	private String modifyUserId;

	/**
	 *  经营模式:0:经销;1:代销
	 * */
	private Integer	businessMode;



	public void setStartAuditTime(String startAuditTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(startAuditTime)));
			Date minDate = format.parse(format1);
			this.startAuditTime = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}



	public void setEndAuditTime(String endAuditTime) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(endAuditTime)));
			Date minDate = format.parse(format1);
			this.endAuditTime = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}



	public void setReceivedTimeStart(String receivedTimeStart) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(receivedTimeStart)));
			Date minDate = format.parse(format1);
			this.receivedTimeStart = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}



	public void setReceivedTimeEnd(String receivedTimeEnd) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String format1 = format.format(new Date(Long.valueOf(receivedTimeEnd)));
			Date minDate = format.parse(format1);
			this.receivedTimeEnd = minDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
