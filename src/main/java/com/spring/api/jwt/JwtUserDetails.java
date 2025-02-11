package com.spring.api.jwt;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private com.spring.api.entity.User user;

    public JwtUserDetails(com.spring.api.entity.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getRole() {
        return this.user.getRole().name();
    }
}
