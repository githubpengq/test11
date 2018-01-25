package com.cotton.service.user;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.AuthCommonService;
import com.cotton.dao.user.UserDAO;
import com.cotton.dao.user.UserMDAO;
import com.cotton.encrypt.PasswordUtil;
import com.cotton.model.user.User;
import com.cotton.page.Pager;
import com.cotton.result.Result;
import com.cotton.util.RandomStringUtils;

/**
 * 后台用户
 *
 *@author qing.peng
 *@date 2017年6月16日上午9:56:24
 */
@Service
public class UserService extends AuthCommonService{

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserMDAO userMDAO;
    
    /**
     *保存用户
     *
     *@author qing.peng
     *@date 2017年6月14日下午4:36:43
     */
	public Result<Integer> saveUser(User user) {
		Result<Integer> result = new Result<Integer>();
		
		if(StringUtils.isBlank(user.getMobile())){
			return result.setSuccess(false, "请输入手机号");
		}
		if(isExistUser(user.getMobile())){
			return result.setSuccess(false, "账号已存在");
		}
		user.setId(null);
		String pwd=RandomStringUtils.generateRandomNumber(6);
		user.setPassword(PasswordUtil.encrypt(pwd));
		this.userDAO.save(user);
		result.setSuccess(true);
		this.sendCode(user.getMobile(),pwd);
		return result;
	}
    
    public boolean isExistUser(String mobile) {
    	User userDB=this.userDAO.findByMobile(mobile);
		if(userDB==null){
			return false;
		}
		return true;
    }
    
	 /**
     *修改用户
     *
     *@author qing.peng
     *@date 2017年6月14日下午4:36:43
     */
	public Result<Integer> updateUser(User user) {
		Result<Integer> result = new Result<Integer>();
		this.userDAO.save(user);
		result.setSuccess(true);
		return result;
	}
    

	
	public User getUserById(Long id) {
		if(id==null){
			return null;
		}
		return this.userDAO.findOne(id);
	}
	
	 /**
     *用户列表
     *
     *@author qing.peng
     *@date 2017年6月14日下午4:36:43
     */
	public Result<Pager<User>> listUser(int pageNum) {
		Result<Pager<User>> result = new Result<Pager<User>>();
		List<User> userList=userMDAO.page(pageNum, 10);
		int count=userMDAO.count();
		result.setSuccess(true,new Pager<User>(count,userList,pageNum,10));
		return result;
    }
	
	/**
	 *删除用户 by id
	 *
	 *@author qing.peng
	 *@date 2017年6月14日下午4:54:19
	 */
	public void removeUser(Long userId){
		User user = this.getUserById(userId);
		if(user!=null){
			this.userDAO.delete(user);
		}
	}
	
	 /**
     *登录
     *
     *@author qing.peng
     *@date 2017年6月14日下午4:36:43
     */
	public Result<User> login(User user) {
		Result<User> result = new Result<User>();
		String mobile=user.getMobile();
		String passWord=user.getPassword();
		if(StringUtils.isBlank(mobile)){
			return result.setSuccess(false, "请输入账号");
		}
		if(StringUtils.isBlank(passWord)){
			return result.setSuccess(false, "请输入密码");
		}
		User userDB=this.userDAO.findByMobileAndPassword(mobile,PasswordUtil.encrypt(passWord));
		if(userDB==null){
			return result.setSuccess(false, "账号或密码错误");
		}
		result.setSuccess(true,userDB);
		return result;
	}
	
	 /**
     *修改密码
     *
     *@author qing.peng
     *@date 2017年6月14日下午4:36:43
     */
	public Result<User> updatePassword(User user) {
		Result<User> result = new Result<User>();
//		String mobile=user.getMobile();
		String passWord=user.getPassword();
		String newPassword=user.getNewPassword();
		
//		if(StringUtils.isBlank(mobile)){
//			return result.setSuccess(false, "请输入手机号");
//		}
		if(StringUtils.isBlank(passWord)){
			return result.setSuccess(false, "请输入旧密码");
		}
		if(StringUtils.isBlank(newPassword)){
			return result.setSuccess(false, "请输入新密码");
		}
//		User userDB=this.userDAO.findByMobileAndPassword(mobile,PasswordUtil.encrypt(passWord));
//		if(userDB==null){
//			return result.setSuccess(false, "手机号或密码错误");
//		}
		
		User userDB=this.userDAO.findOne(user.getId());
		if(userDB==null){
			return result.setSuccess(false, "用户不存在");
		}
		if(!PasswordUtil.encrypt(passWord).equals(userDB.getPassword())) {
			return result.setSuccess(false, "旧密码错误");
		}
		userDB.setPassword(PasswordUtil.encrypt(newPassword));
		this.userDAO.save(userDB);
		result.setSuccess(true,userDB);
		return result;
	}
	
	 /**
     *忘记密码
     *
     *@author qing.peng
     *@date 2017年6月14日下午4:36:43
     */
	public Result<User> forgetPassword(User user) {
		Result<User> result = new Result<User>();
		String mobile=user.getMobile();
		String code=user.getCode();
		String newPassword=user.getNewPassword();
		
		if(StringUtils.isBlank(mobile)){
			return result.setSuccess(false, "请输入手机号");
		}
		if(StringUtils.isBlank(code)){
			return result.setSuccess(false, "请输入验证码");
		}
		if(StringUtils.isBlank(newPassword)){
			return result.setSuccess(false, "请输入新密码");
		}
		Result<Integer> validate=this.validateMobileCode(mobile,code,"FORGET","ADMIN");
		if(!validate.isSuccess()){
			return result.setSuccess(false, validate.getMsg());
		}
		User userDB=this.userDAO.findByMobile(mobile);
		if(userDB==null){
			return result.setSuccess(false, "账号不存在");
		}
		userDB.setPassword(PasswordUtil.encrypt(newPassword));
		this.userDAO.save(userDB);
		result.setSuccess(true,userDB);
		return result;
	}
}