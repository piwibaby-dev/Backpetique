package org.generation.backpetique.repository;

import org.generation.backpetique.models.ProductDetail;
import org.generation.backpetique.models.ProductDetailCompose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, ProductDetailCompose> {
}
