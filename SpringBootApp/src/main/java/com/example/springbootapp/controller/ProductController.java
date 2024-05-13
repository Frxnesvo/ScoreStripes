package com.example.springbootapp.controller;

import com.example.springbootapp.Data.DTO.ProductDto;
import com.example.springbootapp.Data.DTO.ProductSummaryDto;
import com.example.springbootapp.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/summary")
    public ResponseEntity<Page<ProductSummaryDto>> getProductsSummary(Pageable pageable){
        return ResponseEntity.ok(productService.getProductsSummary(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id){
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
