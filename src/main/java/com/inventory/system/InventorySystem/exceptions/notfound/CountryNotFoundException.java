package com.inventory.system.InventorySystem.exceptions.notfound;

public class CountryNotFoundException extends RuntimeException {

	public int id;

	public CountryNotFoundException(int id) {
		super();
		this.id = id;
	}
}
