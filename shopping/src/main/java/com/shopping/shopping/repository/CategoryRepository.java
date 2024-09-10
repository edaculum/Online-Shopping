package com.shopping.shopping.repository;

import com.shopping.shopping.model.Categories;
import com.shopping.shopping.request.CategoryNameDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {

    @Query("SELECT new com.shopping.shopping.request.CategoryNameDto(c.id, c.name) FROM Categories c")
    List<CategoryNameDto> findAllCategoryNames();

}

