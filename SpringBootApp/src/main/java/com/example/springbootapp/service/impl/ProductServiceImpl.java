package com.example.springbootapp.service.impl;

import com.example.springbootapp.Data.DTO.ProductDto;
import com.example.springbootapp.Data.DTO.ProductSummaryDto;
import com.example.springbootapp.Data.Dao.ProductDao;
import com.example.springbootapp.service.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductDao productDao;

    @Override
    public Page<ProductSummaryDto> getProductsSummary(Pageable pageable) {
        if(pageable.getSort().isUnsorted()) {
            Sort sort = Sort.by(Sort.Direction.ASC, "clubName");
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        return productDao.findAll(pageable).map(product -> modelMapper.map(product, ProductSummaryDto.class));
    }

    @Override
    public ProductDto getProductById(String id) {
        return modelMapper.map(productDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found")), ProductDto.class);
    }
}
