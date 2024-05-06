package com.example.springbootapp.Data.DTO;

import com.example.springbootapp.Data.Entities.Enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private String brand;
    private Gender gender;
    private String picUrl;
    private String club;

}
