package com.example.springbootapp.data.dto;

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
public class ProductUpdateDto {

    @NotBlank
    @Size(min = 10, max = 500)
    private String description;

    @Positive
    private Double price;

    @ValidImageExtension
    private MultipartFile picPrincipal;

    @ValidImageExtension
    private MultipartFile pic2;

    @Size(min=5,max=5)      //TODO: 5 sono i numeri delle possibili size che abbiamo. Non mi piace mettere un numero statico. Devo mettere delle costanti
    private Map<com.example.springbootapp.data.entities.Enums.Size,Integer> variantStocks;  //non posso togliere il prefisso perchè size va in conflitto @Size
}
