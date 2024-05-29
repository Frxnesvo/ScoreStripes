package com.example.springbootapp.handler;

import com.example.springbootapp.data.dto.ErrorDto;
import com.example.springbootapp.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ErrorDto createErrorRespose(WebRequest req, String message){
        HttpServletRequest httpreq= (HttpServletRequest) req.resolveReference("request");
        return new ErrorDto(new Date(), httpreq.getRequestURI(), message);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto onResourceNotFoundException(WebRequest req, EntityNotFoundException e) {
        return createErrorRespose(req, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto onMethodArgumentNotValidException(WebRequest req, MethodArgumentNotValidException e) {
        return createErrorRespose(req, e.getMessage());
    }

    @ExceptionHandler(RequestValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto onRequestValidationException(WebRequest req, RequestValidationException e) {
        return createErrorRespose(req, e.getMessage());
    }

    @ExceptionHandler(RateLimitExceededException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ErrorDto onRateLimitExceededException(WebRequest req, RateLimitExceededException e) {
        return createErrorRespose(req, e.getMessage());
    }

    @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto onTokenExpiredException(WebRequest req, TokenExpiredException e) {
        return createErrorRespose(req, e.getMessage());
    }

    @ExceptionHandler(StripeSessionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto onStripeSessionException(WebRequest req, StripeSessionException e) {
        return createErrorRespose(req, e.getMessage());
    }

    @ExceptionHandler(S3PutObjectException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onS3PutObjectException(WebRequest req, S3PutObjectException e) {
        return createErrorRespose(req, e.getMessage());
    }

    @ExceptionHandler(EmailMessagingException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onEmailMessagingException(WebRequest req, EmailMessagingException e) {
        return createErrorRespose(req, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onException(WebRequest req, Exception e) {
        return createErrorRespose(req, e.getMessage());
    }


}
