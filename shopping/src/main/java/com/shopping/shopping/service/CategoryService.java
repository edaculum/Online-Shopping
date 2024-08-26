package com.shopping.shopping.service;

import com.shopping.shopping.model.Categories;
import com.shopping.shopping.model.Products;
import com.shopping.shopping.repository.CategoryRepository;
import com.shopping.shopping.request.CategoryNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;



    public List<CategoryNameDto> getAllCategoryNames(){
        return categoryRepository.findAllCategoryNames();
    }

    public List<Products> getProductByCategoryId(Long id) {
        return categoryRepository.findById(id).map(Categories::getProducts).orElse(Collections.emptyList());
    }
}
