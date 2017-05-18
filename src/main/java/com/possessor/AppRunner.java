package com.possessor;

import com.possessor.validator.DtoUserValidator;
import com.possessor.validator.PropertyValidator;
import com.utility.LocaleCurrency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class AppRunner extends AsyncConfigurerSupport {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public DtoUserValidator userValidator() {
        return new DtoUserValidator();
    }

    @Bean
    public PropertyValidator propertyValidator() {
        return new PropertyValidator();
    }


    @Bean
    public LocaleCurrency localeCurrency() {
        return new LocaleCurrency();
    }


    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("MailSender-");
        executor.initialize();

        return executor;
    }


    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
