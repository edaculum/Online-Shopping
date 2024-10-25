package com.shopping.shopping.request;

import lombok.Data;

@Data
public class GetCustomerResponse {
    private String name;
    private String surname;
    private String email;
    private String address;
    private String cityName;  // Şehir adı, ID değil
    private Long cityId;  // Şehir ID
}
