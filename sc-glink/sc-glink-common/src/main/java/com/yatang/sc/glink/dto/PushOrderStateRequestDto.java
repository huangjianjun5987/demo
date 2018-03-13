package com.yatang.sc.glink.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 际链单据状态通知请求dto<br>
 *
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class PushOrderStateRequestDto implements java.io.Serializable {

    private List<PushOrdersDto> orders;
}
