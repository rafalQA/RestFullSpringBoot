package com.possessor.dto;

import com.possessor.model.Property;

import java.util.Set;

/**
 * Created by rpiotrowicz on 2017-02-06.
 */
public class DtoUser {

    private Long userId;
    private String username;
    private String email;
    private String password;
    private Set<Property> properties;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
