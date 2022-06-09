package com.inventory.system.InventorySystem.api.response;


public class ApiResponseBrand {
	
	
	String message;
	int BrandId;

	public ApiResponseBrand(String message, int brandId) {
		this.message = message;
		BrandId = brandId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getBrandId() {
		return BrandId;
	}

	public void setBrandId(int brandId) {
		BrandId = brandId;
	}
}
