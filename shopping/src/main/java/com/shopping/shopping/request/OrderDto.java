package com.shopping.shopping.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private LocalDateTime date;
    private Long customerId;
    private List<OrderItemDto> orderItems;

}
