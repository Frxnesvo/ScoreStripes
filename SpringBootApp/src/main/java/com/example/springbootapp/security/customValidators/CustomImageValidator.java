package com.example.springbootapp.security.customValidators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CustomImageValidator implements ConstraintValidator<ValidImageExtension, MultipartFile> {

    private final List<String> allowedMimeType = List.of("image/jpeg", "image/png", "image/jpg");

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context){
        System.out.println("STO VALIDANDO L'IMMAGINE");
        if(file == null) return true;
        System.out.println("IL FILE NON E' NULLO");
        if(!allowedMimeType.contains(file.getContentType())){
            System.out.println("IL FILE NON E' UN'IMMAGINE");
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid image file extension. Allowed extensions are: jpg, jpeg, png")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
