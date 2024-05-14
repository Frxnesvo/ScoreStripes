package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.Data.DTO.ProductDto;
import com.example.springbootapp.Data.DTO.ProductSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductSummaryDto> getProductsSummary(Pageable pageable);

    ProductDto getProductById(String id);
}

