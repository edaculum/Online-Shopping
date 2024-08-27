package com.shopping.shopping.request;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter

//Müşteri kaydı sırasında frontend'den gönderilecek olan veri modeli:
public class CreateCustomerRequest {

    private Long id;

    @NotNull(message = "Ad boş bırakılamaz")
    protected String name;

    @NotNull(message = "Soyad boş bırakılamaz")
    private String surname;

    @NotNull(message = "Şifre boş bırakılamaz")
    @Size(min = 8, message = "Şifre en az 8 karakter uzunluğunda olmalıdır")
    private String password;

    @NotNull(message = "Email boş bırakılamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    private String email;

    @NotNull(message = "Adres kısmı boş bırakılamaz")
    private String adress;


    private Long cityId; // Şehir ID'si

}
