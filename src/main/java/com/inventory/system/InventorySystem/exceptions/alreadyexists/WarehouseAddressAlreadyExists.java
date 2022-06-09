package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class WarehouseAddressAlreadyExists extends RuntimeException {
	
	public long postalCode;

	public WarehouseAddressAlreadyExists(long postalCode) {
		super();
		this.postalCode = postalCode;
	}

	
	

}
