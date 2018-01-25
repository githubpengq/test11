package com.cotton.model.user;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cotton.model.base.BaseModel;


/**
 *后台用户表
 *
 *@author qing.peng
 *@date 2017年6月7日下午3:53:06
 */
@Entity
@Table(name = "t_user")
public class User extends BaseModel {

	/** 手机号 */
	private String mobile;

	/** 姓名 */
	private String name;

	/** 密码 */
	private String password;

	/** 角色 */
	private String role;

	/** 职务 */
	private String duties;

	/** 创建人Id */
	private Long createUserId;
	
	//修改人Id
	private Long updateUserId;
	
	/** 新密码 */
	@Transient
	private String newPassword;
	/** 验证码 */
	@Transient
	private String code;
	
	public String getMobile() {
		return mobile;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getRole() {
		return role;
	}

	public String getDuties() {
		return duties;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	@java.lang.Override
    public java.lang.String toString() {
        return "User{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", duties='" + duties + '\'' +
                ", createUserId=" + createUserId +
                '}';
    }
}