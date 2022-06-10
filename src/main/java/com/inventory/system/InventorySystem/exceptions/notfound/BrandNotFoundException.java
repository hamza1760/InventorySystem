package com.inventory.system.InventorySystem.exceptions.notfound;

public class BrandNotFoundException extends RuntimeException {

	public int id;

	public BrandNotFoundException(int id) {
		super();
		this.id = id;
	}
}
