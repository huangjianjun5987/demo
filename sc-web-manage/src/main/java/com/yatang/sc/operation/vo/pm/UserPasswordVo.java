package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 修改密码
 * @类名: UserPasswordVo
 * @作者: kangdong
 * @创建时间: 2017/11/30 17:51
 * @版本: v1.0
 */
@Setter
@Getter
public class UserPasswordVo implements Serializable {


    private static final long serialVersionUID = 6512104391059418375L;

    // 原始密码
    @NotBlank(message = "{msg.notEmpty.message}")
    private String oldPassword;
    // 新的密码
    @NotBlank(message = "{msg.notEmpty.message}")
    private String newPassword;
    //确认密码
    @NotBlank(message = "{msg.notEmpty.message}")
    private String confirmPassword;
}
