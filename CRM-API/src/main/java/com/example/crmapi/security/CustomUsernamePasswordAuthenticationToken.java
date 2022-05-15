package com.example.crmapi.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
public class CustomUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private long userId;
    private Long groupId;
    public CustomUsernamePasswordAuthenticationToken(Object principal, Object credentials, long userId, Long groupId, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.userId = userId;
        this.groupId = groupId;
    }
}
