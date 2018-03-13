package com.yatang.sc.bpm.domain;

import java.io.Serializable;
import java.util.Date;

public class UserPo implements Serializable {
	private Long				id;

	private String				userName;

	private String				password;

	private String				salt;

	private Boolean				locked;

	private String				fullName;

	private String				position;

	private String				mobile;

	private String				email;

	private Date				createTime;

	private Date				updateTime;

	private static final long	serialVersionUID	= 1L;



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}



	public String getSalt() {
		return salt;
	}



	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}



	public Boolean getLocked() {
		return locked;
	}



	public void setLocked(Boolean locked) {
		this.locked = locked;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName == null ? null : fullName.trim();
	}



	public String getPosition() {
		return position;
	}



	public void setPosition(String position) {
		this.position = position == null ? null : position.trim();
	}



	public String getMobile() {
		return mobile;
	}



	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	@Override
	public String toString() {
		return "UserPo [id=" + id + ", userName=" + userName + ", password=" + password + ", salt=" + salt
				+ ", locked=" + locked + ", fullName=" + fullName + ", position=" + position + ", mobile=" + mobile
				+ ", email=" + email + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
}