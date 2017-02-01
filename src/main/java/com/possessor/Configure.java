package com.possessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rpiotrowicz on 2017-01-31.
 */

@Configuration
public class Configure {

   @Bean
    public RestTemplate getRestTemplate(){
       return new RestTemplate();
   }
}
