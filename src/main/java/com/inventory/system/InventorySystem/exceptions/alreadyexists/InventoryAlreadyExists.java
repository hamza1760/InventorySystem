package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class InventoryAlreadyExists extends RuntimeException {
	
	public int inventoryId;

	public InventoryAlreadyExists(int inventoryId) {
		this.inventoryId = inventoryId;
	}
}
