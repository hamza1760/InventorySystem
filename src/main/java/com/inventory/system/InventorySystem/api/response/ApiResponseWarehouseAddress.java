package com.inventory.system.InventorySystem.api.response;


public class ApiResponseWarehouseAddress {
	
	
	String message;
	int addressId;

	public ApiResponseWarehouseAddress(String message, int addressId) {
		this.message = message;
		this.addressId = addressId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
}
