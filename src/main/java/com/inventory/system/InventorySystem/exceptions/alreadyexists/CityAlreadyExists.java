package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class CityAlreadyExists extends RuntimeException {
	
	public String cityCode;

	public CityAlreadyExists(String cityCode) {
		super();
		this.cityCode = cityCode;
	}

	

}
