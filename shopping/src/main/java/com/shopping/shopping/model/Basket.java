package com.shopping.shopping.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ec1_basket")
@Data
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Bir sepet bir müşteriye ait olabilir
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customers customer;

    //Bir sepet birden fazla öğe içerebilir
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<BasketItems> basketItems = new ArrayList<>();
}
