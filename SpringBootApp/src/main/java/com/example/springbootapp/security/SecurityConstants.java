package com.example.springbootapp.security;

public class SecurityConstants {
    public final static String JWT_SECRET = "SecretKey";
    public final static long EXPIRATION_TIME = 3_600;   //1 hour
    public final static long EXPIRATION_REFRESH_TOKEN_TIME = 604_800;   //1 week
    public final static String BEARER_TOKEN_PREFIX = "Bearer ";
    public static final String BASIC_TOKEN_PREFIX =  "Basic ";
    public static final String LOGIN_URI_ENDING = "/login";
    public static final String REGISTER_URI_ENDING = "/register";

}
