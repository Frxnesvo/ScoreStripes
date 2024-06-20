package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.ProductCreateRequestDto;
import com.example.springbootapp.data.dto.ProductDto;
import com.example.springbootapp.data.dto.ProductSummaryDto;
import com.example.springbootapp.data.dto.ProductUpdateDto;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductSummaryDto> getProductsSummary(Pageable pageable);

    ProductDto getProductById(String id);

    ProductDto createProduct(ProductCreateRequestDto productCreateRequestDto);

    List<ProductDto> getProductsByClub(String clubName);

    ProductDto updateProduct(String id, ProductUpdateDto productUpdateDto);

    List<ProductDto> getMoreSoldProducts(ProductCategory category);
}


