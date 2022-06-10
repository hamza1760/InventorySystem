package com.inventory.system.InventorySystem.exceptions.notfound;

public class CityNotFoundException extends RuntimeException {

	public int id;

	public CityNotFoundException(int id) {
		super();
		this.id = id;
	}
}
