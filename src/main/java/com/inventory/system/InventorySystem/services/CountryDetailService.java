package com.inventory.system.InventorySystem.services;

import com.inventory.system.InventorySystem.entities.CountryDetail;

import java.util.List;



public interface CountryDetailService {
	
public List<CountryDetail> getCountry();
	
	public CountryDetail getCountryById(int countryId);
	
	public CountryDetail addCountry(CountryDetail countryDetail);
	
	public void deleteCountry(int countryId);


	
	

}
