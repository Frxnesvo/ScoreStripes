package com.example.springbootapp.config;

import com.example.springbootapp.Data.DTO.CustomerSummaryDto;
import com.example.springbootapp.Data.Entities.Customer;
import com.example.springbootapp.service.impl.AwsS3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.example.springbootapp.Data.DTO.ProductDto;
import com.example.springbootapp.Data.Entities.Product;

@Configuration
@RequiredArgsConstructor                          //TODO: DA RIFATTORIZZARE
public class ModelMapperConfig {

    private final AwsS3ServiceImpl awsS3Service;
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        Converter<String, String> profilePicUrlConverter = context -> {
            String profileImageUrl = context.getSource();
            return awsS3Service.generatePresignedUrl(profileImageUrl);
        };

        // Convert Product to ProductDto
        PropertyMap<Product, ProductDto> productMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setClub(source.getClub().getName());
                using(profilePicUrlConverter)
                        .map(source.getPics().get(0).getPicUrl(), destination.getPicUrl());
            }
        };


        modelMapper.addMappings(new PropertyMap<Customer, CustomerSummaryDto>() {
            @Override
            protected void configure() {
                using(profilePicUrlConverter).map(source.getProfilePicUrl(), destination.getProfilePicUrl());
            }
        });
        modelMapper.addMappings(productMap);
        return modelMapper;
    }
}
