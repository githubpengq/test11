package com.cotton.web.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.common.AuthBaseAction;

public class LoginFilter implements Filter {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private Set<String> excludeUris = new HashSet<String>();
	
	//tomcat context path
	private String contextPath="/auth";
	
	//auth项目app接口uri前缀
	private String authAppUriPrefix="/authApp";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		excludeUris.add("/auth/user/login.json");
		excludeUris.add("/auth/user/logout.json");
		excludeUris.add("/auth/user/getUserById.json");
		excludeUris.add("/user/verificationCode/sendMsg.json");
		excludeUris.add("/auth/weixin/getWxJsConfig.json");
		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse rep = (HttpServletResponse) response;
		String proxyTargetUri=this.getProxyTargetUri(req.getRequestURI());

		logger.warn("代理 targetUri "+proxyTargetUri);
		if(isExcludeUri(proxyTargetUri)||proxyTargetUri.startsWith(authAppUriPrefix)){
			chain.doFilter(requestWrapper(req,proxyTargetUri), response);
			return;
		}
		if (AuthBaseAction.getUserId(req)==0) {
			rep.setContentType("text/html;charset=utf-8");
			rep.getWriter().print("{\"code\":\"100\",\"msg\":\"请登录后，再试\",\"success\":false,\"value\":null}");
			return;
		}
		chain.doFilter(requestWrapper(req,proxyTargetUri), response);
	}
	
	/**
	 *取得目标URI(代理或服务器跳转使用)
	 */
	private String getProxyTargetUri(String uri) {
		uri=uri.substring(contextPath.length(),uri.length());
		return uri;
	}
	
	private boolean isExcludeUri(String proxyTargetUri) {
		for(String excludeUri:excludeUris){
			if(proxyTargetUri.startsWith(excludeUri)){
				return true;
			}
		}
		return false;
	}
    
	/**
	 *重写 request uri
	 */
	private HttpServletRequestWrapper requestWrapper(HttpServletRequest req,String uri) {
		return new HttpServletRequestWrapper(req) {
	        @Override
	        public String getRequestURI() {
	            return uri;
	        }
	    };
	}
	
	@Override
	public void destroy() {}
}