package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.CityDetail;

import java.util.List;




public interface CityDetailService {
	
public List<CityDetail> getCity();
	
	public CityDetail getCityById(int cityId);
	
	public CityDetail addCity(CityDetail cityDetail , int countryId);
	
	public void deleteCity(int cityId);
	
	

}
