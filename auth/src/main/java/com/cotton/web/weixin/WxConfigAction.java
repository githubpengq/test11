package com.cotton.web.weixin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cotton.result.Result;
import com.cotton.service.weixin.WxConfigService;

/**
 * 微信配置相关
 *
 * @author qing.peng
 * @date 2017年6月13日下午6:37:19
 */
@Controller
@RequestMapping("auth/weixin")
public class WxConfigAction {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private WxConfigService wxConfigService;
	
	/**
	 * 获取微信 js sdk 配置信息
	 *
	 * @author qing.peng
	 * @date 2017年6月13日下午6:41:44
	 */
	@RequestMapping("/getWxJsConfig")
	@ResponseBody
	public Result<Map<String, String>> getWxJsConfig(HttpServletRequest request,HttpServletResponse response) {
		Result<Map<String, String>> result=null;
		try {
			result =this.wxConfigService.getWxJsConfig(request.getParameter("url"));
		} catch (Exception e) {
			result = new Result<Map<String, String>>().setSuccess(false,"获取微信js配置信息失败");
			logger.error("获取微信js配置信息异常", e);
		}
		return result;
	}
}