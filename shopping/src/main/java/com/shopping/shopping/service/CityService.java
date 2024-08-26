package com.shopping.shopping.service;

import com.shopping.shopping.repository.CitiesRepository;
import com.shopping.shopping.request.CityNameDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CitiesRepository citiesRepository;

    public List<CityNameDto> getAllCityNames() {
        return citiesRepository.findAll().stream()
                .map(city -> new CityNameDto(city.getId(), city.getName()))
                .collect(Collectors.toList());
    }

}


