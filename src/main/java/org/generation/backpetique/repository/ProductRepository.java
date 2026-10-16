package org.generation.backpetique.repository;

import org.generation.backpetique.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, Long> {
    Optional<Products> findBySku(String sku);
}