package org.generation.backpetique.repository;

import org.generation.backpetique.models.Pets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// CORREGIDO: findByUsersId  se cambio por findByUserId
public interface PetsRepository extends JpaRepository <Pets, Long> {
    List<Pets> findByUserId(Long userId);
}
