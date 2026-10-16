package org.generation.backpetique.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentRequest(
//        @NotNull(message = "El id de la orden es obligatorio")
        @NotBlank
        Long orderId,

        @NotBlank(message = "El método de pago es obligatorio")
        String paymentMethod,

//        @NotNull(message = "El monto es obligatorio")
        @NotBlank
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
        BigDecimal amount
) {
}