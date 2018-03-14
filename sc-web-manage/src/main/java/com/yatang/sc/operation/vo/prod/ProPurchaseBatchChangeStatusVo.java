package com.yatang.sc.operation.vo.prod;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 批量处理的vo类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/17 15:39
 * @版本: v1.0
 */
@Getter
@Setter
public class ProPurchaseBatchChangeStatusVo implements Serializable {

    private static final long serialVersionUID = -7249317009756690295L;
    @NotEmpty(message = "{msg.notEmpty.message}")
    private List<String> productIdList;//商品的id的集合

    @NotBlank(message = "{msg.notEmpty.message}")
    private String spAdrId;//供应商地点信息

    @NotNull(message = "{msg.notEmpty.message}")
    @Range(min = 0, max = 1,message = "{msg.range.message}")
    private Integer status;//采购关系的状态:0,,失效,1启用

    private String modifyUserId;// 修改人id

    private Date modifyDateTime;//修改时间




}
