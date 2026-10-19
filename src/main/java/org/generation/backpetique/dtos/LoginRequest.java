package org.generation.backpetique.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "No puede estar vacio el email")
        String email,

        @NotBlank(message = "No puede estar vacia su contraseña")
        String passwordSite
) {
}
