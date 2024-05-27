package com.example.springbootapp.security.customValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomPersonalizationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPersonalization {
    String message() default "Invalid personalization";   //messaggio inutile perchè lancio un'eccezione personalizzata

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
