package com.shopping.shopping.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="ec1_orders")
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "date")
    private String date;

    // Bir sipariş bir müşteriye ait olabilir
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customer;

    // Bir sipariş birden fazla sipariş öğesini içerebilir
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;

}
