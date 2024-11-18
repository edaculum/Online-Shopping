package com.shopping.shopping.service;

import com.shopping.shopping.model.Products;
import com.shopping.shopping.repository.ProductRepository;
import com.shopping.shopping.request.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // Tüm ürünleri getir
    public List<ProductDto> getAllProducts() {
        List<Products> products = productRepository.findAll();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    // Kategoriye göre ürünleri getir
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        List<Products> products = productRepository.findByCategoryId(categoryId);

        return products.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    // Product entity'sini DTO'ya dönüştür
    private ProductDto convertToDto(Products product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getStock(),
                product.getImageurl()
        );
    }

    public ProductDto getProductById(Long id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return convertToDto(product);
    }

}