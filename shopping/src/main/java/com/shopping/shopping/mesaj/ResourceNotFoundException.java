package com.shopping.shopping.mesaj;


//Belirli türdeki istisnaları ifade eder ve genellikle bir hata mesajını içerir.

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
