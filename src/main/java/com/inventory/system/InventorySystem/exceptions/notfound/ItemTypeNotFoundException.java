package com.inventory.system.InventorySystem.exceptions.notfound;

public class ItemTypeNotFoundException extends RuntimeException {

	public int id;

	public ItemTypeNotFoundException(int id) {
		super();
		this.id = id;
	}
}
