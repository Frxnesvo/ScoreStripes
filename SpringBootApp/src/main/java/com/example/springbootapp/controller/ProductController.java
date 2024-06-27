package com.example.springbootapp.controller;

import com.example.springbootapp.data.dto.*;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
//bindingResult
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@RateLimited(permitsPerSecond = 10)
@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ExceptionUtils exceptionUtils;

    @PreAuthorize("hasRole('ADMIN')")      //lo proteggo perchè vorrei evitare quanto piu possibile di mostrare gli stock ai customer anche se non è un problema
    @GetMapping("/summary")             //questo è per la ricerca dei prodotti nella dashboard dell'admin. Da cambiare il nome
    public ResponseEntity<Page<ProductSummaryDto>> getProductsSummary(Pageable pageable, @RequestParam(required = false) Map<String,String> filters){
        return ResponseEntity.ok(productService.getProductsSummary(pageable, filters));
    }

    @GetMapping()     //questo è per la ricerca nella home del customer
    public ResponseEntity<Page<BasicProductDto>> getProducts(Pageable pageable, @RequestParam(required = false) Map<String,String> filters){
        return ResponseEntity.ok(productService.getProducts(pageable, filters));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}", consumes = {"multipart/form-data"})       //TODO: controllare se posso togliere il throws
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
