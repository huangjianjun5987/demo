package com.yatang.sc.operation.vo.supplier;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yatang.sc.validgroup.GroupOne;
import com.yatang.sc.validgroup.GroupTwo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
public class SpAdrContactVo implements Serializable {

	private static final long	serialVersionUID	= -9003207511138119530L;

	@NotNull(groups = { GroupOne.class }, message = "{msg.notEmpty.message}")
	private Integer				id;

	/**
	 * 供应商姓名
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	@Length(max = 6, groups = { GroupOne.class, GroupTwo.class }, message = "{msg.length.message}")
	private String				providerName;

	/**
	 * 供应商手机号
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	@Pattern(regexp = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$", groups = {
			GroupOne.class, GroupTwo.class }, message = "{msg.mobileNo.message}")
	private String				providerPhone;

	/**
	 * 供应商邮箱
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	@Email(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.email.message}")
	private String				providerEmail;

	/**
	 * 采购员姓名
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	@Length(max = 6, groups = { GroupOne.class, GroupTwo.class }, message = "{msg.length.message}")
	private String				purchaseName;

	/**
	 * 采购员电话
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	@Pattern(regexp = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0135678]|18[0-9]|14[579])[0-9]{8}$", groups = {
			GroupOne.class, GroupTwo.class }, message = "{msg.mobileNo.message}")
	private String				purchasePhone;

	/**
	 * 采购员邮箱
	 */
	@NotBlank(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.notEmpty.message}")
	@Email(groups = { GroupOne.class, GroupTwo.class }, message = "{msg.email.message}")
	private String				purchaseEmail;

	private Integer				modifyId;

    /**
     * 状态：0：原记录, 1：修改记录
     * */
    @NotNull(groups = {GroupOne.class}, message = "{msg.notEmpty.message}")
    private Integer status;

	@JsonIgnore
	private SpAdrContactVo		modification;								// 联系人信息
}