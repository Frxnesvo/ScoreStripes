package com.example.springbootapp.handler;

import com.example.springbootapp.data.dto.ErrorDto;
import com.example.springbootapp.exceptions.RequestValidationException;
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto onException(WebRequest req, Exception e) {
        return createErrorRespose(req, e.getMessage());
    }


}
