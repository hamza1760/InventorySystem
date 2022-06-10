package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class AddressAlreadyExists extends RuntimeException {
	
	public int addressId;

	public AddressAlreadyExists(int addressId) {
		this.addressId = addressId;
	}
}
