package com.possessor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rpiotrowicz on 2017-02-20.
 */

@Configuration
public class WebMvcConfig {

    @Value("${origins}")
    private List<String> origins;

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("*")
                        .allowedOrigins(origins.stream().collect(Collectors.joining(",")));
            }
        };
    }
}
