package com.shopping.shopping.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class BasketDto {
    private Long id;
    private Long customerId;
    private List<BasketItemDto> basketItems;
}
