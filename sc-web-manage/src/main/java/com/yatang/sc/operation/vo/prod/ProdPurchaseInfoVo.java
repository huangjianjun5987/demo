package com.yatang.sc.operation.vo.prod;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @描述: 商品采购价格Vo类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/15 14:04
 * @版本: v1.0
 */
@Setter
@Getter
public class ProdPurchaseInfoVo implements Serializable {
    private static final long serialVersionUID = -6955580483553367055L;

    @NotNull(groups = GroupOne.class, message = "{msg.notEmpty.message}")
    @Range(min = 1, max = Integer.MAX_VALUE, message = "{msg.range.message}", groups = GroupOne.class)
    private Long id;//pk

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String spId;//供应商id

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String spAdrId;//供应商地点id

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String productId;//产品id

    //    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String branchCompanyId;//子公司code(平台)

    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Range(min = 0, max = 1, message = "{msg.range.message}", groups = {GroupOne.class, DefaultGroup.class})
    private Integer supplierType;//供应商类型:0：一般供应商,1:主供应商

    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Min(value = 1, message = "{msg.min.message}", groups = {GroupOne.class, DefaultGroup.class})
    private Integer purchaseInsideNumber;//采购内装数(默认箱规)

    /* @Digits(integer = 8, fraction = 2, message = "{msg.bigDecimal.message}", groups = {GroupOne.class, DefaultGroup.class})
     @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
     @Min(value = 0, message = "{msg.min.message}")*/
    private BigDecimal purchasePrice;//采购价格

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String internationalCode;//条码(国际码)

    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private Long distributeWarehouseId;//配送仓库id


    private Integer status;//采购关系的状态:0,,失效,1启用

    private Integer deleteStatus;//删除状态:0,,未删除,1启删除

    private Date createTime;

    private String createUserId;

    private Date modifyTime;

    private String modifyUserId;


    private String failedReason;

    private String auditUserId;//审核人ID

    private Date auditTime;//审核日期

    private Integer auditStatus;//审核状态:1:已提交;2:已审核;3:已拒绝


    @Digits(integer = 8, fraction = 2, message = "{msg.bigDecimal.message}", groups = {GroupOne.class, DefaultGroup.class})
    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    @DecimalMin(value = "0", message = "{msg.min.message}", groups = {GroupOne.class, DefaultGroup.class})
    private BigDecimal newestPrice;//最新价格

    private BigDecimal percentage;//调价百分比

    private Integer firstCreated;//第一次创建使用:1:是;0:否


    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String productCode;//商品编码


    @Range(min = 0, max = 1, message = "{msg.range.message}", groups = {GroupOne.class, DefaultGroup.class})
    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private Integer supportReturn;//采购退货 1支持,0不支持


}