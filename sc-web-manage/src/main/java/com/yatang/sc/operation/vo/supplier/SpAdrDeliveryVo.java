package com.yatang.sc.operation.vo.supplier;

import com.yatang.sc.facade.dto.WarehouseInfoDto;
import com.yatang.sc.operation.vo.WarehouseInfoVo;
import com.yatang.sc.operation.vo.WarehouseLogicQueryVo;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class SpAdrDeliveryVo implements Serializable {
	
	private static final long serialVersionUID = -5985862502046097806L;

	private Integer id;

	/**供应商地点ID*/
    private Integer spAdrId;

    /**仓库ID*/
    @NotNull(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
    private Integer warehouseId;

    /**仓库信息*/
    //private WarehouseLogicQueryVo warehouse;
    private WarehouseInfoVo warehouse;


}