package com.config.mvc;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.cotton.web"})
@EnableAspectJAutoProxy
public class Mvc extends WebMvcConfigurerAdapter {

//    @Autowired
//    private com.cotton.web.interceptor.PropertiesInterceptor propertiesInterceptor;

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/template/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
    }
    
    /** 处理responseBody 里面日期类型 */
    @Bean
    MappingJackson2HttpMessageConverter converter() {
        //Set HTTP Message converter using a JSON implementation.
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(objectMapper());
        return jsonMessageConverter;
    }
   
    @Bean
   public ObjectMapper objectMapper(){
    	ObjectMapper objectMapper=new ObjectMapper();
    	objectMapper.setDateFormat(simpleDateFormat());
    	return  objectMapper;
    }
    
    @Bean
    public SimpleDateFormat simpleDateFormat(){
    	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     	return  simpleDateFormat;
     }
    
    //// 注册拦截器
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    registry.addInterceptor(propertiesInterceptor);
    //}

}
