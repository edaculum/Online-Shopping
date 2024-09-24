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

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final BasketItemsRepository basketItemsRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    //Sepete ürün ekleme
    public Basket addOrUpdateByProductByBasket(Long customerId, Long productId, int count) {
        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElseGet(() -> createNewBasketForCustomer(customerId)); // Müşteri için sepet yoksa yeni sepet oluştur

        // Mevcut ürünü sepette arıyoruz
        Optional<BasketItems> existingItem = basket.getBasketItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            BasketItems item = existingItem.get();
            item.setCount(item.getCount() + count);  // Mevcut ürün varsa sayısını günceller
        } else {
            // Yeni ürün sepete ekleniyor
            Products product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));
            BasketItems newItem = new BasketItems();
            newItem.setProduct(product);
            newItem.setCount(count);
            newItem.setPrice(product.getPrice() * count);  // Toplam fiyat
            newItem.setBasket(basket);
            basket.getBasketItems().add(newItem);
        }

        return basketRepository.save(basket);
    }



    //Sepetten ürün silme
    public void removeByProductFromBasket(Long basketItemId) {
        basketItemsRepository.deleteById(basketItemId);
    }


    //Sepeti görüntüle
    public Basket getByBasketCustomerId(Long customerId) {
        return basketRepository.findByCustomerId(customerId)
                .orElseThrow(()-> new RuntimeException("Sepet bulunamadı!"));
    }

    // Sepeti temizleme
    public void clearBasket(Long customerId) {
        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı!"));
        basket.getBasketItems().clear();  // Sepetteki tüm öğeleri temizler
        basketRepository.save(basket);    // Sepeti boş olarak kaydeder
    }

    //Yeni bir sepet oluşturma
    private Basket createNewBasketForCustomer(Long customerId) {
        Basket basket = new Basket();
        basket.setCustomer(customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!")));
        basket.setBasketItems(new ArrayList<>());  // Boş bir sepet oluşturulur
        return basketRepository.save(basket);
    }

}
