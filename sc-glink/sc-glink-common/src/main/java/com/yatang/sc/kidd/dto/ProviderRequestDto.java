package com.yatang.sc.kidd.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @描述:kidd 提供给第三方服务 DTO
 * @作者: yipeng
 * @创建时间: 2017-09-25 09:22:39
 * @版本: v1.0
 */
@Setter
@Getter
@ToString
public class ProviderRequestDto<T> implements java.io.Serializable {

    private static final long serialVersionUID = -6638727707669964246L;

    private String apiMethod;
    private T payload;


}
