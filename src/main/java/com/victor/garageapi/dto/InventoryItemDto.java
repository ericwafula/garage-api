package com.victor.garageapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemDto {
    private String SKU;
    private String name;
    private Double price;
    private Boolean isAvailable;
}
