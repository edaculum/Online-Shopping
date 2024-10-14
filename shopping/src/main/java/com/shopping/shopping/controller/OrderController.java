package com.shopping.shopping.controller;


import com.shopping.shopping.model.Orders;
import com.shopping.shopping.request.OrderRequest;
import com.shopping.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@Slf4j  // Lombok'un Slf4j anotasyonu
@CrossOrigin(origins = "http://localhost:3000")  // Frontend URL'sini buraya yazın
@RequestMapping("/shopping/siparis")
public class OrderController {

    private final OrderService orderService;

    //Sepetteki ürünleri siparişe çevirme
    // Kullanıcının sepetindeki tüm ürünler siparişe eklenir ve sipariş oluşturulur.
    @PostMapping("/siparisOlustur")
    public ResponseEntity<Orders> createOrderFromBasket(@RequestParam Long customerId,@RequestBody OrderRequest orderRequest){
        try {
            Orders order = orderService.createByOrderFromBasket(customerId, orderRequest);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            // Hata durumlarını yönetin
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



