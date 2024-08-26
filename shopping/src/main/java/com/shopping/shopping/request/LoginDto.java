package com.shopping.shopping.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class LoginDto {
    @NotEmpty(message = "E-mail boş olamaz!")
    @Email(message = "E-mail example@gmail.com formatına uyumlu olmalı")
    private String email;

    @NotEmpty(message = "Şifre boş olamaz")
    private String password;

}
