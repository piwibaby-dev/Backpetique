package org.generation.backpetique.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor // Te crea un constructor con todos los atributos de tu clase
@NoArgsConstructor // Te crea un constructor vacio
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "user_id")
    private Long id;

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @Column(name = "surnames", length = 100, nullable = false)
    private String surNames;

    @Column(name = "phone_number", length = 20,nullable = false)
    private String phoneNumber;

    @Column(name = "email",length = 100, nullable = false)
    private String email;

    @Column(name = "birthdate", length = 100, nullable = false)
    private Date birthDate;

    @JsonIgnore
    @Column(name = "password_site", length = 250, nullable = false)
    private String passwordSite;

    // CORREGIDO: Se cambió 'mappedBy = "pet"' por 'mappedBy = "userId"'.
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Pets> pets;

}
