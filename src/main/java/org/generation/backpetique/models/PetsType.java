package org.generation.backpetique.models;

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

public class PetsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "petype_id")
    private Long petTypeId;

    @Column(name = "petype_name", nullable = false, length = 100)
    private String petType;

}
