package com.example.springbootapp.security.customValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomImageValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageExtension {
    String message() default "Invalid image file extension. Allowed extensions are: jpg, jpeg, png";  //messaggio inutile perchè lancio un'eccezione personalizzata
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
