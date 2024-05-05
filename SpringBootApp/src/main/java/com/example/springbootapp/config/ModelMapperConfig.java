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

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    private final AwsS3ServiceImpl awsS3Service;
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        // Altre configurazioni generali qui
        Converter<String, String> profilePicUrlConverter = context -> {
            String profileImageUrl = context.getSource();
            return awsS3Service.generatePresignedUrl(profileImageUrl);
        };

        modelMapper.addMappings(new PropertyMap<Customer, CustomerSummaryDto>() {
            @Override
            protected void configure() {
                using(profilePicUrlConverter).map(source.getProfilePicUrl(), destination.getProfilePicUrl());
            }
        });
        return modelMapper;
    }
}
