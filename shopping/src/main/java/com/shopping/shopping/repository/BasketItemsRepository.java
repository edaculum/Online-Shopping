package com.shopping.shopping.repository;

import com.shopping.shopping.model.BasketItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketItemsRepository extends JpaRepository<BasketItems, Long> {

}
