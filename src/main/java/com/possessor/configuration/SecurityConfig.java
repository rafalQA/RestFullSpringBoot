package com.possessor.configuration;

import com.possessor.model.Roles;
import com.possessor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

/**
 * Created by rpiotrowicz on 2017-02-09.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    private UserDetailsService userDetailsService = username -> userRepository.findByAccountName(username)
            .map(user -> new User(user.getAccount().getName(), user.getAccount().getPassword(),
                    user.getAccount().getEnable(), true, true, true,
                    user.getAccount().getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList()))
            ).orElseThrow(() -> new UsernameNotFoundException(String.format("No such user %s", username)));

    @Bean
    GlobalAuthenticationConfigurerAdapter authenticationAdapter() {
        return new GlobalAuthenticationConfigurerAdapter() {
            @Override
            public void init(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService);
            }
        };
    }

    @Bean
    WebSecurityConfigurerAdapter webSecurityAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity.httpBasic().and().csrf().disable()
                        .authorizeRequests()
                        .antMatchers("/user/{id}/properties", "properties/{id}").hasRole(Roles.USER.name())
                        .antMatchers("/users", "/users/{id}", "/properties").hasRole(Roles.ADMIN.name())
                        .anyRequest().authenticated();
            }
        };
    }
}
