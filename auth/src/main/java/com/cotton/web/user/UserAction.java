package com.cotton.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.AuthBaseAction;
import com.cotton.model.user.User;
import com.cotton.page.Pager;
import com.cotton.result.Result;
import com.cotton.service.user.UserService;

/**
 * 后台用户
 *
 * @author qing.peng
 * @date 2017年6月13日下午6:37:19
 */
@Controller
@RequestMapping("auth/user")
public class UserAction extends AuthBaseAction{

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;

	/**
	 * 创建用户信息
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/createUser")
	@ResponseBody
	public Result<Integer> createUser(HttpServletRequest request) {
		Result<Integer> result = null;
		User user = new User();
		user.setMobile(request.getParameter("mobile"));
		user.setName(request.getParameter("name"));
		user.setRole(request.getParameter("role"));
		user.setDuties(request.getParameter("duties"));
		user.setCreateUserId(this.getUserId());
		try {
			result = this.userService.saveUser(user);
		} catch (Exception e) {
			result = new Result<Integer>().setSuccess(false, "创建用户失败，请稍后再试");
			logger.error("创建用户异常", e);
		}
		return result;
	}

	

	/**
	 * 查询成员列表
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/listUser")
	@ResponseBody
	public Result<Pager<User>> listUser(HttpServletRequest request,HttpServletResponse response) {
		Result<Pager<User>> result = null;
		try {
			result = this.userService.listUser(NumberUtils.toInt(request.getParameter("pageNum"),1));
			/*Cookie cookie=CookieUtil.getCookie(request, "JSESSIONID");
			cookie.setPath("/autherw");
			response.addCookie(cookie);*/
		} catch (Exception e) {
			result = new Result<Pager<User>>().setSuccess(false, "查询用户列表失败，请稍后再试");
			logger.error("查询用户列表异常", e);
		}
		return result;
	}

	
	/**
	 * 获取成员信息
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/getUserById")
	@ResponseBody
	public Result<User> getUserById(HttpServletRequest request) {
		Result<User> result = new Result<User>();
		try {
			User userDB=this.userService.getUserById(NumberUtils.toLong(request.getParameter("id")));
			if(userDB==null){
				result.setSuccess(false,"用户不存在");
			}
			result.setSuccess(true,userDB);
		} catch (Exception e) {
			result.setSuccess(false, "查询成员信息失败，请稍后再试");
			logger.error("查询成员信息异常", e);
		}
		return result;
	}

	/**
	 * 修改成员
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/updateUser")
	@ResponseBody
	public Result<Integer> updateUser(HttpServletRequest request) {
		Result<Integer> result = null;
		try {
			
			User userDB=this.userService.getUserById(NumberUtils.toLong(request.getParameter("id")));
			if(userDB==null){
				result=new Result<Integer>().setSuccess(false, "用户不存在");
			}else{
				userDB.setName(request.getParameter("name"));
				userDB.setRole(request.getParameter("role"));
				userDB.setDuties(request.getParameter("duties"));
				userDB.setUpdateUserId(this.getUserId());
				result=this.userService.updateUser(userDB);
			}
		} catch (Exception e) {
			result = new Result<Integer>().setSuccess(false, "修改成员失败，请稍后再试");
			logger.error("修改成员异常", e);
		}
		return result;
	}
	/**
	 * 删除成员
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/deleteUserById")
	@ResponseBody
	public Result<Integer> deleteUserById(HttpServletRequest request) {
		Result<Integer> result = null;
		try {
			this.userService.removeUser(NumberUtils.toLong(request.getParameter("id")));
			result = new Result<Integer>().setSuccess(true, "");
		} catch (Exception e) {
			result = new Result<Integer>().setSuccess(false, "删除成员失败，请稍后再试");
			logger.error("删除成员异常", e);
		}
		return result;
	}
	
	/**
	 * 登录
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/login")
	@ResponseBody
	public Result<User> login(HttpServletRequest request,HttpServletResponse response) {
		Result<User> result = null;
		User user = new User();
		user.setMobile(request.getParameter("mobile"));
		user.setPassword(request.getParameter("password"));
		try {
			result = this.userService.login(user);
			if(result.isSuccess()){
				this.setUser(result.getValue());
			}
		} catch (Exception e) {
			result = new Result<User>().setSuccess(false, "登录失败，请稍后再试");
			logger.error("登录异常", e);
		}
		return result;
	}
	
	/**
	 * 退出
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Result<Integer> logout(HttpServletRequest request) {
		Result<Integer> result=null;
		try {
			this.setUser(null);
			result = new Result<Integer>().setSuccess(true,"退出成功");
		} catch (Exception e) {
			result = new Result<Integer>().setSuccess(false,"退出失败，请稍后再试");
			logger.error("退出异常", e);
		}
		return result;
	}
	
	/**
	 * 获取会话用户信息
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/getSessionUser")
	@ResponseBody
	public Result<User> getSessionUser(HttpServletRequest request,HttpServletResponse response) {
		Result<User> result=null;
		try {
			result = new Result<User>().setSuccess(true,this.getUser());
		} catch (Exception e) {
			result = new Result<User>().setSuccess(false,"获取用户信息失败");
			logger.error("获取会话用户信息异常", e);
		}
		return result;
	}
	
	/**
	 * 修改密码
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/updatePassWord")
	@ResponseBody
	public Result<User> updatePassWord(HttpServletRequest request) {
		Result<User> result = null;
		User user = new User();
		user.setId(this.getUserId());
		user.setPassword(request.getParameter("oldPassword"));
		user.setNewPassword(request.getParameter("newPassword"));
		try {
			result = this.userService.updatePassword(user);
		} catch (Exception e) {
			result = new Result<User>().setSuccess(false, "修改密码失败，请稍后再试");
			logger.error("修改密码异常", e);
		}
		return result;
	}
	
	/**
	 * 忘记密码
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/forgetPassWord")
	@ResponseBody
	public Result<User> forgetPassWord(HttpServletRequest request) {
		Result<User> result = null;
		User user = new User();
		user.setMobile(request.getParameter("mobile"));
		user.setCode(request.getParameter("code"));
		user.setNewPassword(request.getParameter("newPassword"));
		try {
			result = this.userService.forgetPassword(user);
		} catch (Exception e) {
			result = new Result<User>().setSuccess(false, "忘记密码找回失败，请稍后再试");
			logger.error("忘记密码找回异常", e);
		}
		return result;
	}
}