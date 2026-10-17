package org.generation.backpetique.dtos;

import java.math.BigDecimal;

public record ProductsRequest (
        String sku,
        String title,
        String description,
        String species,
        String subcategory,
        String lifeStage,
        String breedSize,
        BigDecimal price,
        Integer stock,
        Boolean prescription,
        BigDecimal weight,
        String unit,
        String urlImg,
        Long categoryId,
        Long brandId
){}