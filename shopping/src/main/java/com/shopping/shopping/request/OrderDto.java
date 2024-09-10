package com.shopping.shopping.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private String date;
    private Long customerId;
    private List<OrderItemDto> orderItems;

}
