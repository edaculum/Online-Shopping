package com.shopping.shopping.controller;

import com.shopping.shopping.model.Basket;
import com.shopping.shopping.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Slf4j  // Lombok'un Slf4j anotasyonu
@RequestMapping("/shopping/sepet")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;


    // Sepete ürün ekleme veya güncelleme
    //kullanıcının ID'si (customerId), sepete eklenecek ürünün ID'si (productId), ve ürün adedi (count) istekle birlikte alınır.
    // sepet servisine gidip ürünü sepete ekler ve sonucunda sepetin güncel halini döner.
    // Sepete ürün ekleme veya güncelleme
    @PostMapping("/sepeteEkle")
    public ResponseEntity<Basket> addOrUpdateProductInBasket(
            @RequestParam Long customerId,
            @RequestParam Long productId,
            @RequestParam int count) {

        // Parametrelerin geçerliliğini kontrol et
        if (customerId == null || productId == null || count == 0) {// count != 0
            return ResponseEntity.badRequest().build(); // Geçersiz parametreler için 400 hatası
        }

        log.info("Sepete ekleme isteği alındı. CustomerId: {}, ProductId: {}, Count: {}", customerId, productId, count);
        Basket basket = basketService.addOrUpdateByProductByBasket(customerId, productId, count);
        return new ResponseEntity<>(basket, HttpStatus.OK); // Güncel sepeti döndür
    }

    // Sepetten Ürün silme
    @DeleteMapping("/ürünüSil/{basketItemId}")
    public ResponseEntity<Basket> removeProductFromBasket(@PathVariable Long basketItemId) {
        Basket basket = basketService.removeByProductFromBasket(basketItemId);
        return new ResponseEntity<>(basket, HttpStatus.OK); // Güncel sepeti döndür
    }



    // Müşterinin Sepetini görüntüleme
    @GetMapping("/{customerId}")
    public ResponseEntity<Basket> getBasketCustomerId(@PathVariable Long customerId) {
        try {
            Basket basket = basketService.getByBasketCustomerId(customerId);
            return ResponseEntity.ok(basket);
        } catch (RuntimeException e) {
            // Eğer sepet bulunamazsa, boş bir sepet döndür
            Basket emptyBasket = new Basket();
            emptyBasket.setBasketItems(new ArrayList<>());
            return ResponseEntity.ok(emptyBasket);
        }
    }

    // Sepeti temizleme
    @DeleteMapping("/sepetiTemizle/{customerId}")
    public ResponseEntity<Void> clearBasket(@PathVariable Long customerId) {
        basketService.clearBasket(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

