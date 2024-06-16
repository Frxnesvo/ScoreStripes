package com.example.springbootapp.config;

//import com.example.springbootapp.security.filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    @Bean   //TODO: TUTTO DA FARE
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/oauth2/**").permitAll()
                .requestMatchers("/oauth2login").permitAll()
                .anyRequest().authenticated()
        );
        http.sessionManagement(sess -> sess.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));


//        http.addFilterBefore(new CustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.oauth2ResourceServer(AbstractHttpConfigurer::disable);     //disabilita la configurazione del resource server OAuth2
        return http.build();
    }


//
}