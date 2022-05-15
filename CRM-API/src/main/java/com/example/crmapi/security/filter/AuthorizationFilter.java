package com.example.crmapi.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.crmapi.security.CustomUsernamePasswordAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/register")) {
            filterChain.doFilter(request, response);
        } else {
            String accessToken = request.getHeader("accessToken");
            if (accessToken != null) {
                try {
                    decodeTokenAndSetAuthContext(accessToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    processException(response, e);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private void processException(HttpServletResponse response, Exception e) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("Error", e.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(403);
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }

    private void decodeTokenAndSetAuthContext(String accessToken) {
        Algorithm algorithm = Algorithm.HMAC256("tajne".getBytes(StandardCharsets.UTF_8));
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(accessToken);
        String email = decodedJWT.getSubject();
        long id = decodedJWT.getClaim("id").asLong();
        Long groupId = decodedJWT.getClaim("groupId").asLong();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("SIEMA"));
        UsernamePasswordAuthenticationToken authenticationToken = new CustomUsernamePasswordAuthenticationToken(email, null, id,groupId,authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
