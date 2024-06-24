package com.example.springbootapp.service.impl;

import com.example.springbootapp.data.dao.ClubDao;
import com.example.springbootapp.data.dto.ProductCreateRequestDto;
import com.example.springbootapp.data.dto.ProductDto;
import com.example.springbootapp.data.dto.ProductSummaryDto;
import com.example.springbootapp.data.dao.ProductDao;
import com.example.springbootapp.data.dto.ProductUpdateDto;
import com.example.springbootapp.data.entities.Club;
import com.example.springbootapp.data.entities.Enums.ProductCategory;
import com.example.springbootapp.data.entities.Product;
import com.example.springbootapp.data.entities.ProductPic;
import com.example.springbootapp.data.entities.ProductWithVariant;
import com.example.springbootapp.data.specification.ProductSpecification;
import com.example.springbootapp.exceptions.RequestValidationException;
import com.example.springbootapp.service.interfaces.AwsS3Service;
import com.example.springbootapp.service.interfaces.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public Page<ProductSummaryDto> getProductsSummary(Pageable pageable, Map<String, String> filters) {
        if(pageable.getSort().isUnsorted()) {
            Sort sort = Sort.by(Sort.Direction.ASC, "clubName");
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        }
        String name = filters.get("name");
        String league = filters.get("league");
        String category = filters.get("category");
        String size = filters.get("size");
        Specification<Product> spec = ProductSpecification.withFilters(name, league, category, size);
        return productDao.findAll(spec,pageable)
                .map(product -> modelMapper.map(product, ProductSummaryDto.class));
    }

    @Override
    public ProductDto getProductById(String id) {
        return modelMapper.map(productDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found")), ProductDto.class);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductCreateRequestDto productCreateRequestDto) {
        Product product = new Product();
        Club club = clubDao.findByName(productCreateRequestDto.getClubName()).orElseThrow(() -> new RequestValidationException("Club not found"));
        if(productDao.existsByName(productCreateRequestDto.getName())) {  //TODO: potrei spostare questo controllo in un'annotazione custom come ho fatto per le immagini
            throw new RequestValidationException("Product with this name already exists");
        }
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

    @Override
    public List<ProductDto> getProductsByClub(String clubName) {
        return productDao.findByClubName(clubName).stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override   //TODO: al momento sto mappando a mano perchè usando il modelMapper con tutta la gestione dei campi nulli è troppo complesso. Da ricontrollare per better method
    @Transactional
    public ProductDto updateProduct(String id, ProductUpdateDto productUpdateDto) {
        Product product = productDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        if(productUpdateDto.getDescription() != null) {product.setDescription(productUpdateDto.getDescription());}
        if(productUpdateDto.getVariantStocks() != null) {
            List<ProductWithVariant> variants = productUpdateDto.getVariantStocks().entrySet().stream()
                    .map(entry -> new ProductWithVariant(entry.getKey(), entry.getValue(), product))
                    .collect(Collectors.toList());
            product.setVariants(variants);
        }
        if(productUpdateDto.getPrice() != null) {product.setPrice(productUpdateDto.getPrice());}
        if(productUpdateDto.getPicPrincipal() != null) {
            ProductPic principalPic = product.getPics().stream()
                    .filter(ProductPic::getPrincipal)
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Principal picture not found"));
            String newPrincipalPicUrl = awsS3Service.uploadFile(productUpdateDto.getPicPrincipal(), "products", product.getName() + "_principal");
            principalPic.setPicUrl(newPrincipalPicUrl);
        }
        if(productUpdateDto.getPic2() != null) {
            ProductPic pic2 = product.getPics().stream()
                    .filter(pic -> !pic.getPrincipal())
                    .findFirst().orElse(null);
            if (pic2 == null) {
                String pic2Url = awsS3Service.uploadFile(productUpdateDto.getPic2(), "products", product.getName() + "_2");
                ProductPic newPic2 = new ProductPic(pic2Url, false, product);
                product.getPics().add(newPic2);
            } else {
                String newPic2Url = awsS3Service.uploadFile(productUpdateDto.getPic2(), "products", product.getName() + "_2");
                pic2.setPicUrl(newPic2Url);
            }
        }
        productDao.save(product);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getMoreSoldProducts(ProductCategory category) {
        Specification<Product> spec = ProductSpecification.topSellingProducts(category);
        Pageable pageable = PageRequest.of(0,5);
        Page<Product> page= productDao.findAll(spec, pageable);
        List<Product> result = page.getContent();
        return result.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());

    }
}
