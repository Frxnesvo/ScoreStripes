package com.example.springbootapp.security.customValidators;

import com.example.springbootapp.data.dao.ClubDao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClubNameValidator implements ConstraintValidator<ValidClubName, String> {

    private final ClubDao clubDao;

    @Override
    public boolean isValid(String clubName, ConstraintValidatorContext context){
        if(clubName == null) return true;
        return clubDao.existsByName(clubName);
    }
}
