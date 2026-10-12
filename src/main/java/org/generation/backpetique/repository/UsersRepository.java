package org.generation.backpetique.repository;

import org.generation.backpetique.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
