package com.common;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cotton.model.user.User;

public class AuthBaseAction {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String LOGIN_USER = "loginUser";
	
	@Autowired  
	protected  HttpServletRequest request;
	
	/**
	 * 判断运营平台是否登陆
	 */
	protected boolean isUserLogon() {
        if(this.getUser()!=null){
        	return true;
        }
        return false;
    }
	
	/**
	 * 获取用户{@link User},密码为空
	 */
	protected User getUser(){
		User user = (User) request.getSession().getAttribute(LOGIN_USER);
	    return user;
	}
	
	/**
	 * Session存储用户信息
	 */
	protected void setUser(User user) {
		if (user != null) {
			user.setPassword(null);
		}
		request.getSession().setAttribute(LOGIN_USER, user);
	}
	
	/**
	 * 获取用户ID
	 */
	protected Long getUserId() {
		User user = this.getUser();
		return user!=null?user.getId():0L;
	}
	
	/**
	 *获取用户ID
	 */
	public static Long getUserId(HttpServletRequest req) {
		User user = (User) req.getSession().getAttribute(LOGIN_USER);
		return user!=null?user.getId():0L;
    }
}
