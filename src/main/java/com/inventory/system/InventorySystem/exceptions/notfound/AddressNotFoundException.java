package com.inventory.system.InventorySystem.exceptions.notfound;

public class AddressNotFoundException extends RuntimeException {

	public int id;

	public AddressNotFoundException(int id) {
		super();
		this.id = id;
	}
}
