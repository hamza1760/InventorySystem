package com.inventory.system.InventorySystem.api.response;


public class ApiResponseInventory {

	String message;
	int inventoryId;


	public ApiResponseInventory(String message, int inventoryId) {
		this.message = message;
		this.inventoryId = inventoryId;
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


}
