package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.ProductWithVariantDao;
import com.example.springbootapp.service.interfaces.ProductWithVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductWithVariantServiceImpl implements ProductWithVariantService {

    private final ProductWithVariantDao productWithVariantDao;
    @Override
    public Integer countVariantsOutOfStock() {
        return productWithVariantDao.countAllByAvailabilityEquals(0);
    }
}
