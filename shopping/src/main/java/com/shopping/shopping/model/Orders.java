package com.shopping.shopping.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ec1_orders")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Aşırı JSON döngülerini kırmak için
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "date")
    private LocalDateTime date; // String yerine LocalDateTime kullanıyoruz

    // Bir sipariş bir müşteriye ait olabilir
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference // Müşteri ile ilişkide geri referans
    private Customers customer;

    private String address; //siparişin verildiği adres
    private String paymentMethod; //sipariş verilirken ödeme şekli
    private Double totalPrice;// sipariş verilen ürünlerin toplam fiyatı

    // Bir sipariş birden fazla sipariş öğesini içerebilir
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<OrderItems> orderItems= new ArrayList<>();

}
