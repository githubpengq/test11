package com.cotton.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cotton.model.user.User;

public interface UserDAO extends JpaRepository<User, Long> {
	User findByMobileAndPassword(String mobile, String passWord);
	User findByMobile(String mobile);
	
}
