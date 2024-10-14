package com.shopping.shopping.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ec1_order_item")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Aşırı JSON döngülerini kırmak için
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="price")
    private Double price;

    @Column(name="count")
    private Integer count;

    // Bir sipariş öğesinin bir ürüne ait olduğunu belirtir.
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Orders order;

    // Bir sipariş öğesinin bir ürüne ait olduğunu belirtir.
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;


}


