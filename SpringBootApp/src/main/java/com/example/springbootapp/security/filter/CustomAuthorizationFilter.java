package com.example.springbootapp.security.filter;

import com.example.springbootapp.security.JwtUtils;
import com.example.springbootapp.security.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

import java.io.IOException;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String token;

        String header = request.getHeader(AUTHORIZATION);

        if(header != null && header.startsWith(SecurityConstants.BEARER_TOKEN_PREFIX) &&
                !uri.endsWith(SecurityConstants.LOGIN_URI_ENDING) && !uri.endsWith(SecurityConstants.REGISTER_URI_ENDING)){
            try {
                token = header.substring("Bearer ".length());
                UsernamePasswordAuthenticationToken authenticationToken = JwtUtils.parseToken(token);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            catch (Exception e) {
                response.setStatus(FORBIDDEN.value());
                response.setContentType(APPLICATION_JSON_VALUE);
            }
        }
        filterChain.doFilter(request, response);
    }
}