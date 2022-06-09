package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class WarehouseAlreadyExists extends RuntimeException {
	
	public int id;

	public WarehouseAlreadyExists(int id) {
		super();
		this.id = id;
	}
	

}
