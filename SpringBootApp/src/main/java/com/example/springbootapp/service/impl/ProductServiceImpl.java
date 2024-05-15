package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.ClubDao;
import com.example.springbootapp.data.dto.ProductCreateRequestDto;
import com.example.springbootapp.data.dto.ProductDto;
import com.example.springbootapp.data.dto.ProductSummaryDto;
import com.example.springbootapp.data.dao.ProductDao;
import com.example.springbootapp.data.entities.Club;
import com.example.springbootapp.data.entities.Product;
import com.example.springbootapp.data.entities.ProductPic;
import com.example.springbootapp.data.entities.ProductWithVariant;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.example.springbootapp.service.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final AwsS3Service awsS3Service;
    private final ModelMapper modelMapper;
    private final ProductDao productDao;
    private final ClubDao clubDao;

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

    @Override
    public ProductDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Product product = new Product();
        Club club = clubDao.findByName(productCreateRequestDto.getClubName()).orElseThrow(() -> new RequestValidationException("Club not found"));
        modelMapper.map(productCreateRequestDto, product);
        product.setClub(club);
        String principalPicUrl = awsS3Service.uploadFile(productCreateRequestDto.getPicPrincipal(), "products", productCreateRequestDto.getName() + "_principal");
        ProductPic principalPic = new ProductPic(principalPicUrl, true, product);

        String pic2Url = productCreateRequestDto.getPic2() != null
                ? awsS3Service.uploadFile(productCreateRequestDto.getPic2(), "products", productCreateRequestDto.getName() + "_2")
                : null;
        ProductPic pic2 = pic2Url != null ? new ProductPic(pic2Url, false, product) : null;

        product.setPics(Stream.of(principalPic, pic2)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        List<ProductWithVariant> variants = productCreateRequestDto.getVariantStocks().entrySet().stream()
                .map(entry -> new ProductWithVariant(entry.getKey(), entry.getValue(), product))
                .collect(Collectors.toList());
        product.setVariants(variants);

        productDao.save(product);
        return modelMapper.map(product, ProductDto.class);
    }
}
