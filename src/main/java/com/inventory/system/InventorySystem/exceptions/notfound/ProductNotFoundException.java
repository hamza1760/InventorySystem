package com.inventory.system.InventorySystem.exceptions.notfound;

public class ProductNotFoundException extends RuntimeException {

	public int id;

	public ProductNotFoundException(int id) {
		super();
		this.id = id;
	}
}
