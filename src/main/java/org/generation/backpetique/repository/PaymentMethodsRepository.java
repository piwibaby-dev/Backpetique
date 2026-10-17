package org.generation.backpetique.repository;

import org.generation.backpetique.models.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods, Long> {
    List<PaymentMethods> findByUserId_IdOrderByIsDefaultDescIdAsc(Long userId);
}
