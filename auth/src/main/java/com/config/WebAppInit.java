package com.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.log4j.Logger;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.config.component.BeanConfig;
import com.config.component.JdbcConfig;
import com.config.component.PropertyConfig;
import com.config.mvc.Mvc;
import com.cotton.web.filter.LoginFilter;
import com.cotton.web.servlet.ProxyServlet;

/**
 * springmvc 入口
 *
 * @author yongfei.zheng
 * @date 2017/5/27 16:18 $
 */
public class WebAppInit implements WebApplicationInitializer {

    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("启动web容器");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        //配置项,数据库,bean注入
        rootContext.register(new Class[]{PropertyConfig.class, JdbcConfig.class, BeanConfig.class});
        
        //过滤器
        javax.servlet.FilterRegistration.Dynamic filter = servletContext.addFilter("login",new LoginFilter());
        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "*");
        
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
        dispatcherServlet.register(Mvc.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("*.jsonx");
        
        //ProxyServlet
        ServletRegistration.Dynamic proxyServlet = servletContext.addServlet("proxyServlet", new ProxyServlet());
        proxyServlet.setLoadOnStartup(1);
        proxyServlet.addMapping("/");
    }
}