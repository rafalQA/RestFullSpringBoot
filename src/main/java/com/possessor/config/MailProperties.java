package com.possessor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rpiotrowicz on 2017-02-14.
 */
@Component
@ConfigurationProperties("")
public class MailProperties {

    public Map<String, String> getJavaMailProp() {
        return javaMailProp;
    }

    private Map<String,String> javaMailProp = new HashMap<>();
}
