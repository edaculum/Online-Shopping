package com.shopping.shopping.request;

import lombok.Data;

@Data
public class UpdateCustomerRequest {
    private String name;
    private String surname;
    private String email;
    private String address;
    private Long cityId;  // Şehir ID'si


}
