package com.shopping.shopping.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class CategoryNameDto {
    private Long id;
    private String name;
}
