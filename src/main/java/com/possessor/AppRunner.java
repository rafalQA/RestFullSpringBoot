package com.possessor;

import com.utility.LocaleCurrency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AppRunner {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public LocaleCurrency localeCurrency() {
        return new LocaleCurrency();
    }

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }

}
