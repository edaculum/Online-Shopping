package com.shopping.shopping.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketItemDto {
    private Long productId;
    private Long basketItemId;
    private Integer count;
    private Double price;
    private ProductDto product;
}
