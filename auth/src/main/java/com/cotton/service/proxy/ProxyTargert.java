package com.cotton.service.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.common.Constant;



/**
 *代理目标地址解析
 *
 *@author qing.peng
 *@date 2017年7月20日下午1:39:34
 */
@Component
public class ProxyTargert {
	
	@Autowired
	private Environment env;
	
    public String getTargetHostByPath(String path){
		if(path==null){
			return null;
		}
		if(path.startsWith(Constant.URI_USER_PREFIX)){
			return this.env.getProperty("host.customer");
		}
		if(path.startsWith(Constant.URI_PRODUCT_PREFIX)){
			return this.env.getProperty("host.product");
		}
		if(path.startsWith(Constant.URI_COTTON_PREFIX)){
			return this.env.getProperty("host.cotton");
		}
		if(path.startsWith(Constant.URI_ORDER_PREFIX)){
			return this.env.getProperty("host.order");
		}
		return null;
	}
}