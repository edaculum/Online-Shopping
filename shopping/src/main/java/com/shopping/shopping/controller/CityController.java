package com.shopping.shopping.controller;


import com.shopping.shopping.request.CityNameDto;
import com.shopping.shopping.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")  // Frontend URL'sini buraya yazın
@RequestMapping("shopping/sehirler")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public List<CityNameDto> getAllCityNames(){
        return cityService.getAllCityNames();
    }
}
