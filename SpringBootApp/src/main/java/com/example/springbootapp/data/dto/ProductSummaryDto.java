package com.example.springbootapp.data.dto;

import com.example.springbootapp.data.entities.Enums.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ProductSummaryDto {       //TODO: da cambiare il nome. Questo serve per la visualizzazione veloce dello stock da parte degli admin
    private String id;
    private String clubName;
    private String name;
    private String picUrl;
    private String leagueName;  //Non serve per la visualizzazione ma serve per il filtraggio
    private Map<Size,Integer> variantStock;
}
