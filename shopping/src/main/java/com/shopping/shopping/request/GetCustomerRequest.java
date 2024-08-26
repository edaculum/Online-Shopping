package com.shopping.shopping.request;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Data
@RequiredArgsConstructor
public class GetCustomerRequest {
    private String name;
    private String surname;
    private String email;
    private Long cityId;
    private String adress;

    public GetCustomerRequest(String name, String surname, String email, Long cityId, String adress) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cityId = cityId;
        this.adress = adress;
    }

}
