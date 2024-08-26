package com.shopping.shopping.controller;

import com.shopping.shopping.request.ProductDto;
import com.shopping.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000") // Frontend URL'sini buraya yazın
@RequestMapping("shopping/ürünler")
public class ProductController {
    private final ProductService productService;


    @GetMapping("kategori/{categoryId}")
    public List<ProductDto> getProductsByCategory(@PathVariable Long categoryId){
        return productService.getProductsByCategory(categoryId);

    }


}
