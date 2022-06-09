package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.CountryDetail;




public interface CountryDetailService {
	
public List<CountryDetail> getCountry();
	
	public CountryDetail getCountryById();
	
	public CountryDetail addCountry(CountryDetail countryDetail);
	
	public void deleteCountry();
	
	

}
