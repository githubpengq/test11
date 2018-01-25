package com.cotton.dao.weixin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotton.model.weixin.AccessToken;
import com.cotton.model.weixin.AccessToken.AccountType;

public interface AccessTokenDAO extends JpaRepository<AccessToken, Long> {
	AccessToken findByAppIdAndAccountType(String appId,AccountType accountType);
}
