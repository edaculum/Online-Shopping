package com.shopping.shopping.request;


import lombok.*;
import java.time.LocalDateTime;

// Sipariş Listeleme için DTO
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDto {
    private Long id;
    private LocalDateTime date;
}
