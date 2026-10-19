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

public class Pets {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pet_name", nullable = false, length = 100)
    private String petName;

    @Column(name = "pet_age", nullable = false, length = 100)
    private String petAge;

    @Column(name = "pet_weight", nullable = false, length = 100)
    private String petWeight;

    @Column(name = "pet_size", nullable = false, length = 100)
    private String petSize;

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    @JsonIgnore
    private Users userId;

    @ManyToOne
    @JoinColumn(name = "petype_id")
    private PetsType petType;
}
