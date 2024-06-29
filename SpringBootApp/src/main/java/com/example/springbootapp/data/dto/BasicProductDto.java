package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Gender;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BasicProductDto {
    private String id;
    private String name;
    private String description;
    private String brand;
    private Gender gender;
    private String picUrl;
    private String club;
    private String league;
    private ProductCategory category;
}
