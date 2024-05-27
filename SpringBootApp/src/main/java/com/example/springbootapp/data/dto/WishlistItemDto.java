package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WishlistItemDto {
    private String id;
    private LocalDate dateAdded;
    private ProductDto product;
}
