package com.example.crmapi.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
public class SpringSecurityCustomUser extends org.springframework.security.core.userdetails.User {
    private long userId;
    private Long groupId;

    public SpringSecurityCustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, long userId, Long groupId) {
        super(username, password, authorities);
        this.userId = userId;
        this.groupId = groupId;
    }
}
