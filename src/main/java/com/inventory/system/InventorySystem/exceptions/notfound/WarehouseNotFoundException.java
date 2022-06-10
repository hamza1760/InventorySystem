package com.inventory.system.InventorySystem.exceptions.notfound;

public class WarehouseNotFoundException extends RuntimeException {

	
	public int id;
	public WarehouseNotFoundException(int id) {
		super();
		this.id = id;
	}
	
	

}
