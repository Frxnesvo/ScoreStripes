package com.example.springbootapp.data.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderInfosRequestDto {
    @NotNull
    private String AddressId;
}
