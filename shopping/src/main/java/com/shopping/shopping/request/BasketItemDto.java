package com.shopping.shopping.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class BasketItemDto {
    private Long productId;
    private Integer count;
    private Double price;
}
