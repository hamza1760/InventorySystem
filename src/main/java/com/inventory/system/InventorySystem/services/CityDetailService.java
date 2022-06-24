package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.CityDetail;

import java.util.List;


public interface CityDetailService {

    List<CityDetail> getCity();

    CityDetail getCityById(int cityId);

    CityDetail addCity(CityDetail cityDetail, int countryId);

    void deleteCity(int cityId);
}
