package com.shopping.shopping.service;


import com.shopping.shopping.model.*;
import com.shopping.shopping.repository.*;
import com.shopping.shopping.request.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



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

        // Sepet öğelerini sipariş öğelerine dönüştürün
        for (BasketItems basketItem : basket.getBasketItems()) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setProduct(basketItem.getProduct());
            orderItem.setCount(basketItem.getCount());
            orderItem.setPrice(basketItem.getPrice());
            order.getOrderItems().add(orderItem);
        }

        // Sipariş kaydet
        Orders savedOrder = orderRepository.save(order);

        // Sepeti temizle
        basket.getBasketItems().clear();
        basketRepository.save(basket);

        return savedOrder;
    }


}
