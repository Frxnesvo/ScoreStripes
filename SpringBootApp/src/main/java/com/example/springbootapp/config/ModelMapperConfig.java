package com.example.springbootapp.config;

import com.example.springbootapp.data.dto.*;
import com.example.springbootapp.data.entities.*;
import com.example.springbootapp.service.impl.AwsS3ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.*;
import org.modelmapper.spi.Mapping;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        //converter personalizzato per gestire la conversione da una lista di varianti a una mappa di stock (taglia -> disponibilità)
        Converter<List<ProductWithVariant>, Map<String,Integer>> variantStockConverter = context -> {
            Map<String,Integer> stockMap = new HashMap<>();
            List<ProductWithVariant> variants = context.getSource();
            if (variants != null) {
                for (ProductWithVariant variant : variants) {
                    stockMap.put(variant.getSize().name(), variant.getAvailability());
                }
            }
            return stockMap;
        };

        //convert personalizzato per gestire la conversione da una lista di WishlistItem a una lista di WishlistItemDto
//        Converter<List<WishlistItem>, List<WishlistItemDto>> wishlistItemConverter = context -> { TODO: DOVREBBE FARLO IN AUTOMATICO
//            List<WishlistItem> source = context.getSource();
//            return source.stream()
//                    .map(item -> modelMapper.map(item, WishlistItemDto.class))
//                    .collect(Collectors.toList());
//        };

        // Convert ProductPic to String
        Converter<ProductPic, String> productPicUrlConverter = context -> {
            String picUrl = context.getSource().getPicUrl();
            return awsS3Service.generatePresignedUrl(picUrl);
        };

//        Converter<Product,BasicProductDto> productToBasicProductDto = context -> {
//            Product source = context.getSource();
//            BasicProductDto destination = context.getDestination();
//            destination.setId(source.getId());
//            destination.setName(source.getName());
//            destination.setDescription(source.getDescription());
//            destination.setBrand(source.getBrand());
//            destination.setGender(source.getGender());
//            destination.setClub(source.getClub().getName());
//            MappingContext<Product,String> picUrlContext = context.create(source, String.class);
//            destination.setPicUrl(firstPicUrlConverter.convert(picUrlContext));
//            return destination;
//        };

//        Converter<ProductWithVariant, ProductWithVariantDto> productWithVariantDtoConverter = context -> {  //TODO: DOVREBBE FARLO IN AUTOMATICO
//            ProductWithVariant source = context.getSource();
//            ProductWithVariantDto destination = context.getDestination();
//            destination.setId(source.getId());
//            destination.setSize(source.getSize());
//            MappingContext<Product,BasicProductDto> productContext = context.create(source.getProduct(), BasicProductDto.class);
//            destination.setProduct(productToBasicProductDto.convert(productContext));
//            return destination;
//        };


        // Convert Product to ProductDto
        PropertyMap<Product, BasicProductDto> productMap = new PropertyMap<>() {
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
        // Convert Customer to CustomerSummaryDto
        PropertyMap<Customer, CustomerSummaryDto> customerMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(profilePicUrlConverter)
                        .map(source.getProfilePicUrl(), destination.getProfilePicUrl());
            }
        };
        // Convert Product to ProductSummaryDto
        PropertyMap<Product, ProductSummaryDto> productSummaryMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setClubName(source.getClub().getName());
                map().setLeagueName(source.getClub().getLeague().getName());
                using(firstPicUrlConverter)
                        .map(source, destination.getPicUrl());
                using(variantStockConverter)
                        .map(source.getVariants(), destination.getVariantStock());
            }
        };
        // Convert Productpic to ProductPicDto
        PropertyMap<ProductPic, ProductPicDto> productPicMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(profilePicUrlConverter)
                        .map(source.getPicUrl(), destination.getPicUrl());
            }
        };

        // Convert Product to ProductDto
        PropertyMap<Product, ProductDto> productDtoMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setClubName(source.getClub().getName());
            }
        };

        // Convert Wishlist to WishlistDto
//        PropertyMap<Wishlist, WishlistDto> wishlistMap = new PropertyMap<>() {    TODO: DOVREBBE FARLO IN AUTOMATICO
//            @Override
//            protected void configure(){
//                using(wishlistItemConverter)
//                        .map(source.getItems(), destination.getItems());
//                //map().setOwnerUsername(source.getOwner().getUsername());
//            }
//        };

        PropertyMap<CartItem, OrderItem> cartItemMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setPrice(source.getPrice());
            }
        };


//        PropertyMap<CartItem, CartItemDto> cartItemToDtoMap = new PropertyMap<>() {  TODO: DOVREBBE FARLO IN AUTOMATICO
//            @Override
//            protected void configure() {
//                using(productWithVariantDtoConverter)
//                        .map(source.getProduct(), destination.getProduct());
//            }
//        };



        modelMapper.addMappings(customerMap);
        modelMapper.addMappings(productMap);
        modelMapper.addMappings(leagueMap);
        modelMapper.addMappings(clubMap);
        modelMapper.addMappings(productSummaryMap);
        modelMapper.addMappings(productPicMap);
        modelMapper.addMappings(productDtoMap);
        //modelMapper.addMappings(wishlistMap);
        modelMapper.addMappings(cartItemMap);
        //modelMapper.addMappings(cartItemToDtoMap);

        modelMapper.addConverter(productPicUrlConverter);


        return modelMapper;
    }
}
