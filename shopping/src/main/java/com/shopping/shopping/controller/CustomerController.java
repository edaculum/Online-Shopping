package com.shopping.shopping.controller;

import com.shopping.shopping.mesaj.ResourceNotFoundException;
import com.shopping.shopping.request.GetCustomerRequest;
import com.shopping.shopping.model.Customers;
import com.shopping.shopping.request.CreateCustomerRequest;
import com.shopping.shopping.request.LoginDto;
import com.shopping.shopping.request.UpdateCustomerRequest;
import com.shopping.shopping.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shopping/musteriler")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<GetCustomerRequest>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllByCustomers());
    }


 //    @GetMapping("/{email}")
//    public ResponseEntity<GetCustomerRequest> getCustomerEmail(@PathVariable String email){
//        //email ile müşteri bilgisini al ve DTO formatında geri döndür
//        return ResponseEntity.ok(customerService.getByCustomerEmail(email));
//    }

    @PostMapping("/kayitol")
    public ResponseEntity<?> signUp(@Valid @RequestBody CreateCustomerRequest customerRequest){
        try {
            Customers newCustomer = customerService.createdByCustomer(customerRequest);
            return ResponseEntity.ok(newCustomer);
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
            return ResponseEntity.ok(loggedInCustomer); //Başarılı Giriş
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // name, surname, password, email güncellenir.
    @PutMapping("/{email}")
    public ResponseEntity<Customers> updateCustomer( @PathVariable String email,@RequestBody UpdateCustomerRequest updateCustomerRequest ){
        Customers updatedCustomer = customerService.updateByCustomer(email,updateCustomerRequest);
        return ResponseEntity.ok(updatedCustomer);

    }

    //Belirli bir id'ye sahip olan müşteriyi siler.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteByCustomer(id);
        return ResponseEntity.ok().build();
    }

}
