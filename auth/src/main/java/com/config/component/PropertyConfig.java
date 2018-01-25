package com.config.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({
	@PropertySource("classpath:config.properties"),
	@PropertySource("classpath:outInterface.properties")
})
public class PropertyConfig {
	
    /**
     * 注入该bean后,可通过environment获取属性
     *
     * @author yongfei.zheng
     * @date 2017/5/27 16:17 $
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
        p.setFileEncoding("ISO-8859-1");
        return p;
    }
}
