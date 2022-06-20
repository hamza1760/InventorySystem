package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class ProductTypeAlreadyExists extends RuntimeException {
	
	public int id;

	public ProductTypeAlreadyExists(int id) {
		super();
		this.id = id;
	}
	

}
