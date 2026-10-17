package org.generation.backpetique.dtos;

import jakarta.validation.constraints.NotNull;

public record ProductDetailRequest(

        @NotNull(message = "La cantidad no puede estar vacia")
        Integer quantity,

        @NotNull(message = "no se recibio id el producto")
        Long product_id
) {
}
