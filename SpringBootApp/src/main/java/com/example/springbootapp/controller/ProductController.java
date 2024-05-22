package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.ProductCreateRequestDto;
import com.example.springbootapp.data.dto.ProductDto;
import com.example.springbootapp.data.dto.ProductSummaryDto;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
//bindingResult
import org.springframework.validation.BindingResult;

@RateLimited(permitsPerSecond = 10)
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

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ProductDto> createProduct(@Valid @ModelAttribute ProductCreateRequestDto productCreateRequestDto, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException("Input validation failed");
        }
        ProductDto createdProduct = productService.createProduct(productCreateRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProduct);
    }


}
