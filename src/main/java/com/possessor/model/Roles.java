package com.possessor.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by rpiotrowicz on 2017-02-08.
 */
public enum Roles implements GrantedAuthority{
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String value;

    Roles(String value) {
        this.value = value;
    }

    public String getAuthority(){
        return value;
    }
}
