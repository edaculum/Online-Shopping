package com.shopping.shopping.controller;

import com.shopping.shopping.mesaj.ResourceNotFoundException;
import com.shopping.shopping.request.*;
import com.shopping.shopping.model.Customers;
import com.shopping.shopping.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")  // Frontend URL'sini buraya yazın
@RequestMapping("/shopping/musteriler")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<GetCustomerRequest>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllByCustomers());
    }


    @PostMapping("/kayitol")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateCustomerRequest customerRequest){
        try {
            Customers newCustomer = customerService.createdByCustomer(customerRequest);
            return ResponseEntity.ok(new HashMap<String, Object>() {{
                put("id", newCustomer.getId());
                put("name", newCustomer.getName());
                put("surname", newCustomer.getSurname());
                put("message", "Kayıt başarılı");
            }});
        } catch (ResourceNotFoundException e) {
            // Özel hata mesajını döndürüyoruz
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        } catch (RuntimeException e) {
            // Genel hata mesajını döndürüyoruz
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            // Genel hata mesajını döndürüyoruz
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Bir hata oluştu!"));
        }
    }

    @PostMapping("/girisyap")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        try{
            Customers loggedInCustomer= customerService.login(loginDto);
            Map<String, Object> response = new HashMap<>();
            response.put("id", loggedInCustomer.getId());
            response.put("name", loggedInCustomer.getName()); // Kullanıcı adını ekliyoruz
            response.put("surname", loggedInCustomer.getSurname()); //Kullanıcı soyadını ekliyoruz
            return ResponseEntity.ok(response); // Başarılı Giriş
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", e.getMessage()));
        }
    }


    // Kullanıcının bilgilerini getir
    @GetMapping("/me")
    public ResponseEntity<GetCustomerResponse> getCustomerInfo(@RequestParam String email) {
        GetCustomerResponse customerResponse = customerService.getCustomerByEmail(email);
        if (customerResponse != null) {
            return ResponseEntity.ok(customerResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Kullanıcının bilgilerini güncelle
    @PutMapping("/me")
    public ResponseEntity<Customers> updateCustomerInfo(@RequestParam String email, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Customers updatedCustomer = customerService.updateByCustomer(email, updateCustomerRequest);
        if (updatedCustomer != null) {
            return ResponseEntity.ok(updatedCustomer);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    //Belirli bir id'ye sahip olan müşteriyi siler.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteByCustomer(id);
        return ResponseEntity.ok().build();
    }

}
