package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.CountryDetail;

import java.util.List;


public interface CountryDetailService {

    List<CountryDetail> getCountry();

    CountryDetail getCountryById(int countryId);

    CountryDetail addCountry(CountryDetail countryDetail);

    void deleteCountry(int countryId);
}
