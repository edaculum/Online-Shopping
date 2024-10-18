package com.shopping.shopping.controller;


import com.shopping.shopping.model.Orders;
import com.shopping.shopping.request.OrderRequest;
import com.shopping.shopping.request.OrdersDetailDto;
import com.shopping.shopping.request.OrdersDto;
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
    public ResponseEntity<Orders> createOrderFromBasket(@RequestParam Long customerId,@RequestBody OrderRequest orderRequest){
        try {
            Orders order = orderService.createByOrderFromBasket(customerId, orderRequest);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception e) {
            // Hata durumlarını yönetin
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Müşterinin tüm siparişlerini listeleme
    @GetMapping("/customer/{customerId}/orders")
    public ResponseEntity<List<OrdersDto>> getOrdersByCustomer(@PathVariable Long customerId) {
        try {
            List<OrdersDto> orders = orderService.getOrdersByCustomer(customerId);
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Belirli bir siparişin detaylarını alma
    @GetMapping("/orderDetail/{orderId}")
    public ResponseEntity<List<OrdersDetailDto>> getOrderDetails(@PathVariable Long orderId) {
        try {
            List<OrdersDetailDto> orderDetails = orderService.getOrderDetails(orderId);
            return new ResponseEntity<>(orderDetails, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}



