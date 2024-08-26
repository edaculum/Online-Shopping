package com.shopping.shopping.repository;

import com.shopping.shopping.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    List<Products> findByCategoryId(Long categoryId);
}
