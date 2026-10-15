package org.generation.backpetique.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BrandRequest(

        @NotBlank(message = "El nombre de la marca es obligatorio")
        @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres")
        String brandName,

        @NotNull(message = "El ID de producto es obligatorio")
        Long productsId,

        @NotNull(message = "El ID de categoría es obligatorio")
        Long categoryId
) {
}
