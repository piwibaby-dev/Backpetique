package org.generation.backpetique.dtos;

import jakarta.validation.constraints.NotBlank;

public record PetsRequest(//@NotBlank
                          Long userId,
                          String petName,
                          String petAge,
                          String petWeight,
                          String petSize,
                          Long petTypeId) {
}
