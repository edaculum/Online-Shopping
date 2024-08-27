package com.shopping.shopping.controller;

import com.shopping.shopping.model.Categories;
import com.shopping.shopping.model.Products;
import com.shopping.shopping.request.CategoryNameDto;
import com.shopping.shopping.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3002")  // Frontend URL'sini buraya yazın
@RequestMapping("shopping/kategoriler")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryNameDto> getAllCategoryNames(){
        return categoryService.getAllCategoryNames();
    }

    @GetMapping("/{id}/ürünler")
    public List<Products> getProductsCategoryId(@PathVariable Long id){
        return categoryService.getProductByCategoryId(id);

    }
}
