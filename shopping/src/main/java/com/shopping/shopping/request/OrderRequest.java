package com.shopping.shopping.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String address;
    private String paymentMethod;//"card" veya "cash"
    // Diğer sipariş detaylarını ekleyebiliriz
}
