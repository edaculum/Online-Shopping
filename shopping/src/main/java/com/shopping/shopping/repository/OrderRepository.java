package com.shopping.shopping.repository;

import com.shopping.shopping.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long > {
}
