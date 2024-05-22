package com.example.springbootapp.controller;

import com.example.springbootapp.security.RateLimited;
import com.example.springbootapp.service.interfaces.ProductWithVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/product-variants")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductWithVariantService productWithVariantService;

    @GetMapping("out-of-stock")
    public ResponseEntity<Integer> countVariantsOutOfStock(){
        return ResponseEntity.ok(productWithVariantService.countVariantsOutOfStock());
    }
}
