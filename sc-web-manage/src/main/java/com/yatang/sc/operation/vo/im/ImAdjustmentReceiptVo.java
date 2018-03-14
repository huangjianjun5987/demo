package com.yatang.sc.operation.vo.im;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @描述: 库存调整单
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/30 上午10:39
 * @版本: v1.0
 */
@ToString
@Setter
@Getter
public class ImAdjustmentReceiptVo implements Serializable {
    private static final long serialVersionUID = 5745539819612884656L;

    private ImAdjustmentVo imAdjustment;// 主表

    private List<ImAdjustmentItemVo> imAdjustmentItems;//库存调整单item(商品)
}
