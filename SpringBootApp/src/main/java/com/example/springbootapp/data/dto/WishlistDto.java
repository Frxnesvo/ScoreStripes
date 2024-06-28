package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WishlistDto {
    private String id;
    private WishlistVisibility visibility;
    private List<WishlistItemDto> items;
    private String ownerUsername;
}
