package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class CountryAlreadyExists extends RuntimeException {
	
	public int countryId;

	public CountryAlreadyExists(int countryId) {
		this.countryId = countryId;
	}
}
