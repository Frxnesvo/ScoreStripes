package com.example.springbootapp.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExceptionUtils {

    public String createErrorMessage(BindingResult bindingResult) {     // Costruisce un messaggio di errore dettagliato da tutte le violazioni di vincolo
        return bindingResult.getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        FieldError fieldError = (FieldError) error;
                        return String.format("Campo '%s': %s", fieldError.getField(), fieldError.getDefaultMessage());
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .collect(Collectors.joining(", "));
    }
}
