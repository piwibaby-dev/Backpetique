package org.generation.backpetique.repository;

import org.generation.backpetique.models.Pets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetsRepository extends JpaRepository <Pets, Long> {
    List<Pets> findByUsersId(Long userId);
}
