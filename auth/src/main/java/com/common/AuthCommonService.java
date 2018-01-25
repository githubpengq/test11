package com.common;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.cotton.result.Result;
import com.cotton.util.FastJsonUtil;

public class AuthCommonService {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
    private Environment env;
	
	/**
	 * 校验手机验证码
	 *
	 *@author qing.peng
	 *@date 2017年6月26日下午3:30:37
	 */
	@SuppressWarnings("unchecked")
	public Result<Integer> validateMobileCode(String mobile,String code,String serviceTypeEnum,String sourceTypeEnum) {
		Result<Integer> result = null;
		try {
           String json = Request.Post(this.env.getProperty("host.customer")+this.env.getProperty("uri.validateCode"))
        		    .bodyForm(Form.form()
                            .add("mobile",mobile)
                            .add("code", code)
                            .add("serviceTypeEnum",serviceTypeEnum)
                            .add("sourceTypeEnum",sourceTypeEnum)
                            .build())
                    .execute().returnContent().asString();
            result = FastJsonUtil.toBean(json, Result.class);
            logger.error("校验手机验证码 result "+result);
        } catch (Exception e) {
        	result = new Result<Integer>().setSuccess(false, "验证码失败");
            logger.error("校验手机验证码异常",e);
        }
		return result;
	}
	
	/**
	 * 发送短信
	 *
	 *@author qing.peng
	 *@date 2017年6月26日下午3:30:37
	 */
	@SuppressWarnings("unchecked")
	public Result<Integer> sendCode(String mobile,String code) {
		Result<Integer> result = null;
		try {
           String json = Request.Post(this.env.getProperty("host.customer")+this.env.getProperty("uri.sendMsg"))
        		    .bodyForm(Form.form()
                            .add("mobile",mobile)
                            .add("code",code)
                            .build())
                    .execute().returnContent().asString();
            result = FastJsonUtil.toBean(json, Result.class);
            logger.error("发送短信 result "+result);
        } catch (Exception e) {
        	result = new Result<Integer>().setSuccess(false, "发送短信失败");
            logger.error("发送短信异常",e);
        }
		return result;
	}
}