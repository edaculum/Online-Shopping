package com.shopping.shopping.service;


import com.shopping.shopping.model.*;
import com.shopping.shopping.repository.*;
import com.shopping.shopping.request.OrderRequest;
import com.shopping.shopping.request.OrdersDetailDto;
import com.shopping.shopping.request.OrdersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BasketRepository basketRepository;


    //Sepetten sipariş oluşturma
    @Transactional
    public Orders createByOrderFromBasket(Long customerId, OrderRequest orderRequest) {
        Basket basket = basketRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı!"));

        if (basket.getBasketItems().isEmpty()) {
            throw new RuntimeException("Sepetiniz boş!");
        }

        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!"));

        Orders order = new Orders();
        order.setCustomer(customer);
        order.setAddress(orderRequest.getAddress());
        order.setPaymentMethod(orderRequest.getPaymentMethod());

        // Siparişin oluşturulduğu anın tarih ve saat bilgisini alıyoruz
        order.setDate(LocalDateTime.now());


        // Sepet öğelerini sipariş öğelerine dönüştürün
        // Toplam fiyatı hesapla
        double totalPrice = 0.0;
        for (BasketItems basketItem : basket.getBasketItems()) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setProduct(basketItem.getProduct());
            orderItem.setCount(basketItem.getCount());
            orderItem.setPrice(basketItem.getPrice());

            totalPrice += (basketItem.getCount() * basketItem.getPrice()); // Toplam fiyatı güncelle
            order.getOrderItems().add(orderItem);
        }
        order.setTotalPrice(totalPrice); // Toplam fiyatı sipariş nesnesine ekle

        // Sipariş kaydet
        Orders savedOrder = orderRepository.save(order);

        // Sepeti temizle
        basket.getBasketItems().clear();
        basketRepository.save(basket);

        return savedOrder;
    }


    // Belirli bir müşterinin tüm siparişlerini listeleme
    public List<OrdersDto> getOrdersByCustomer(Long customerId) {
        List<Orders> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream()
                .map(order -> new OrdersDto(order.getId(), order.getDate()))
                .collect(Collectors.toList());
    }


    // Belirli bir siparişin detaylarını alma
    public List<OrdersDetailDto> getOrderDetails(Long orderId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı!"));

        return order.getOrderItems().stream()
                .map(orderItem -> new OrdersDetailDto(
                        orderItem.getProduct().getName(),
                        orderItem.getCount(),
                        order.getAddress(),
                        orderItem.getPrice() // Fiyat bilgisini de ekliyoruz
                ))
                .collect(Collectors.toList());
    }


}
