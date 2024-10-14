package com.shopping.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ec1_basket")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Aşırı JSON döngülerini kırmak için
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Bir sepet bir müşteriye ait olabilir
    @ManyToOne
    @JoinColumn(name="customer_id")
    @JsonManagedReference // Müşteri ile ilişkide yönetici referans
    private Customers customer;

    //Bir sepet birden fazla öğe içerebilir
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference // Sepet öğeleri ile ilişkide yönetici referans
    private List<BasketItems> basketItems = new ArrayList<>();
}
