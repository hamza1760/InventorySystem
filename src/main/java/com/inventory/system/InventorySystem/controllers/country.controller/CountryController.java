package com.inventory.system.InventorySystem.controllers.country.controller;

import com.inventory.system.InventorySystem.api.response.ApiResponseCountry;
import com.inventory.system.InventorySystem.entities.CountryDetail;
import com.inventory.system.InventorySystem.services.CountryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<CountryDetail> getCountry() {
        return countryDetailService.getCountry();
    }

    @GetMapping("/country/{countryId}")
    public CountryDetail getCountryById(@PathVariable int countryId) {
        return countryDetailService.getCountryById(countryId);
        
    }

    @DeleteMapping("/country/{countryId}")
    public ResponseEntity<?> deleteCountryById(@PathVariable int countryId){
        countryDetailService.deleteCountry(countryId);
        return new ResponseEntity<>((new ApiResponseCountry("Country Deleted",countryId)), HttpStatus.FOUND);
    }
}
