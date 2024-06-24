package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.*;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Page<ProductSummaryDto> getProductsSummary(Pageable pageable, Map<String,String> filters);

    ProductDto getProductById(String id);

    ProductDto createProduct(ProductCreateRequestDto productCreateRequestDto);

    List<ProductDto> getProductsByClub(String clubName);

    ProductDto updateProduct(String id, ProductUpdateDto productUpdateDto);

    List<ProductDto> getMoreSoldProducts(ProductCategory category);

    Page<BasicProductDto> getProducts(Pageable pageable, Map<String, String> filters);
}


