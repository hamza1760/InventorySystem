package com.inventory.system.InventorySystem.api.response;


public class ApiResponseInventory {
	
	
	String message;
	int inventoryId;
	int itemId;
	String itemName;

	public ApiResponseInventory(String message, int inventoryId) {
		this.message = message;
		this.inventoryId = inventoryId;
	}

	public ApiResponseInventory(String message, int inventoryId, int itemId, String itemName) {
		this.message = message;
		this.inventoryId = inventoryId;
		this.itemId = itemId;
		this.itemName = itemName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
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
