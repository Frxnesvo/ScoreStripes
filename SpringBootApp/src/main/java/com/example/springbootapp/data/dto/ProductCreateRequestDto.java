package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Gender;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import com.example.springbootapp.security.customValidators.ValidImageExtension;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
@NoArgsConstructor
public class ProductCreateRequestDto {

    @NotBlank
    @Size(min = 3, max = 40)
    private String name;

    @NotBlank
    @Size(min = 10, max = 500)
    private String description;

    @NotNull
    @Positive
    private Double price;

    @NotBlank
    @Size(min = 2, max = 40)
    private String brand;

    @NotNull
    private Gender gender;

    @NotNull
    private ProductCategory category;

    @NotNull
    @ValidImageExtension
    private MultipartFile picPrincipal;

    @ValidImageExtension
    private MultipartFile pic2;

    @NotBlank
    @Size(min = 3, max = 40)
    private String clubName;

    @NotNull
    @Size(min=5,max=5)      //TODO: 5 sono i numeri delle possibili size che abbiamo. Non mi piace mettere un numero statico
    private Map<com.example.springbootapp.data.entities.Enums.Size,Integer> variantStocks;  //FIXME: vedere come eliminare il prefisso
}
