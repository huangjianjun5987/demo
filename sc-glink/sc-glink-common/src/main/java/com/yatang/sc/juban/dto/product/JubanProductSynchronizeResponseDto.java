package com.yatang.sc.juban.dto.product;

import com.yatang.sc.juban.dto.ResponseDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品同步响应dto<br>
 *
 * @author yipeng
 */
@Getter
@Setter
@ToString
public class JubanProductSynchronizeResponseDto extends ResponseDto {

    private String itemId;   //仓储系统商品Id, string (50) ,条件必填， 当这个字段不为空的时候, 所有erp传输的时候都碰到itemid必传

}
