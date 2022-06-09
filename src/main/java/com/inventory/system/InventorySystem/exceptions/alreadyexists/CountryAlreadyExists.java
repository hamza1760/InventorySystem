package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class CountryAlreadyExists extends RuntimeException {
	
	public String countryCode;

	public CountryAlreadyExists(String countryCode) {
		super();
		this.countryCode = countryCode;
	}

	
	

}
