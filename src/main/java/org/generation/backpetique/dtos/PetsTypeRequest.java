package org.generation.backpetique.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PetsTypeRequest(
        @NotBlank(message = "El tipo de mascota no puede estar vacío")
        @Size(min = 2, max = 100, message = "El tipo de mascota debe tener entre 2 y 100 caracteres")
        String petType
) {
}