package com.shopping.shopping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ec1_products")
@Data
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="seq")
    private Integer seq;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private Double price;

    @Column(name="description")
    private String description;

    @Column(name="stock")
    private Integer stock;

    @Column(name="imageurl")
    private String imageurl;

    //birçok ürün yalnızca bir kategoriye ait olabilir.
    //ürünler kategorileri category_id sütunu aracılığıyla ilişkilendirir.
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties("products") // Kategorinin ürünler alanını serileştirmiyor
    private Categories category;

    //Bir ürün (Products) birden fazla sipariş öğesi (OrderItems) içerebilir.
    // her Products (Ürün) nesnesinin birden fazla OrderItems (Sipariş Öğesi) nesnesine sahip olabileceğini belirtir.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;


}
