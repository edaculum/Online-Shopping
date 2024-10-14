package com.shopping.shopping.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto {
    private Long id;
    private Long customerId;
    private List<BasketItemDto> basketItems;
}

