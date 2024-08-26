package com.shopping.shopping.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ProductDto{
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private String imageurl;
}


