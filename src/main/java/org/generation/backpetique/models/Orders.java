package org.generation.backpetique.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "order_id")
    private Long id;

    // Mapea la columna 'total_amount' como un decimal obligatorio de hasta 8 dígitos totales (2 decimales)
    @Column(name = "total_amount", precision = 8, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    // Mapea la columna 'order_date' (tipo datetime de SQL) usando LocalDateTime para manejar fecha y hora
    // Ejemplo: "2026-10-12T22:30:00"
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status_o", length = 100)
    private String statusO;

    @ManyToOne
    @JoinColumn(name = "payment_id") // Puede llevar nullable = false si el pago es obligatorio
    private Payment paymentId;


    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    @JsonIgnore
    private Users userId;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "orders")
    private Set<ProductDetail> productDetails;
}
