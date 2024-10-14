package com.shopping.shopping.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto{
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Integer stock;
    private String imageurl;
}


