package com.shopping.shopping.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="ec1_basket_items")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Aşırı JSON döngülerini kırmak için
public class BasketItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Double price;

    private Integer count;

    //Bir sepet öğesi bir sepete ait olabilir
    @ManyToOne
    @JoinColumn(name="basket_id")
    @JsonBackReference // Sepet ile ilişkide geri referans
    private Basket basket;

    //Bir sepet öğesi bir ürüne ait olabilir
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Products product;
}
