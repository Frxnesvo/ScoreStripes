package com.example.springbootapp.security.customValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ClubNameValidator.class) // Associa l'annotazione al validatore
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER }) // Dove può essere applicata
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidClubName {
    String message() default "The club name is not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
