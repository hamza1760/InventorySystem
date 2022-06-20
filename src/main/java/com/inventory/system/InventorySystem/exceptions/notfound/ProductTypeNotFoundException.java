package com.inventory.system.InventorySystem.exceptions.notfound;

public class ProductTypeNotFoundException extends RuntimeException {

	public int id;

	public ProductTypeNotFoundException(int id) {
		super();
		this.id = id;
	}
}
