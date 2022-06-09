package com.inventory.system.InventorySystem.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CountryDetail {

	@Id
	private String countryCode;
	private String countryName;

	public CountryDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CountryDetail(String countryCode, String countryName) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}
