package com.example.crmapi.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.crmapi.security.LoginForm;
import com.example.crmapi.security.SpringSecurityCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            BufferedReader reader = request.getReader();
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine())!=null){
                sb.append(line);
            }
            LoginForm loginForm = objectMapper.readValue(sb.toString(), LoginForm.class);
            String email = loginForm.getEmail();
            String password = loginForm.getPassword();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            return authenticationManager.authenticate(authenticationToken);
        }catch (IOException e){
            throw new NotYetImplementedException("Boże złóż tego jaśka dobrze");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SpringSecurityCustomUser user = (SpringSecurityCustomUser) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("tajne".getBytes(StandardCharsets.UTF_8));
        String accessToken = buildToken(request, user, algorithm, 240 * 60 * 1000);
        String refreshToken = buildToken(request, user, algorithm, 30 * 24 * 60 * 1000);
        Map<String, String> tokens = getTokensData(response, accessToken, refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("timestamp", new Date(System.currentTimeMillis()));
        responseData.put("message", failed.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseData);
    }

    private Map<String, String> getTokensData(HttpServletResponse response, String accessToken, String refreshToken) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        return tokens;
    }

    private String buildToken(HttpServletRequest request, SpringSecurityCustomUser user, Algorithm algorithm, int i) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("id", user.getUserId())
                .withClaim("groupId", user.getGroupId())
                .withExpiresAt(new Date(System.currentTimeMillis() + i))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }
}
