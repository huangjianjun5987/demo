package com.yatang.sc.operation.vo.wish;

import com.yatang.sc.web.view.XlsHealder;
import com.yatang.sc.web.view.XlsSheet;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 心愿单列表对象dto ---后端(用于导出)
 * @author: yinyuxin
 * @date: 2018/1/3 11:55
 * @version: v1.0
 */
@XlsSheet("预定单列表")
public class WishListForExcelVo implements Serializable{

	private static final long serialVersionUID = 2560325355831717941L;
	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 商品编码
	 */
	@XlsHealder(value = "商品编码", width = 18)
	private String productCode;

	/**
	 * 商品国标码
	 */
	@XlsHealder(value = "商品条码", width = 18)
	private String gbCode;

	/**
	 * 商品名称
	 */
	@XlsHealder(value = "商品名称", width = 36)
	private String productName;

	/**
	 * 总预订量
	 */
	@XlsHealder(value = "需求数量", width = 10)
	private Long totalQuantity;

	/**
	 * 创建时间
	 */
	@XlsHealder(value = "第一次提交时间", width = 18)
	private Date createTime;

	/**
	 * 分公司编码
	 */
	private String branchCompanyId;

	/**
	 * 分公司名字
	 */
	@XlsHealder(value = "所属子公司名字", width = 36)
	private String branchCompanyName;

	/**
	 * 完结时间
	 */
	private Date completeTime;

	/**
	 * 状态，init:待处理， complete:已完成， close：关闭
	 */
	@XlsHealder(value = "处理状态", width = 18)
	private String status;

	/**
	 * 商品图片
	 */
	private String productImg;



	public static long getSerialVersionUID() {
		return serialVersionUID;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}



	public String getGbCode() {
		return gbCode;
	}



	public void setGbCode(String gbCode) {
		this.gbCode = gbCode;
	}



	public String getProductCode() {
		return productCode;
	}



	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}



	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public Long getTotalQuantity() {
		return totalQuantity;
	}



	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getCompleteTime() {
		return completeTime;
	}



	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getProductImg() {
		return productImg;
	}



	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}



	public String getBranchCompanyName() {
		return branchCompanyName;
	}



	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}
}
