package com.inventory.system.InventorySystem.exceptions.notfound;

public class ItemNotFoundException extends RuntimeException {

	public int id;

	public ItemNotFoundException(int id) {
		super();
		this.id = id;
	}
}
