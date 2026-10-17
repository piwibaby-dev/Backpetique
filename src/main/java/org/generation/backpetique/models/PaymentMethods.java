package org.generation.backpetique.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payment_methods")
public class PaymentMethods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(length = 50)
    private String alias;

    @Column(name = "holder_name", length = 100)
    private String holderName;

    @Column(length = 20)
    private String brand;

    @Column(length = 4)
    private String last4;

    @Column(length = 5)
    private String expiry;

    @Column(name = "paypal_email", length = 100)
    private String paypalEmail;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Users userId;
}
