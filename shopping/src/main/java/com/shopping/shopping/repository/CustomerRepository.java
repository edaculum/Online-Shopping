package com.shopping.shopping.repository;

import com.shopping.shopping.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customers, Long> {
  Optional<Customers> findByEmail(String email);

}
