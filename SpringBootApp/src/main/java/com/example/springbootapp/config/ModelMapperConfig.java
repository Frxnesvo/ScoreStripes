package com.example.springbootapp.config;

import com.example.springbootapp.Data.DTO.CustomerSummaryDto;
import com.example.springbootapp.Data.Entities.Customer;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        // Altre configurazioni generali qui
        modelMapper.addMappings(new PropertyMap<Customer, CustomerSummaryDto>() {
            @Override
            protected void configure() {
                map().setProfilePicUrl("CIAO");  //TODO: FUNZIONE CHE CAMBIA L'URL DELLA FOTO
            }
        });
        return modelMapper;
    }
}
