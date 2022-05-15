package com.example.crmapi.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public long getUserId() {
        CustomUsernamePasswordAuthenticationToken customAuthenticationToken = (CustomUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return customAuthenticationToken.getUserId();
    }

    public long getGroupId() {
        CustomUsernamePasswordAuthenticationToken customAuthenticationToken = (CustomUsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return customAuthenticationToken.getGroupId();
    }

}
