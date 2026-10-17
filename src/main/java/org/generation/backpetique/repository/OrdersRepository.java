package org.generation.backpetique.repository;

import org.generation.backpetique.models.Orders;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUserId_IdOrderByOrderDateDesc(Long userId);
}
