package com.shopping.shopping.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ec1_categories")
@Data
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "seq")
    private Integer seq;

    @Column(name = "name")
    private String name;

    // Bir kategori birden fazla ürüne sahip olabilir
    //mappedBy = "category", bu ilişkiyi yöneten alanın Products sınıfındaki category alanı olduğunu belirtir.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Products> products;

}
