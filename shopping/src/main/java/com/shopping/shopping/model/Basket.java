package com.shopping.shopping.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GeneratedColumn;

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
    @OneToMany(mappedBy = "basket",cascade = CascadeType.ALL)
    private List<BasketItems> basketItems;
}
