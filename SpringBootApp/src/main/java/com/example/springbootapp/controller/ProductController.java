package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.ProductCreateRequestDto;
import com.example.springbootapp.data.dto.ProductDto;
import com.example.springbootapp.data.dto.ProductSummaryDto;
import com.example.springbootapp.data.dto.ProductUpdateDto;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.utils.ExceptionUtils;
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

import java.util.List;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ExceptionUtils exceptionUtils;

    @GetMapping("/summary")
    public ResponseEntity<Page<ProductSummaryDto>> getProductsSummary(Pageable pageable){
        return ResponseEntity.ok(productService.getProductsSummary(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping(consumes = {"multipart/form-data"})                                  //TODO: controllare se posso togliere il throws
    public ResponseEntity<ProductDto> createProduct(@Valid @ModelAttribute ProductCreateRequestDto productCreateRequestDto, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(exceptionUtils.createErrorMessage(bindingResult));
        }
        ProductDto createdProduct = productService.createProduct(productCreateRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProduct);
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProductsByClub(@RequestParam() String clubName){
        return ResponseEntity.ok(productService.getProductsByClub(clubName));
    }

    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})  //TODO: da proteggere, solo admin                          //TODO: controllare se posso togliere il throws
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @Valid @ModelAttribute ProductUpdateDto productUpdateDto, BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (bindingResult.hasErrors()) {
            throw new RequestValidationException(exceptionUtils.createErrorMessage(bindingResult));
        }
        return ResponseEntity.ok(productService.updateProduct(id, productUpdateDto));
    }

    @GetMapping("/more-sold")
    public ResponseEntity<List<ProductDto>> getMoreSoldProducts(@RequestParam() ProductCategory category){
        return ResponseEntity.ok(productService.getMoreSoldProducts(category));
    }

}
