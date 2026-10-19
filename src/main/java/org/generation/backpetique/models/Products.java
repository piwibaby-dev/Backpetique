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
@Table(name = "products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(length = 50)
    private String species;

    @Column(length = 100)
    private String subcategory;

    @Column(name = "life_stage", length = 100)
    private String lifeStage;

    @Column(name = "breed_size", length = 100)
    private String breedSize;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock = 0;

    @Column
    private Boolean prescription = false;

    @Column(precision = 8, scale = 2)
    private BigDecimal weight;

    @Column(length = 20)
    private String unit;

    @Column(name = "url_img", length = 255)
    private String urlImg;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name = "brand_id",nullable = false)
    private Brand brandId;

}