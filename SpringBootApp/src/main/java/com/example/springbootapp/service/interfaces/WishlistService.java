package com.example.springbootapp.service.interfaces;

import com.example.springbootapp.data.dto.WishlistVisibilityDto;
import com.example.springbootapp.data.entities.Enums.WishlistVisibility;

public interface WishlistService {
    void changeVisibility(WishlistVisibilityDto visibilityDto);
}
