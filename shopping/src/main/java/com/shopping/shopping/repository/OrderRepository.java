package com.shopping.shopping.repository;

import com.shopping.shopping.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long > {
    List<Orders> findByCustomerId(Long customerId);
}
