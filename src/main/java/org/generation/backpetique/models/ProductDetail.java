package org.generation.backpetique.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetail {
    @Column(name = "state", nullable = false, length = 50)
    private String state;
    @Column(name = "quantity",nullable = false)
    private Integer quantity;
    @Column(name = "unit_price_historical")
    private BigDecimal unitPriceHistorical;

    //Se coloca esta anotacion en el atributo de la entidad donde se quiere incorporar esos atributos
    @EmbeddedId
    private ProductDetailCompose productDetailCompose;

    @ManyToOne
    //Se utiliza esta anotacion para que no cree tablas intermediarias entre orders y products
    //la configuración de esta relación ya la maneja la variable orders y products en la clase ProductDetail
    //Gracias a la creacion de las llaves compuestas
    @MapsId("order_id")
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Orders orders;

    @ManyToOne
    @MapsId("product_id")
    @JoinColumn(name = "product_id")
    private Products products;

}
