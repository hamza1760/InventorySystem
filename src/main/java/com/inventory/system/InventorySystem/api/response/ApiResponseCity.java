package com.inventory.system.InventorySystem.api.response;


public class ApiResponseCity {
	
	
	String message;
	int cityId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public ApiResponseCity(String message, int cityId) {
		this.message = message;
		this.cityId = cityId;
	}
}
