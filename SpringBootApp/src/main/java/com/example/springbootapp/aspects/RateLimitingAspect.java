package com.example.springbootapp.aspects;

import com.example.springbootapp.exceptions.RateLimitExceededException;
import com.example.springbootapp.security.RateLimited;
import com.google.common.util.concurrent.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitingAspect {

    private final ConcurrentHashMap<Method, RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();  //Mappa thread-safe

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object limitRequestRate(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method= ((MethodSignature) joinPoint.getSignature()).getMethod();
        System.out.println("SIAMO NEL METODO DEL RATE LIMITING ASPECT");  //TODO: da rimuovere
        RateLimited rateLimited = method.getAnnotation(RateLimited.class);  // Se l'annotazione è presente sul metodo, usa quella altrimenti, cerca l'annotazione sulla classe
        if (rateLimited == null) {
            rateLimited = joinPoint.getTarget().getClass().getAnnotation(RateLimited.class);
        }
        if (rateLimited != null) {
            final double permitsPerSecond = rateLimited.permitsPerSecond();
            RateLimiter rateLimiter = rateLimiterMap.computeIfAbsent(method, key -> RateLimiter.create(permitsPerSecond));

            if (rateLimiter.tryAcquire()) {
                return joinPoint.proceed();
            } else {                                                    //non rifattorizzo questi if else per evitare di chiamare la proceed() inutilmente e sprecare risorse
                throw new RateLimitExceededException("Too many requests");
            }
        } else {
            return joinPoint.proceed();
        }
    }
}
