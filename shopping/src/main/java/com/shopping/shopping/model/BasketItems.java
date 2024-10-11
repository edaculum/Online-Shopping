package com.shopping.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="ec1_basket_items")
@Data
public class BasketItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Double price;

    private Integer count;

    //Bir sepet öğesi bir sepete ait olabilir
    @ManyToOne
    @JoinColumn(name="basket_id")
    @JsonIgnore
    private Basket basket;

    //Bir sepet öğesi bir ürüne ait olabilir
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"products"})
    private Products product;
}
