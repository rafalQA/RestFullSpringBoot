package com.possessor.config;

import com.possessor.interceptor.RequestLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by rpiotrowicz on 2017-05-18.
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new RequestLimitInterceptor()).addPathPatterns("/**");
    }
}
