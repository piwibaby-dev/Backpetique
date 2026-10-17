package org.generation.backpetique.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PaymentMethodsRequest(
        @Pattern(regexp = "Tarjeta|PayPal|OXXO", message = "El tipo debe ser Tarjeta, PayPal u OXXO")
        String type,

        @Size(max = 50, message = "El alias no puede superar los 50 caracteres")
        String alias,

        @Size(max = 100, message = "El titular no puede superar los 100 caracteres")
        String holderName,

        @Size(max = 20)
        String brand,

        @Pattern(regexp = "\\d{4}", message = "Debes enviar solo los últimos 4 dígitos de la tarjeta")
        String last4,

        @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "El vencimiento debe tener el formato MM/AA")
        String expiry,

        @Email(message = "El correo de PayPal no es válido")
        String paypalEmail,

        Boolean isDefault
) {
}
