package com.example.springbootapp.Data.Entities.Embdeddables;

import com.example.springbootapp.Data.Entities.Enums.Size;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class Personalization {
    private String PlayerName;
    private Integer PlayerNumber;
    @Enumerated(EnumType.STRING)
    private Size size;
}
