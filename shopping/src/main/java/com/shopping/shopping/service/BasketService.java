// src/main/java/com/shopping/shopping/service/BasketService.java
package com.shopping.shopping.service;

import com.shopping.shopping.model.*;
import com.shopping.shopping.repository.BasketItemsRepository;
import com.shopping.shopping.repository.BasketRepository;
import com.shopping.shopping.repository.CustomerRepository;
import com.shopping.shopping.repository.ProductRepository;
import com.shopping.shopping.request.BasketDto;
import com.shopping.shopping.request.BasketItemDto;
import com.shopping.shopping.request.CategoryNameDto;
import com.shopping.shopping.request.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {
    private final BasketRepository basketRepository;
    private final BasketItemsRepository basketItemsRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    // Sepete ürün ekleme/güncelleme
    public BasketDto addOrUpdateByProductByBasket(Long customerId, Long productId, int count) {
        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElseGet(() -> createNewBasketForCustomer(customerId));

        Optional<BasketItems> existingItem = basket.getBasketItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));

        if (existingItem.isPresent()) {
            BasketItems item = existingItem.get();
            item.setCount(item.getCount() + count);
            if (item.getCount() <= 0) {
                basket.getBasketItems().remove(item);
                basketItemsRepository.delete(item);
            } else {
                item.setPrice(product.getPrice() * item.getCount());
            }
        } else {
            if (count > 0) {
                BasketItems newItem = new BasketItems();
                newItem.setProduct(product);
                newItem.setCount(count);
                newItem.setPrice(product.getPrice() * count);
                newItem.setBasket(basket);
                basket.getBasketItems().add(newItem);
            } else {
                throw new RuntimeException("Geçersiz ürün miktarı");
            }
        }

        Basket savedBasket = basketRepository.save(basket);
        return convertToDTO(savedBasket);
    }


    // Sepetten ürün silme
    public BasketDto removeByProductFromBasket(Long basketItemId) {
        BasketItems item = basketItemsRepository.findById(basketItemId)
                .orElseThrow(() -> new RuntimeException("Sepet öğesi bulunamadı!"));

        Basket basket = item.getBasket();
        basketItemsRepository.delete(item);
        basket.getBasketItems().removeIf(basketItem -> basketItem.getId().equals(basketItemId));

        Basket savedBasket = basketRepository.save(basket);
        return convertToDTO(savedBasket);
    }

    // Sepeti görüntüleme
    public BasketDto getByBasketCustomerId(Long customerId) {
        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElseGet(() -> createNewBasketForCustomer(customerId));
        return convertToDTO(basket);
    }

    //Müşterinin adresini getirme
    public String getCustomerAddress(Long customerId) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!"));

        return customer.getAdress(); // Müşterinin adresini döndür
    }


    // Sepeti temizleme
    public void clearBasket(Long customerId) {
        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElseGet(() -> createNewBasketForCustomer(customerId));
        basket.getBasketItems().clear();
        basketRepository.save(basket);
    }

    // Yeni bir sepet oluşturma
    private Basket createNewBasketForCustomer(Long customerId) {
        Basket basket = new Basket();
        basket.setCustomer(customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!")));
        basket.setBasketItems(new ArrayList<>());  // Boş bir sepet oluşturulur
        return basketRepository.save(basket);
    }


    // Basket'i DTO'ya dönüştürme
    private BasketDto convertToDTO(Basket basket) {
        BasketDto basketDTO = new BasketDto();
        basketDTO.setId(basket.getId());
        basketDTO.setCustomerId(basket.getCustomer().getId());

        List<BasketItemDto> basketItemDTOs = basket.getBasketItems().stream().map(item -> {
            BasketItemDto itemDTO = new BasketItemDto();
            itemDTO.setBasketItemId(item.getId()); // Burada basketItemId ekleniyor
            itemDTO.setProductId(item.getProduct().getId());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setCount(item.getCount());

            ProductDto productDTO = new ProductDto();
            Products product = item.getProduct();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());
            productDTO.setStock(product.getStock());
            productDTO.setImageurl(product.getImageurl());

            CategoryNameDto categoryDTO = new CategoryNameDto();
            Categories category = product.getCategory();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());

            itemDTO.setProduct(productDTO);

            return itemDTO;
        }).collect(Collectors.toList());

        basketDTO.setBasketItems(basketItemDTOs);
        return basketDTO;
    }
}


