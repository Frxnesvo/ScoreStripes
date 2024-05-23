package com.example.springbootapp.security.filter;

import com.example.springbootapp.security.CustomUserDetails;
import com.example.springbootapp.security.GoogleTokenVerifier;
import com.example.springbootapp.security.JwtUtils;
import com.example.springbootapp.security.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX)) {
                String token = authorizationHeader.substring("Bearer ".length());
                return authenticationManager.authenticate(GoogleTokenVerifier.parseToken(token));
            }
            return null;
            //TODO va bene?
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException(e);
            //TODO da gestire
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails user = (CustomUserDetails) authResult.getPrincipal();
        String userId = user.getID();
        String accessToken = JwtUtils.createAccessToken(userId, request.getRequestURL().toString(),
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String refreshToken = JwtUtils.createRefreshToken(userId);
        response.addHeader("access_token", accessToken);
        response.addHeader("refresh_token", refreshToken);
    }
}
