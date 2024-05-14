package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.ProductDto;
import com.example.springbootapp.data.dto.ProductSummaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductSummaryDto> getProductsSummary(Pageable pageable);

    ProductDto getProductById(String id);
}

