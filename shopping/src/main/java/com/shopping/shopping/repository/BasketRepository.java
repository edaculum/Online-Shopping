package com.shopping.shopping.repository;

import com.shopping.shopping.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Optional<Basket> findByCustomerId(Long customerId);
}
