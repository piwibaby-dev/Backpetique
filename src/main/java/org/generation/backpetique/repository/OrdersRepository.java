package org.generation.backpetique.repository;

import org.generation.backpetique.models.Orders;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}
