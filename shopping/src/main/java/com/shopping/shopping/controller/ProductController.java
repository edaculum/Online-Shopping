package com.shopping.shopping.controller;

import com.shopping.shopping.model.Products;
import com.shopping.shopping.request.ProductDto;
import com.shopping.shopping.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3002") // Frontend URL'sini buraya yazın
@RequestMapping("shopping/ürünler")
public class ProductController {
    private final ProductService productService;

    // Tüm ürünleri getir
    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("kategori/{categoryId}")
    public List<ProductDto> getProductsByCategory(@PathVariable Long categoryId){
        return productService.getProductsByCategory(categoryId);

    }


}
