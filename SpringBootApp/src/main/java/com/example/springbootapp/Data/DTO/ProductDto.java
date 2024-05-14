package com.example.springbootapp.Data.DTO;

import com.example.springbootapp.Data.Entities.Enums.Gender;
import com.example.springbootapp.Data.Entities.Enums.ProductCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto {
    private String id;
    private String name;
    private String description;
    private Double price;
    private String brand;
    private Gender gender;
    private ProductCategory category;
    private List<ProductPicDto> pics;
    private String clubName;
    private List<ProductWithVariantAvailabilityDto> variants;
}
