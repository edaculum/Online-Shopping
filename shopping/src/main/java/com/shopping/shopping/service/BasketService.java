// src/main/java/com/shopping/shopping/service/BasketService.java
package com.shopping.shopping.service;

import com.shopping.shopping.model.Basket;
import com.shopping.shopping.model.BasketItems;
import com.shopping.shopping.model.Products;
import com.shopping.shopping.repository.BasketItemsRepository;
import com.shopping.shopping.repository.BasketRepository;
import com.shopping.shopping.repository.CustomerRepository;
import com.shopping.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BasketService {
    private final BasketRepository basketRepository;
    private final BasketItemsRepository basketItemsRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    // Sepete ürün ekleme/güncelleme
    public Basket addOrUpdateByProductByBasket(Long customerId, Long productId, int count) {
        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElseGet(() -> createNewBasketForCustomer(customerId));

        // Mevcut ürünü sepette arıyoruz
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
                item.setPrice(product.getPrice() * item.getCount());  // Güncellenen toplam fiyat
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

        return basketRepository.save(basket);
    }

    // Sepetten ürün silme
    public Basket removeByProductFromBasket(Long basketItemId) {
        BasketItems item = basketItemsRepository.findById(basketItemId)
                .orElseThrow(() -> new RuntimeException("Sepet öğesi bulunamadı!"));

        Basket basket = item.getBasket();
        basketItemsRepository.delete(item); // Ürünü sil

        // Sepet öğesini listeden çıkarın
        basket.getBasketItems().removeIf(basketItem -> basketItem.getId().equals(basketItemId));

        return basketRepository.save(basket); // Güncel sepeti döndür
    }

    // Sepeti görüntüleme
    public Basket getByBasketCustomerId(Long customerId) {
        return basketRepository.findByCustomerId(customerId)
                .orElseGet(() -> createNewBasketForCustomer(customerId));
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
}


