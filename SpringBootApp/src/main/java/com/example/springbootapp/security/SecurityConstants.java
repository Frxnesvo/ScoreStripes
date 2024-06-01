package com.example.springbootapp.security;

import org.springframework.beans.factory.annotation.Value;


public class SecurityConstants {
    @Value("${jwt.secret}")
    public static String JWT_SECRET;

    @Value("${client-id}")
    public static String WEB_CLIENT_ID;

    public final static long EXPIRATION_TIME = 3_600;   //1 hour
    public final static long EXPIRATION_REFRESH_TOKEN_TIME = 604_800;   //1 week
    public final static String BEARER_TOKEN_PREFIX = "Bearer ";
    public static final String ADMIN_LOGIN_URI_ENDING = "/admin-login";
    public static final String CUSTOMER_LOGIN_URI_ENDING = "/customer-login";
    public static final String REGISTER_ADMIN_URI_ENDING = "/admin-register";
    public static final String REGISTER_CUSTOMER_URI_ENDING = "/customer-register";

}
