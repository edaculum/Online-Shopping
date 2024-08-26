package com.shopping.shopping.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter

//Müşteri kaydı sırasında frontend'den gönderilecek olan veri modeli:
public class CreateCustomerRequest {

    private Long id;

    @NotEmpty(message = "İsim boş olamaz")
    protected String name;

    @NotEmpty(message = "Soyadı boş olamaz")
    private String surname;

    @NotEmpty(message = "Şifre boş olamaz")
    @Size(min = 8, message = "Şifre en az 8 karakter uzunluğunda olmalıdır")
    private String password;

    @NotEmpty(message = "E-mail boş olamaz!")
    @Email(message = "Email geçerli olmalıdır")
    private String email;

    @NotEmpty(message = "Adres boş olamaz")
    private String adress;


    private Long cityId; // Şehir ID'si

}
