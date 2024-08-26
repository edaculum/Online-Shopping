package com.shopping.shopping.model;


import jakarta.persistence.*;
import lombok.Data;


import java.util.List;
//import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name="ec1_customers")
@Data
public class Customers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="email", unique = true, nullable = false)
    private String email;

    //name alanı için boş bir değer (null) girilemez.
    @Column(name = "name", nullable = false) // nullable = false ile belirtiyoruz
    private String name;

    @Column(name = "surname", nullable = false) // nullable = false ile belirtiyoruz
    private String surname;


    @Column(name = "password", nullable = false)
    private String password;


    // Şehir referansı
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false) // Şehir ID'sine referans
    private Cities city;

    @Column(name = "adress", nullable = false)
    private String adress;

    //Bir müşteri birden fazla siparişe sahip olabilir.
    // mappedBy = "customer", bu ilişkiyi yöneten alanın Orders sınıfındaki customer alanı olduğunu belirtir.
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Orders> orders;


}
