package com.example.springbootapp.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressRequestDto {
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    @Pattern(regexp = "\\d+[A-Za-z]?(/[A-Za-z]?)?", message = "Formato del numero civico non valido")
    private String civicNumber;
    @NotBlank
    @Pattern(regexp = "\\d{5}", message = "Formato del codice postale non valido")
    private String zipCode;
    @NotBlank
    private String state;
    @NotNull
    private Boolean defaultAddress;
}
