package com.example.springbootapp.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerSummaryDto {
    private String id;
    private String username;
    private String profilePicUrl;

}
