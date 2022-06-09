package com.inventory.system.InventorySystem.api.response;


public class ApiResponseCity {
	
	
	String message;
	String cityCode;

	public ApiResponseCity(String message, String cityCode) {
		this.message = message;
		this.cityCode = cityCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
