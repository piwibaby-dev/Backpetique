package org.generation.backpetique.dtos;

import java.math.BigDecimal;

public record ProductsRequest (
        String sku,
        String title,
        BigDecimal price,
        Integer stock,
        Boolean prescription,
        BigDecimal weight,
        String unit,
        String urlImg,
        Integer categoryId,
        Integer brandId
){}