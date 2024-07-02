package com.example.springbootapp.security.filter;

import com.example.springbootapp.handler.JwtHandler;
import com.example.springbootapp.security.CustomUserDetails;
import com.example.springbootapp.service.impl.UserDetailsServiceImpl;
import com.example.springbootapp.service.interfaces.AccessTokenBlacklistService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtHandler jwtHandler;
    private final AccessTokenBlacklistService accessTokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt= jwtHandler.getJwtFromRequest(request);
        if(StringUtils.hasText(jwt) && jwtHandler.validateToken(jwt) && !accessTokenBlacklistService.isTokenBlacklisted(jwt)){
            String email= jwtHandler.getEmailFromJwt(jwt);
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByEmail(email);
            String role = jwtHandler.getRoleFromJwt(jwt);
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
            UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}


