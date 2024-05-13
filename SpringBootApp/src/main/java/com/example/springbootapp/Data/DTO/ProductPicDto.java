package com.example.springbootapp.Data.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductPicDto {
    private String id;
    private String picUrl;
    private Boolean principal;
}
