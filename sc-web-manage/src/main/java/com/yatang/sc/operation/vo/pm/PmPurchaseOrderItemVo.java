package com.yatang.sc.operation.vo.pm;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @描述: 采购单商品表vo类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/24 17:59
 * @版本: v1.0
 */
@Getter
@Setter
@ToString
public class PmPurchaseOrderItemVo implements Serializable {
    private static final long serialVersionUID = -6284210448825913199L;


    private Long id;


    private String purchaseOrderId;//采购单号

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String productId;//商品id

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String prodPurchaseId;//采购关系主键  new

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String productCode;//商品编号

//    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String productName;//商品名称

//    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String internationalCode;//条码(国际码

//    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String packingSpecifications;//规格

//    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String producePlace;//产地

//    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private Integer purchaseInsideNumber;//采购内装数(默认箱规)

//    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String unitExplanation;//单位

//    @NotNull(message = "{msg.notEmpty.message}",groups = {GroupOne.class, DefaultGroup.class})
//    @Digits(integer = 2, fraction = 2, message = "{msg.bigDecimal.message}", groups = {GroupOne.class, DefaultGroup.class})
//    @Min (value = 0,message = "{msg.min.message}",groups = {GroupOne.class, DefaultGroup.class})
    private BigDecimal inputTaxRate;//税率,进项税率

//    @NotNull(message = "{msg.notEmpty.message}",groups = {GroupOne.class, DefaultGroup.class})
//    @Digits(integer = 8, fraction = 2, message = "{msg.bigDecimal.message}", groups = {GroupOne.class, DefaultGroup.class})
//    @Min (value = 0,message = "{msg.min.message}",groups = {GroupOne.class, DefaultGroup.class})
    private BigDecimal purchasePrice;//采购价格（含税）

    @NotNull(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    @Min(value = 1,message = "{msg.min.message}",groups = {GroupOne.class, DefaultGroup.class})
    private Integer purchaseNumber;//采购数量,必须为采购内装数的倍数

    private Long totalAmount;//采购金额，含税

    private Integer receivedNumber;//'已收货数量,收货数量<=采购数量

    private Date createTime;//创建时间

    private Date modifyTime;//修改时间

    private String createUserId;//创建者id

    private String modifyUserId;//修改者id


    private BigDecimal taxAmount;//税额 new

    private BigDecimal totalWithoutTaxAmount;//不含税采购金额 new

    private Integer isValid;//是否有效:0,无效,1:有效

    private String inValidateReason;//失败原因

    private String               productBrandId;                            //商品品牌id

    private String               productBrandName;                          //商品品牌名称

    private Integer	availableInventory;	// 可用库存，计算方式=现有库存-销售保留-调拨预留-退货预留


}