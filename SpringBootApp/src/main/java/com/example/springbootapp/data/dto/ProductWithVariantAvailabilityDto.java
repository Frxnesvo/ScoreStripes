package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductWithVariantAvailabilityDto {
    private String id;
    private Size size;
    private Integer availability;
}
