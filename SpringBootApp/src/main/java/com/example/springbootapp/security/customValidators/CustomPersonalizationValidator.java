package com.example.springbootapp.security.customValidators;

import com.example.springbootapp.data.dto.AddToCartRequestDto;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import com.example.springbootapp.exceptions.RequestValidationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.validation.annotation.Validated;

@Validated
public class CustomPersonalizationValidator implements ConstraintValidator<ValidPersonalization, AddToCartRequestDto> {
    @Override
    public boolean isValid(AddToCartRequestDto addToCartRequestDto, ConstraintValidatorContext context){
        return addToCartRequestDto.getCategory() != ProductCategory.SHORTS || addToCartRequestDto.getPersonalization().getPlayerName() == null;
    }
}
