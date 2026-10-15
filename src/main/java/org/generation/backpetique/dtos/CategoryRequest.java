package org.generation.backpetique.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

        @NotBlank (message = "Aqui va el nombre de la categoria")
        @Size(min = 3, max = 50)
        String categoryName
//Crear nuevas categoras y luego asociarlas el nombre debe estar entre 3 y 50 caracteres



      //  @NotBlank
       // @Size(min = 3, max = 50)
        // Long ProductsId
//Este request significa que recibe un Id de producto y lo liga a una categoria
) {
}
