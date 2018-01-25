package com.config.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.common.JpaRepositoryAspect;
import com.cotton.context.SpringContext;

@Configuration
@ComponentScan(basePackages = {"com.cotton.service", "com.cotton.web"})
@EnableAspectJAutoProxy
public class BeanConfig {

    @Autowired
    private Environment env;

    /**
     * 文件上传
     *
     * @author yongfei.zheng
     * @date 2017/5/27 16:15 $
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver getMultipartResolver() {
        CommonsMultipartResolver m = new CommonsMultipartResolver();
        m.setMaxUploadSize(10485760l);
        return m;
    }

    /**
     * 自动设置模型的createTime和updateTime
     *
     * @author yongfei.zheng
     * @date 2017/5/27 16:15 $
     */
    @Bean
    public JpaRepositoryAspect jpaRepositoryAspect() {
        return new JpaRepositoryAspect();
    }
    
    @Bean
    public SpringContext springContext() {
    	SpringContext springContext=new SpringContext();
    	return springContext;
    }
}