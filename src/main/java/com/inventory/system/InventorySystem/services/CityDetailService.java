package com.inventory.system.InventorySystem.services;

import java.util.List;

import com.inventory.system.InventorySystem.entities.CityDetail;




public interface CityDetailService {
	
public List<CityDetail> getCity();
	
	public CityDetail getCityById();
	
	public CityDetail addCity(CityDetail cityDetail);
	
	public void deleteCity();
	
	

}
