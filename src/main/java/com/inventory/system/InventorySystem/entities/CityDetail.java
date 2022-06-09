package com.inventory.system.InventorySystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CityDetail {

	@Id
	private String cityCode;
	private String cityName;

	public CityDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CityDetail(String cityCode, String cityName) {
		super();
		this.cityCode = cityCode;
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
