package com.example.springbootapp.Data.DTO;

import com.example.springbootapp.Data.Entities.Enums.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductWithVariantAvailabilityDto {
    private String id;
    private Size size;
    private Integer availability;
}
