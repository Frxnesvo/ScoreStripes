package com.example.springbootapp.config;

import com.example.springbootapp.Data.DTO.ClubDto;
import com.example.springbootapp.Data.DTO.CustomerSummaryDto;
import com.example.springbootapp.Data.DTO.LeagueDto;
import com.example.springbootapp.Data.Entities.Club;
import com.example.springbootapp.Data.Entities.Customer;
import com.example.springbootapp.Data.Entities.League;
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
         //converter personalizzato per gestire la conversione da una lista di pics a una stringa
        Converter<Product,String> firstPicUrlConverter = context -> {
            if(!context.getSource().getPics().isEmpty()){
                return awsS3Service.generatePresignedUrl(context.getSource().getPics().get(0).getPicUrl());
            }
            return null;
        };



        PropertyMap<Product, ProductDto> productMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setClub(source.getClub().getName());
                using(firstPicUrlConverter)
                        .map(source, destination.getPicUrl());
            }
        };
        // Convert League to LeagueDto
        PropertyMap<League, LeagueDto> leagueMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(profilePicUrlConverter)
                        .map(source.getPicUrl(), destination.getPicUrl());
            }
        };
        // Convert Club to ClubDto
        PropertyMap<Club, ClubDto> clubMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(profilePicUrlConverter)
                        .map(source.getPicUrl(), destination.getPicUrl());
            }
        };

        PropertyMap<Customer, CustomerSummaryDto> customerMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(profilePicUrlConverter)
                        .map(source.getProfilePicUrl(), destination.getProfilePicUrl());
            }
        };
//
        modelMapper.addMappings(customerMap);
        modelMapper.addMappings(productMap);
        modelMapper.addMappings(leagueMap);
        modelMapper.addMappings(clubMap);
        return modelMapper;
    }
}
