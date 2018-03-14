package com.yatang.sc.sorder.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/7/25.
 */
@Getter@Setter@NoArgsConstructor
public class BatchHandleVo implements Serializable{

    @Length(min = 1)
    private String[] ids;

    @NotNull
    private int type;

    private String description;
}
