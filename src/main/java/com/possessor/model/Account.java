package com.possessor.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rpiotrowicz on 2017-02-06.
 */
@Entity
public class Account implements UserDetails {

    @Id
    private Long accountId;
    @Column(name = "ACCOUNT_NAME", unique = true)
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER,targetClass = Roles.class)
    @CollectionTable(name = "AUTHORITY_ROLES", joinColumns = @JoinColumn(name = "ACCOUNT_ID"))
    @Column(name = "ROLES")
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public Account() {
        this.roles = new HashSet<>();
        this.roles.add(Roles.USER);
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
