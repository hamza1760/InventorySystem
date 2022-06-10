package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class CityAlreadyExists extends RuntimeException {
	
	public int cityId;

	public CityAlreadyExists(int cityId) {
		this.cityId = cityId;
	}
}
