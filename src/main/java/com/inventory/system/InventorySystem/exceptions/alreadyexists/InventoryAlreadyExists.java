package com.inventory.system.InventorySystem.exceptions.alreadyexists;

public class InventoryAlreadyExists extends RuntimeException {
	
	public int inventoryId;
	public int itemId;
	public String itemName;

	public InventoryAlreadyExists(int inventoryId, int itemId, String itemName) {
		this.inventoryId = inventoryId;
		this.itemId = itemId;
		this.itemName = itemName;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
