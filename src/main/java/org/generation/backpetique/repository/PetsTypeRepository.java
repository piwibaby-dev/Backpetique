package org.generation.backpetique.repository;

import org.generation.backpetique.models.PetsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetsTypeRepository extends JpaRepository<PetsType, Long> {
}