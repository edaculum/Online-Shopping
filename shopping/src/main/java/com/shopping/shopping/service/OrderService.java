package com.shopping.shopping.service;


import com.shopping.shopping.model.*;
import com.shopping.shopping.repository.CustomerRepository;
import com.shopping.shopping.repository.OrderItemRepository;
import com.shopping.shopping.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BasketService basketService;
    private final CustomerRepository customerRepository;


    //Sepetten sipariş oluşturma
    public Orders createByOrderFromBasket(Long customerId) {
        //Müşteri ve sepetin varlığını kontrol et
        Customers customer= customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı!"));

        Basket basket= basketService.getByBasketCustomerId(customerId);

        //Sipariş oluştur
        Orders order= new Orders();
        order.setCustomer(customer);
        order.setDate(LocalDate.now().toString());
        Orders savedOrder=orderRepository.save(order);

        //Sepetteki her ürünü sipariş öğesine çevir
        for(BasketItems basketItem : basket.getBasketItems()){
            OrderItems orderItem = new OrderItems();

            orderItem.setOrder(savedOrder);
            orderItem.setProduct(basketItem.getProduct());
            orderItem.setCount(basketItem.getCount());
            orderItem.setPrice(basketItem.getPrice());
            orderItemRepository.save(orderItem);
        }

        //Siparişten sonra sepeti temizle
        basketService.clearBasket(basket);
        return savedOrder;
    }


    //Müşterinin siparişlerini listeleme
    public List<Orders> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    //Sipaeiş detaylarını görüntüleme
    public Orders getByOrderDetails(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Sipariş bulunamadı!"));
    }
}
