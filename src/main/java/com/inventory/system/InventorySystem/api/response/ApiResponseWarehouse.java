package com.inventory.system.InventorySystem.api.response;

public class ApiResponseWarehouse {
	
	String message;
	int warehouseId;
	public ApiResponseWarehouse(String message, int warehouseId) {
		this.message = message;
		this.warehouseId = warehouseId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getWarehouseId() {
		return warehouseId;
	}
	public void setWarehouseId(int warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	
	
	
	
	

}
