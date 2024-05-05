package com.example.springbootapp.Data.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerSummaryDto {
    private String id;
    private String username;
    private String profilePicUrl;

}
