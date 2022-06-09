package com.inventory.system.InventorySystem.exceptions.notfound;

public class InventoryNotFoundException extends RuntimeException {

	
	public int id;
	public InventoryNotFoundException() {

		super();
	}
	public InventoryNotFoundException(int id) {
		super();
		this.id = id;
	}
	
	

}
