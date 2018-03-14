package com.yatang.sc.sorder.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/7/19.
 */
@Data
public class OrderDescriptionVo implements Serializable {

    @NotBlank(message = "{msg.notEmpty.message}")
    private String orderId;

    @NotBlank(message = "{msg.notEmpty.message}")
    private String description;

}
