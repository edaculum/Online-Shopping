package com.shopping.shopping.repository;


import com.shopping.shopping.model.Cities;
import com.shopping.shopping.request.CityNameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CitiesRepository extends JpaRepository<Cities, Long> {

    // Şehir ismine göre sorgulama yapar
    Optional<Cities> findByName(String name);
}
