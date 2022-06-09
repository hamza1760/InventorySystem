package com.inventory.system.InventorySystem.api.response;


public class ApiResponseItemType {
	
	
	String message;
	int itemTypeId;

	public ApiResponseItemType(String message, int itemTypeId) {
		this.message = message;
		this.itemTypeId = itemTypeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getItemTypeId() {
		return itemTypeId;
	}

	public void setItemTypeId(int itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
}
