package org.generation.backpetique.repository;

import org.generation.backpetique.models.PetsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetsTypeRepository extends JpaRepository<PetsType, Long> {
    Optional<PetsType> findByPetType(String petType);
}