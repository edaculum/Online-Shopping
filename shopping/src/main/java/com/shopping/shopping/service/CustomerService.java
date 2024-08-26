package com.shopping.shopping.service;

import com.shopping.shopping.mesaj.ResourceNotFoundException;
import com.shopping.shopping.model.Cities;
import com.shopping.shopping.request.CreateCustomerRequest;
import com.shopping.shopping.request.GetCustomerRequest;
import com.shopping.shopping.model.Customers;
import com.shopping.shopping.repository.CustomerRepository;
import com.shopping.shopping.repository.CitiesRepository;

import com.shopping.shopping.request.LoginDto;
import com.shopping.shopping.request.UpdateCustomerRequest;
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
            return new GetCustomerRequest(
                    customer.getName(),
                    customer.getSurname(),
                    customer.getEmail(),
                    cityId, // cityId olarak döndürdük
                    customer.getAdress()
            );
        }).collect(Collectors.toList());
    }

    // Yeni müşteri kaydet
    public Customers createdByCustomer(CreateCustomerRequest customerRequest) {
        if(customerRepository.findByEmail(customerRequest.getEmail()).isPresent()){
            throw new ResourceNotFoundException("Bu email zaten var!");
        }

        // Şehir ID'sine göre şehir bulunuyor
        Cities city = cityRepository.findById(customerRequest.getCityId())
                .orElseThrow(() -> new RuntimeException("Geçersiz şehir ID'si"));


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
            throw new ResourceNotFoundException("E-posta veya şifre yanlış.");
        }

        if (!customer.getPassword().equals(loginDto.getPassword())) {
            throw new ResourceNotFoundException("E-posta veya şifre yanlış.");
        }

        return customer;
    }



//    public GetCustomerRequest getByCustomerEmail(String email) {
//         // Müşteriyi veritabanından ID ile bul
//        Customers customer = customerRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Böyle bir müşteri bulunamadı!"));
//
//        // Müşteri bilgilerini DTO'ya dönüştür ve geri döndür
//        return new GetCustomerRequest(customer.getName(),customer.getSurname(),customer.getEmail(),customer.getAdress(),customer.getCity());
//
//    }


    public Customers updateByCustomer(String email, UpdateCustomerRequest updateCustomerRequest) {
        // İlk olarak, güncellenecek olan müşteri kayıtlı mı kontrol ediliyor
        Customers existingCustomer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));


        // Gelen request içindeki verilerle mevcut müşteri bilgilerini güncelliyoruz
        existingCustomer.setName(updateCustomerRequest.getName());
        existingCustomer.setSurname(updateCustomerRequest.getSurname());
        existingCustomer.setPassword(updateCustomerRequest.getPassword());
        existingCustomer.setEmail(updateCustomerRequest.getEmail());

        // Güncellenmiş müşteriyi veritabanına kaydediyoruz
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

