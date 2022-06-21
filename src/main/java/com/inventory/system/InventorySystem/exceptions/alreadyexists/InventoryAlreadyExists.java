package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class InventoryAlreadyExists extends RuntimeException {
	
	public int inventoryId;

	public InventoryAlreadyExists(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}
}
