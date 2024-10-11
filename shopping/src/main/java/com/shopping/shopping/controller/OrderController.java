package com.shopping.shopping.controller;


import com.shopping.shopping.model.Orders;
import com.shopping.shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Orders> createOrderFromBasket(@RequestParam Long customerId){
        log.info("Sipariş oluşturma isteği alındı. CustomerId: {}", customerId);
        Orders order= orderService.createByOrderFromBasket(customerId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }


    // Siparişleri görüntüleme
    @GetMapping("/{customerId}")
    public ResponseEntity<List<Orders>> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<Orders> orders = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    //Sipariş detaylarını görüntüleme
    @GetMapping("/siparisDetay/{orderId}")
    public ResponseEntity<Orders> getOrderDetails(@PathVariable Long orderId) {
        Orders order = orderService.getByOrderDetails(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
