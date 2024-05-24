package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.WishlistVisibility;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistVisibilityDto {
    @NotNull
    private WishlistVisibility visibility;
}
