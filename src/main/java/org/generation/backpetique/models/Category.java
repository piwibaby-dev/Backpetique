package org.generation.backpetique.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter



//campo de productos?


public class Category {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name =  "category_name", nullable = false, length = 50)
    private String categoryName;
//similar a marify de un album relacionado a tracks!!!!!!!
//Relacion con Tabla productos (modelos)
    //lo dejo comentado para unirlo con products

    // @OneToMany(mappedBy = "Category", cascade = CascadeType.ALL, orphanRemoval = true)

    // @JsonIgnore
   //  private List<Products> products;



}
