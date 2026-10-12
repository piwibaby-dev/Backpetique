package org.generation.backpetique.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record UsersRequest(
        @NotBlank(message = "No puede estar vacio el nombre")
        @Size(min = 2 , max = 100)
        String userName,

        @NotBlank(message = "No puede estar vacios los apellidos")
        @Size(min = 2 , max = 100)
        String surNames,

        @NotBlank(message = "No puede estar vacio el numero de telefono")
        @Size(min = 8 )
        String phoneNumber,

        @NotBlank(message = "No puede estar vacio el email")
        @Size(min = 2 , max = 50)
        String email,

        @NotBlank(message = "No puede estar vacia la fecha ")
        Date birthDate,

        @NotBlank(message = "No puede estar vacia su contraseña ")
        @Size(min = 8 , max = 50)
        String passwordSite
) {
}
