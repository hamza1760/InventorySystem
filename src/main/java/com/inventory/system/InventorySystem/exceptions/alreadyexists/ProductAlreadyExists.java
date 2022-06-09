package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class ProductAlreadyExists extends RuntimeException {
	
	public int id;

	public ProductAlreadyExists(int id) {
		super();
		this.id = id;
	}
	

}
