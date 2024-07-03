package com.example.springbootapp.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClubDto {
    private String id;
    private String name;
    private String picUrl;
    private String leagueName;
}
