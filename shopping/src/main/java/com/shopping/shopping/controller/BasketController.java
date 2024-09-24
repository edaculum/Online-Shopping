package com.shopping.shopping.controller;

import com.shopping.shopping.model.Basket;
import com.shopping.shopping.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping/sepet")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    // Sepete ürün ekleme veya güncelleme
    //kullanıcının ID'si (customerId), sepete eklenecek ürünün ID'si (productId), ve ürün adedi (count) istekle birlikte alınır.
    // sepet servisine gidip ürünü sepete ekler ve sonucunda sepetin güncel halini döner.
    @PostMapping("/sepeteEkle")
    public ResponseEntity<Basket> addOrUpdateProductInBasket(@RequestParam Long customerId, @RequestParam Long productId,@RequestParam int count){
        Basket basket = basketService.addOrUpdateByProductByBasket(customerId,productId,count);
        return new ResponseEntity<>(basket,HttpStatus.OK);
    }


    //Sepetten Ürün silme
    @DeleteMapping("/ürünüSil/{basketItemId}")

    public ResponseEntity<Void> removeProductFromBasket(@PathVariable Long basketItemId){
        basketService.removeByProductFromBasket(basketItemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Müşterinin Sepetini görüntüleme
    @GetMapping("/{customerId}")
    public ResponseEntity<Basket> getBasketCustomerId(@PathVariable Long customerId){
        Basket basket= basketService.getByBasketCustomerId(customerId);
        return new ResponseEntity<>(basket,HttpStatus.OK);
    }

    // Sepeti temizleme
    @DeleteMapping("/sepetiTemizle/{customerId}")
    public ResponseEntity<Void> clearBasket(@PathVariable Long customerId) {
        basketService.clearBasket(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
