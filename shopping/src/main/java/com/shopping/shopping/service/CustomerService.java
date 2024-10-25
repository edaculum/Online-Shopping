package com.shopping.shopping.service;

import com.shopping.shopping.mesaj.ResourceNotFoundException;
import com.shopping.shopping.model.Cities;
import com.shopping.shopping.request.*;
import com.shopping.shopping.model.Customers;
import com.shopping.shopping.repository.CustomerRepository;
import com.shopping.shopping.repository.CitiesRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CitiesRepository cityRepository;



    // tüm müşterileri gösterir
    public List<GetCustomerRequest> getAllByCustomers() {
        return customerRepository.findAll().stream().map(customer -> {
            Long cityId = customer.getCity().getId(); // City nesnesinin ID'sini alıyoruz
            String cityName = customer.getCity().getName(); // City nesnesinin adını alıyoruz
            return new GetCustomerRequest(
                    customer.getName(),
                    customer.getSurname(),
                    customer.getEmail(),
                    cityId, // cityId olarak döndürdük
                    cityName, // cityName olarak döndürdük
                    customer.getAdress()
            );
        }).collect(Collectors.toList());
    }

    // Yeni müşteri kaydet
    public Customers createdByCustomer(CreateCustomerRequest customerRequest) {
        if(customerRepository.findByEmail(customerRequest.getEmail()).isPresent()){
            throw new ResourceNotFoundException("*Bu email zaten kayıtlı!");
        }

        // Şehir ID'sine göre şehir bulunuyor
        Cities city = cityRepository.findById(customerRequest.getCityId())
                .orElseThrow(() -> new RuntimeException("*Geçersiz şehir ID'si"));


        // Yeni bir Customers nesnesi oluşturuyoruz
        Customers customer=new Customers();
        customer.setName(customerRequest.getName());
        customer.setSurname(customerRequest.getSurname());
        customer.setEmail(customerRequest.getEmail());
        customer.setPassword(customerRequest.getPassword());
        customer.setAdress(customerRequest.getAdress());
        customer.setCity(city); // Şehir ID'si ile bulunan şehir set ediliyor

        // Müşteriyi veritabanına kaydediyoruz
        return customerRepository.save(customer);
    }


    // Kullanıcı girişi işlemi
    //verilen e-posta adresine sahip bir müşteriyi arar.
    //e-posta ile eşleşen bir müşteri bulunamazsa, orElse(null) ifadesi customer değişkenini null olarak ayarlar.
    public Customers login(LoginDto loginDto) {
        Customers customer = customerRepository.findByEmail(loginDto.getEmail())
                .orElse(null);

        if (customer == null) {
            // ResourceNotFoundException classını çağırıyor
            throw new ResourceNotFoundException("*E-posta veya şifre yanlış.");
        }

        if (!customer.getPassword().equals(loginDto.getPassword())) {
            throw new ResourceNotFoundException("*E-posta veya şifre yanlış.");
        }

        return customer;
    }


    // Kullanıcının bilgilerini getir
    public GetCustomerResponse getCustomerByEmail(String email) {
        Customers customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));

        // DTO'ya dönüştürme
        GetCustomerResponse response = new GetCustomerResponse();
        response.setName(customer.getName());
        response.setSurname(customer.getSurname());
        response.setEmail(customer.getEmail());
        response.setAddress(customer.getAdress());  // Burayı düzeltin

        // Şehir bilgilerini DTO'ya ekleyin
        if (customer.getCity() != null) {
            response.setCityId(customer.getCity().getId());  // Şehir ID'sini ekle
            response.setCityName(customer.getCity().getName());  // Şehir adını ekle
        }

        return response;
    }


    // Müşteriyi güncelle
    public Customers updateByCustomer(String email, UpdateCustomerRequest updateCustomerRequest) {
        Customers existingCustomer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));

        Cities city = cityRepository.findById(updateCustomerRequest.getCityId())
                .orElseThrow(() -> new RuntimeException("*Geçersiz şehir ID'si"));

        existingCustomer.setName(updateCustomerRequest.getName());
        existingCustomer.setSurname(updateCustomerRequest.getSurname());
        existingCustomer.setEmail(updateCustomerRequest.getEmail());
        existingCustomer.setAdress(updateCustomerRequest.getAddress());
        existingCustomer.setCity(city);  // Şehir bilgisini güncelle

        return customerRepository.save(existingCustomer);
    }
    public void deleteByCustomer(Long id) {

        //müşteri kaydının veritabanında mevcut olup olmadığını kontrol eder ve sadece müşteri mevcutsa silme işlemini gerçekleştirir.

        if(doesCustomerExist(id)) {
            customerRepository.deleteById(id);
        }else{ //müşteri kaydı bulunamazsa, ResourceNotFoundException fırlatılır.
            throw new ResourceNotFoundException("Customer not found with id: " + id);

        }
    }


    private boolean doesCustomerExist(Long id) {
        return customerRepository.existsById(id);
    }
}

