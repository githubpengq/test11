package com.cotton.model.weixin;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.cotton.model.base.BaseModel;

@Entity
@Table(name="t_access_token")
public class AccessToken extends BaseModel {
	
	private String appId;
	
	private String token;
	
	private Integer expireTime;

	@Enumerated(EnumType.STRING)
	private AccountType accountType;
	
    public enum AccountType {
		FW,QY,DY
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}

	public boolean isExpire() {
		return System.currentTimeMillis()-getUpdateTime().getTime()>getExpireTime()*1000*0.9;
	}
}