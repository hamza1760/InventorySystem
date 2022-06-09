package com.inventory.system.InventorySystem.api.response;


public class ApiResponseWarehouseAddress {
	
	
	String message;
	long postalCode;

	public ApiResponseWarehouseAddress(String message, long postalCode) {
		this.message = message;
		this.postalCode = postalCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(long postalCode) {
		this.postalCode = postalCode;
	}
}
