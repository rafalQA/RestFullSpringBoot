package com.possessor.model;

import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rpiotrowicz on 2017-02-06.
 */
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    @Column(name = "ACCOUNT_NAME", unique = true)
    private String name;
    private String password;
    private Boolean enable;
    @ElementCollection(fetch = FetchType.EAGER,targetClass = Roles.class)
    @CollectionTable(name = "AUTHORITY_ROLES", joinColumns = @JoinColumn(name = "ACCOUNT_ID"))
    @Column(name = "ROLES")
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    public Account() {
        this.password = Base64.getEncoder()
                .encodeToString(RandomStringUtils.randomAlphanumeric(20).getBytes());
        this.enable = true;
        this.roles = new HashSet<>();
        this.roles.add(Roles.USER);
    }


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
