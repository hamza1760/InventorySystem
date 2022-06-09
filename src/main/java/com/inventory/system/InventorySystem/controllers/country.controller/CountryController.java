package com.inventory.system.InventorySystem.controllers.country.controller;

import com.inventory.system.InventorySystem.entities.CountryDetail;
import com.inventory.system.InventorySystem.services.CountryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/* country detail Controller*/
@RestController
public class CountryController {

    @Autowired
    private CountryDetailService countryDetailService;

    @PostMapping("/country")
    public CountryDetail addCountry(@RequestBody CountryDetail countryDetail) {

        return countryDetailService.addCountry(countryDetail);
    }

    @GetMapping("/country")
    public List<CountryDetail> getCountry(){
        return countryDetailService.getCountry();
    }
}
