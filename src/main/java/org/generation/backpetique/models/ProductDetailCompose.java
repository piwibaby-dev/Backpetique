package org.generation.backpetique.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Embeddable
 * Anotacion que sirve para definir una clase cuyos atributos son almacenados de otra entidad en esta misma
 * La idea de esta anotacion es reutilizar un conjunto de atributos sin tener que crear una tabla independiente para ellos
 * como se hizo en marify
 *
 * SE COLOCA EN LA CLASE DONDE SE VAN A DEFINIR LOS ATRUBUTOS REUTILIZABLES
 *
 * ESTO ES UNA LLAVE COMPUESTA PARA TRAER LOS IDS DE ORDERS Y PRODUCTS
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailCompose {
    private Long order_id;
    private Long product_id;
}
