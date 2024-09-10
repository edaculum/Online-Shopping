package com.shopping.shopping.service;

import com.shopping.shopping.model.Basket;
import com.shopping.shopping.model.BasketItems;
import com.shopping.shopping.model.Products;
import com.shopping.shopping.repository.BasketItemsRepository;
import com.shopping.shopping.repository.BasketRepository;
import com.shopping.shopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final BasketItemsRepository basketItemsRepository;
    private final ProductRepository productRepository;

    //Sepete ürün ekleme
    public Basket addByProductToBasket(Long customerId, Long productId, int count) {
        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElse(new Basket()); // Müşteri için sepet yoksa yeni sepet oluştur

        //eklenmek istenen ürün veritabanında aranır.
        Products product=productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı!"));


        //basketItem nesnesi oluşturularak, ürün ve adet bilgisi bu nesneye eklenir.
        // Sepet ile ilişkilendirilir ve son olarak sepetin item listesine eklenir.
        BasketItems basketItem= new BasketItems();
        basketItem.setProduct(product);
        basketItem.setCount(count);
        basketItem.setPrice(product.getPrice());
        basketItem.setBasket(basket);

        basket.getBasketItems().add(basketItem);

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

    public void clearBasket(Basket basket) {
        basketItemsRepository.deleteAll(basket.getBasketItems());
    }
}
