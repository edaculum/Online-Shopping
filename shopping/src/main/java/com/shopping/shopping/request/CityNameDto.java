package com.shopping.shopping.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CityNameDto {
    private Long id;   // Şehir ID'si
    private String name;  // Şehir ismi
}
