package com.cotton.service.weixin;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cotton.dao.weixin.AccessTokenDAO;
import com.cotton.dao.weixin.TicketDAO;
import com.cotton.model.weixin.AccessToken;
import com.cotton.model.weixin.AccessToken.AccountType;
import com.cotton.model.weixin.Ticket;
import com.cotton.model.weixin.Ticket.TicketType;
import com.cotton.result.Result;
import com.cotton.util.FastJsonUtil;
import com.util.weixin.JsSdkSign;

import weixin.popular.api.TicketAPI;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.token.Token;

/**
 * 微信配置相关
 *
 *@author qing.peng
 *@date 2017年6月16日上午9:56:24
 */
@Service
public class WxConfigService {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private AccessTokenDAO accessTokenDAO;
    
    @Autowired
    private TicketDAO ticketDAO;
    
    @Autowired
    private Environment env;

    public String appId;
    
    @PostConstruct
    public void init(){
    	appId=this.env.getProperty("weixin.fw.appid");
    }
    
	/**
     * 获取微信js sdk 配置信息
     *@param url 必须是调用JS接口页面的完整URL
     *@author qing.peng
     *@date 2017年6月14日下午4:36:43
     */
	public Result<Map<String, String>> getWxJsConfig(String url) {
		Result<Map<String, String>> result = new Result<Map<String, String>>();
		if (StringUtils.isBlank(url)) {
			return result.setSuccess(false, "url必须是调用JS接口页面的完整URL");
		}
		Map<String, String> ret = JsSdkSign.sign(this.getJsTicket(), url);
		ret.put("appId",this.appId);
		result.setSuccess(true,ret);
		return result;
    }
	/**
	 * 
	 * 获取微信JS接口的临时票据
	 */
	public String getJsTicket() {
		Ticket ticket = ticketDAO.findByTicketType(TicketType.Jsapi);
		if(ticket == null || ticket.isExpire()){
			weixin.popular.bean.ticket.Ticket result = TicketAPI.ticketGetticket(this.getAccessToken());
			if(!result.isSuccess()){
				logger.error("获取微信JS接口的临时票据失败 result "+FastJsonUtil.toJSONString(result));
				throw new RuntimeException();
			}
			if(ticket == null){
				ticket = new Ticket();
				ticket.setTicketType(TicketType.Jsapi);
			}
			ticket.setTicket(result.getTicket());
			ticket.setExpireTime(result.getExpires_in());
			ticketDAO.save(ticket);
		}
		return ticket.getTicket();
	}
	
	/**
	 * 
	 * 获取微信访问令牌
	 */
	public String getAccessToken() {
		AccessToken token = accessTokenDAO.findByAppIdAndAccountType(this.appId, AccountType.FW);
		if(token == null || token.isExpire()){
			Token result = TokenAPI.token(this.appId, this.env.getProperty("weixin.fw.secret"));
			if(!result.isSuccess()){
				logger.error("获取微信访问令牌失败 result "+FastJsonUtil.toJSONString(result));
				throw new RuntimeException();
			}
			if(token == null){
				token = new AccessToken();
				token.setAppId(this.appId);
				token.setAccountType(AccountType.FW);
			}
			token.setToken(result.getAccess_token());
			token.setExpireTime(result.getExpires_in());
			accessTokenDAO.save(token);
		}
		return token.getToken();
	}
}