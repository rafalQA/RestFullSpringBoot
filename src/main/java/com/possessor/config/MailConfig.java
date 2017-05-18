package com.possessor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Map;

/**
 * Created by rpiotrowicz on 2017-02-13.
 */
@Configuration
public class MailConfig {

    @Value("${javaMailSender.protocol}")
    private String protocol;
    @Value("${javaMailSender.host}")
    private String host;
    @Value("${javaMailSender.port}")
    private int port;
    @Value("${javaMailSender.username}")
    private String username;
    @Value("${javaMailSender.password}")
    private String password;


    @Bean
    MailProperties mailProperties() {
        return new MailProperties();
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setProtocol(protocol);
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);

        Map mailProperties =  mailProperties().getJavaMailProp();

        sender.getJavaMailProperties().putAll(mailProperties);

        return sender;
    }
}
