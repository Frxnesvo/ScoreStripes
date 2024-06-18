package com.example.springbootapp.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WishlistDto {
    private String id;
    //private String ownerUsername;
    private List<WishlistItemDto> items;
}
