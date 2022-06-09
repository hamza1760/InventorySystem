package com.inventory.system.InventorySystem.api.response;


public class ApiResponseItem {
	
	
	String message;
	int itemId;
	public ApiResponseItem(String message, int itemId) {
		this.message = message;
		this.itemId = itemId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getitemId() {
		return itemId;
	}
	public void setitemId(int itemId) {
		this.itemId = itemId;
	}
	
	
	

	

}
