package org.generation.backpetique.dtos;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;


public record OrdersRequest(
        @NotNull(message = "El monto total es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El monto total debe ser mayor a cero")
        @Digits(integer = 6, fraction = 2, message = "El monto total debe tener como máximo 6 dígitos enteros y 2 decimales")
        BigDecimal totalAmount,

        @Size(max = 100, message = "El estado no puede superar los 100 caracteres")
        String statusO,

//        @NotNull(message = "El ID del pago es obligatorio")
//        Long paymentId,

        @NotNull(message = "El ID del usuario es obligatorio")
        Long userId
) {
}
