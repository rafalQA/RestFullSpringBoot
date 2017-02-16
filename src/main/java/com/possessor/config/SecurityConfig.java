package com.possessor.config;

import com.possessor.model.Roles;
import com.possessor.model.User;
import com.possessor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by rpiotrowicz on 2017-02-09.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    private UserDetailsService userDetailsService = username ->
            userRepository.findByAccountUsername(username).map(User::getAccount)
                    .orElseThrow(() ->
                            new UsernameNotFoundException(String.format("No such account for user %s", username)));

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    GlobalAuthenticationConfigurerAdapter authenticationAdapter() {
        return new GlobalAuthenticationConfigurerAdapter() {
            @Override
            public void init(AuthenticationManagerBuilder auth) throws Exception {
                auth.authenticationProvider(authProvider());
            }
        };
    }

    @Bean
    WebSecurityConfigurerAdapter webSecurityAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity httpSecurity) throws Exception {
                httpSecurity
                        .httpBasic()
                        .and()
                        .csrf().disable()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.PUT, "/users").permitAll()
                        .antMatchers("/user/{id}/properties", "properties/{id}").hasRole(Roles.USER.name())
                        .antMatchers(HttpMethod.GET, "/users").hasRole(Roles.ADMIN.name())
                        .antMatchers("/users/{id}", "/properties").hasRole(Roles.ADMIN.name());
            }
        };
    }
}
