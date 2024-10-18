package com.shopping.shopping.request;

import lombok.*;

// Sipariş Detayları için DTO

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDetailDto {
    private String productName; //sipariş edilen ürün adı
    private Integer count; //üründen kaç adet sipariş edildiği
    private String address; //ürünün sipariş edildiği adres bilgisi
    private Double  price; //ürünün fiyatı
}
